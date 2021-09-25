package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

public class ProcessorEvenSecError implements Processor {

    public static final String EVEN_SEC_EXCEPTION = "There is an even second";
    private final DateTimeProvider dateTimeProvider;

    // в конструктор добавим время запуска
    public ProcessorEvenSecError(DateTimeProvider dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
    }


    // Класс, который генерирует исключение в чётную секунду времени
    @Override
    public Message process(Message message) {
        // текущая секунда
        var curSec = dateTimeProvider.getDate().getSecond();
        // если секунда чётная - сгенерируем ошибку
        if (curSec%2==0) {
            System.out.println(EVEN_SEC_EXCEPTION);
            throw new RuntimeException(EVEN_SEC_EXCEPTION);
        }
        return message;

    }
}
