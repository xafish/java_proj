package ru.otus.model;

import java.io.*;

import java.util.List;

public class ObjectForMessage implements Serializable {
    private List<String> data;

    // конструктор по умолчанию
    public ObjectForMessage() {
    };

    // конструктор копия - для иммутабельности
    public ObjectForMessage(ObjectForMessage other) {
        this.data = other.getData();
    };

    // данные получаем копией для того, чтобы первичные данные остались неизменны
    public List<String> getData() {
        return List.copyOf(data);
    }


    public void setData(List<String> data) {
        this.data = data;
    }
}
