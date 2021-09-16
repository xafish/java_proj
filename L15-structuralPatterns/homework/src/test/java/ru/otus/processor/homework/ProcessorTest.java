package ru.otus.processor.homework;


import org.junit.jupiter.api.Test;
import ru.otus.handler.ComplexProcessor;
import ru.otus.listener.ListenerPrinterConsole;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;
import ru.otus.processor.Processor;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class ProcessorTest {

    @Test
    void processorTest() {

        var id = 100L;
        var data = "33";
        var field13 = new ObjectForMessage();
        var field13Data = new ArrayList<String>();
        field13Data.add(data);
        field13.setData(field13Data);

        List<Processor> processors = List.of(new ProcessorEvenSecError());

        final String[] curSec = new String[1];
        var complexProcessor = new ComplexProcessor(processors,
                // обреботаем ошибку
                ex -> {System.out.println(ex);
                    // получаем сообщение об ошибке
                    var mes = ex.getMessage();
                    // получаем секунду и сохраняем её в массив
                    curSec[0] = mes.substring(mes.indexOf(":")+1, mes.length());
                });
        var listenerPrinter = new ListenerPrinterConsole();
        complexProcessor.addListener(listenerPrinter);

        var message = new Message.Builder(id)
                .field1("field1")
                .field2("field2")
                .field3("field3")
                .field6("field6")
                .field10("field10")
                .field11("field11")
                .field12("field12")
                .field13(field13)
                .build();

        var result = complexProcessor.handle(message);
        System.out.println("result:" + result);
        System.out.println("curSec[0]:" + curSec[0]);
        complexProcessor.removeListener(listenerPrinter);
        // проверим полученную секунду на чётность
        if (curSec[0] != null) {
            int sec = Integer.parseInt(curSec[0].trim());
            boolean res = (sec%2==0);
            assertThat(res).isTrue();
        }
    }
}