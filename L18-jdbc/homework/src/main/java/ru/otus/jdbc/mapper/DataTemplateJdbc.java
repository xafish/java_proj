package ru.otus.jdbc.mapper;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;
import ru.otus.core.sessionmanager.DataBaseOperationException;
import ru.otus.crm.model.Client;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final EntityClassMetaData<T> entityClassMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData, EntityClassMetaData entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), rs -> {
            try {
                if (rs.next()) {
                    // создаём экземпляр класса
                    T model = entityClassMetaData.getConstructor().newInstance();
                    // список полей
                    List<Field> fields = entityClassMetaData.getAllFields();
                    // пробежимся по списку полей класса
                    for (Field field : fields) {
                        // делаем приватные поля видимыми
                        field.setAccessible(true);
                        // установим значения, передав в качестве параметра созданный экземпляр класса
                        field.set(model, rs.getObject(field.getName()));
                    }
                    // вернём экземпляр класса
                    return model;
                }
                return null;
            } catch (SQLException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectAllSql(), Collections.emptyList(), rs -> {
            var clientList = new ArrayList<T>();
            try {
                while (rs.next()) {
                    // создаём экземпляр класса
                    T model = entityClassMetaData.getConstructor().newInstance();
                    // список полей
                    List<Field> fields = entityClassMetaData.getAllFields();
                    // пробежимся по списку полей класса
                    for (Field field : fields) {
                        // делаем приватные поля видимыми
                        field.setAccessible(true);
                        // установим значения, передав в качестве параметра созданный экземпляр класса
                        field.set(model, rs.getObject(field.getName()));
                    }
                    clientList.add(model);
                }
                return clientList;
            } catch (SQLException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }).orElseThrow(() -> new RuntimeException("Unexpected error"));
    }

    @Override
    public long insert(Connection connection, T client) {
        // список полей БЕЗ id, так как значение ID генерируется сиквенсом в базе
        List<Field> fields = entityClassMetaData.getFieldsWithoutId();
        // параметры инстанса
        List<Object> params = new ArrayList();
        try {
            // пробежимся по списку полей класса для формирования списка параметров
            for (Field field : fields) {
                // делаем приватные поля видимыми
                field.setAccessible(true);
                params.add(field.get(client));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(), params);
    }

    @Override
    public void update(Connection connection, T client) {
        // список полей БЕЗ id, так как значение ID генерируется сиквенсом в базе
        List<Field> fields = entityClassMetaData.getFieldsWithoutId();
        // параметры инстанса
        List<Object> params = new ArrayList();
        try {
            // пробежимся по списку полей класса для формирования списка параметров
            for (Field field : fields) {
                // делаем приватные поля видимыми
                field.setAccessible(true);
                params.add(field.get(client));
            }
            // следом достанем параметр id
            Field fieldId = entityClassMetaData.getIdField();
            // делаем приватное поле видимым
            fieldId.setAccessible(true);
            // ID добавляем последним параметром
            params.add(fieldId.get(client));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(), params);
    }
}
