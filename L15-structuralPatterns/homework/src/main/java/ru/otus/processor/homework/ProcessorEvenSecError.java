package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

import java.time.LocalDateTime;

public class ProcessorEvenSecError implements Processor {

    public static final String EVEN_SEC_EXCEPTION = "There is an even second";
    //private final DateTimeProvider dateTimeProvider;

    // Класс, который генерирует исключение в чётную секунду времени
    @Override
    public Message process(Message message) {
        // текущая секунда
        var curSec = LocalDateTime.now().getSecond();
        // если секунда чётная - сгенерируем ошибку
        if (curSec%2==0) {
            System.out.println(EVEN_SEC_EXCEPTION);
            throw new RuntimeException(EVEN_SEC_EXCEPTION);
        }
        return message;

    }
}
