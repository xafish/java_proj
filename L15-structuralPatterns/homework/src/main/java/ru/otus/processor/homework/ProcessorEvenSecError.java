package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

import java.time.*;

public class ProcessorEvenSecError implements Processor {
    private int curSec;
    private String errMes;

    // Класс, который генерирует исключение в чётную секунду времени
    @Override
    public Message process(Message message) {
        // значение временной зоны по умолчанию(нам она и не важна)
        ZoneId zone = ZoneId.systemDefault();
        // текущая секунда
        curSec = ZonedDateTime.now( zone ).getSecond() ;
        // если число чётное - сгенерируем ошибку
        if (curSec%2==0) {
            System.out.println("There is an even second!");
            throw new RuntimeException("There is an even second:"+curSec);
        }
        return message;


    }
}
