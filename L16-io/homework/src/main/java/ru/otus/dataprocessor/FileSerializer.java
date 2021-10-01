package ru.otus.dataprocessor;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

public class FileSerializer implements Serializer {
    private final String fileName;
    private final ObjectMapper mapper = new ObjectMapper();

    public FileSerializer(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) {
        // формирует результирующий json и сохраняет его в файл
        // сформируем билдер, который будем использовать
        /*var jsonObject = Json.createObjectBuilder();
        data.forEach((str, val) -> {
            //System.out.println("str="+str);
            //System.out.println("val="+val);
            // добавим строку, удалив лишние кавычки в названии
            jsonObject.add(str.replace("\"", ""), val);
            //jsonObject.add(str, val);
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
        }*/
        // пишем мапу в фаил
        try {
            var file = new File(fileName);
            Files.writeString(file.toPath(), mapper.writeValueAsString(data));
        } catch (IOException e) {
            e.printStackTrace();
            // вызываем пользовательскую ошибку
            throw new FileProcessException(e);
        }

    }
}
