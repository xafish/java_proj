package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

public class ProcessorChangeField11Field12 implements Processor {

    // Класс, который меняет поля 11 и 12 местами
    @Override
    public Message process(Message message) {
        var pField11 = message.getField11();
        var pField12 = message.getField12();
        return message.toBuilder().field11(pField12).field12(pField11).build();
    }
}
