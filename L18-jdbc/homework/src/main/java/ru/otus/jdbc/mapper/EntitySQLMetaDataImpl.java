package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {
    private final EntityClassMetaData metaData;
    // список полей
    private final String fieldsList;
    private final String fieldsListWoId;

    public EntitySQLMetaDataImpl(EntityClassMetaData metaData) {
        this.metaData = metaData;
        // получим все поля
        List<Field> fields = metaData.getAllFields();
        // преобразуем полученные поля в строку значений
        this.fieldsList = fields.stream()
                .map(Field::getName)
                .collect(Collectors.joining(", "));
        // получим поля без ID
        List<Field> fieldsWoId = metaData.getFieldsWithoutId();
        // преобразуем полученные поля в строку значений
        this.fieldsListWoId = fieldsWoId.stream()
                .map(Field::getName)
                .collect(Collectors.joining(", "));
    }

    @Override
    public String getSelectAllSql() {
        return "select " + fieldsList + " from " + metaData.getName();
    }

    @Override
    public String getSelectByIdSql() {
        return  "select " + fieldsList + " from " + metaData.getName() + " where " + metaData.getIdField().getName() + " = ?";
    }

    @Override
    public String getInsertSql() {
        // тут стоит пояснить строку в values
        // 1) по строковому списку полей удаляем с помощью регулярки все символы, кроме ","
        // 2) в получившейся строке добавляет "?" перед каждой ","
        // 3) добавляем недостающий "?"
        return "insert into " + metaData.getName() + "("+fieldsListWoId+") values(" + fieldsListWoId.replaceAll("[^,]", "").replace(",","?,").concat("?") +")";
    }

    @Override
    public String getUpdateSql() {
        // получим все поля
        List<Field> fields = metaData.getFieldsWithoutId();
        // преобразуем полученные поля в строку значений
        String fieldsList = fields.stream()
                .map(Field::getName)
                .collect(Collectors.joining("=?, "));
        fieldsList = fieldsList.concat("=?");
        return "update "+ metaData.getName() +" set " + fieldsList + " where " + metaData.getIdField().getName() + " = ?";
    }
}
