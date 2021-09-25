package ru.otus.dataprocessor;

import ru.otus.model.Measurement;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonStructure;
import javax.json.JsonWriter;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class FileSerializer implements Serializer {
    private final String fileName;

    public FileSerializer(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) {
        // формирует результирующий json и сохраняет его в файл
        // сформируем билдер, который будем использовать
        var jsonObject = Json.createObjectBuilder();
        data.forEach((str, val) -> {
            //System.out.println("str="+str);
            //System.out.println("val="+val);
            // добавим строку, удалив лишние кавычки в названии
            jsonObject.add(str.replace("\"", ""), val);
        });
        try (
            // создаём writer
            var jsonReader = Json.createWriter( new BufferedOutputStream(new FileOutputStream(fileName)))
        ){
            // пишем json объект в фаил
            jsonReader.writeObject(jsonObject.build());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            // вызываем пользовательскую ошибку
            throw new FileProcessException(e);
        }
    }
}
