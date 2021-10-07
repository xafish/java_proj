package ru.otus.jdbc.mapper;

import ru.otus.crm.annotations.Id;

import java.lang.reflect.Field;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData {
    private final Class<T> targetClass;

    public EntityClassMetaDataImpl(Class<T> targetClass) {
        this.targetClass = targetClass;
    }

    // возврат имени
    @Override
    public String getName() {
        return targetClass.getSimpleName();
    };

    // получение конструктора объекта
    @Override
    public Constructor<T> getConstructor() {
        try {
            return targetClass.getConstructor();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            // вызываем ошибку
            throw new RuntimeException(e);
        }

    };

    //Поле Id должно определять по наличию аннотации Id
    //Аннотацию @Id надо сделать самостоятельно
    @Override
    public Field getIdField() {
        return  Arrays.stream(targetClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .get();
    };

    @Override
    public List<Field> getAllFields() {
        List<Field> res = Arrays.asList(targetClass.getDeclaredFields());// = Arrays.stream(obj.getClass().getDeclaredFields()).collect(Collectors.toList());
        return res;
    };

    @Override
    public List<Field> getFieldsWithoutId() {
        return Arrays.stream(targetClass.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Id.class))
                .collect(Collectors.toList());
    };
}
