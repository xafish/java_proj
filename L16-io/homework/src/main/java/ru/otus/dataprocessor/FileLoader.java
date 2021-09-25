package ru.otus.dataprocessor;

import ru.otus.model.Measurement;

import javax.json.JsonArray;
import javax.json.Json;
import javax.json.JsonStructure;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class FileLoader implements Loader {
    private final String fileName;

    public FileLoader(String fileName) {
        // указываем путь к ресурсам
        this.fileName = "src\\test\\resources\\"+fileName;
    }

    @Override
    public List<Measurement> load() {
        //читает файл, парсит и возвращает результат
        try (
             // создаём ридер из потока байт по переданному текстовому файлу
             var jsonReader = Json.createReader( new BufferedInputStream(new FileInputStream(fileName)))
        ){
            // массив результатов
            final List<Measurement> res = new ArrayList<>();
            // читаем данные из ридера
            JsonStructure jsonFromTheFile = jsonReader.read();
            // пробегаем по полученным данным, как по массиву
            jsonFromTheFile.asJsonArray().stream().forEach(str -> {
                // извлекаем данные каждой строки массива
                JsonStructure strJsonFromTheFile = str.asJsonObject();
                String name = strJsonFromTheFile.getValue("/name").toString();
                double value = Double.parseDouble(strJsonFromTheFile.getValue("/value").toString());
                //System.out.println("name:" + name);
                //System.out.println("value:" + value);
                // добавляем данные в результат
                res.add(new Measurement(
                        name,
                        value
                        )
                );
            });
            return res;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            // вызываем пользовательскую ошибку
            throw new FileProcessException(e);
        }
    }
}
