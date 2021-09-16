package ru.otus;

import ru.otus.handler.ComplexProcessor;
import ru.otus.listener.ListenerPrinterConsole;
import ru.otus.listener.homework.HistoryListener;
import ru.otus.model.Message;
import ru.otus.processor.Processor;
import ru.otus.processor.homework.ProcessorChangeField11Field12;
import ru.otus.processor.homework.ProcessorEvenSecError;

import java.util.List;

public class HomeWork {

    /*
     Реализовать to do:
       1. Добавить поля field11 - field13 (для field13 используйте класс ObjectForMessage)
       2. Сделать процессор, который поменяет местами значения field11 и field12
       3. Сделать процессор, который будет выбрасывать исключение в четную секунду (сделайте тест с гарантированным результатом)
            Секунда должна определяьться во время выполнения.
       4. Сделать Listener для ведения истории (подумайте, как сделать, чтобы сообщения не портились)
          Уже есть заготовка - класс HistoryListener, надо сделать его реализацию
          Для него уже есть тест, убедитесь, что тест проходит
     */

    public static void main(String[] args) {
        /*
           по аналогии с Demo.class
           из элеменов "to do" создать new ComplexProcessor и обработать сообщение
         */
        List<Processor> processors = List.of(new ProcessorChangeField11Field12(), new ProcessorEvenSecError());

        final String[] curSec = new String[1];
        var complexProcessor = new ComplexProcessor(processors, ex -> {System.out.println(ex);
            // получаем сообщение об ошибке
            var mes = ex.getMessage();
            // получаем секунду
            curSec[0] = mes.substring(mes.indexOf(":")+1, mes.length());
        });
        var listenerPrinter = new ListenerPrinterConsole();
        complexProcessor.addListener(listenerPrinter);
        // тестирование historyListener
        var historyListener = new HistoryListener();
        complexProcessor.addListener(historyListener);

        var message = new Message.Builder(1L)
                .field1("field1")
                .field2("field2")
                .field3("field3")
                .field6("field6")
                .field10("field10")
                .field11("field11")
                .field12("field12")
                .build();

        var result = complexProcessor.handle(message);
        System.out.println("result:" + result);

        var messageFromHistory = historyListener.findMessageById(1L);
        System.out.println(messageFromHistory);
        complexProcessor.removeListener(listenerPrinter);

    }
}
