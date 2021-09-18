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

        var processorEvenSecError = new ProcessorEvenSecError();

        List<Processor> processors = List.of(processorEvenSecError);

        // список ошибок четных секунд
        final ArrayList<String> evenErrList = new ArrayList<>();
        var complexProcessor = new ComplexProcessor(processors,
                // обреботаем ошибку
                ex -> {System.out.println(ex);
                    // получаем сообщение об ошибке
                    var mes = ex.getMessage();
                    // если ошибка чётной секунды
                    if (mes.equals(ProcessorEvenSecError.EVEN_SEC_EXCEPTION)) {
                        // сохраняем ошибку в список
                        evenErrList.add(mes);
                    }
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
        // получаем секунду запуска из лога по классу нужного нам процессора
        var launchSec = complexProcessor.getLog(processorEvenSecError.getClass()).getDate().getSecond();
        System.out.println("launchSec:" + launchSec);
        System.out.println("result:" + result);
        // Была ли ошибка в чётную секунду(и не было ли её в нечётную)
        boolean res = (evenErrList.isEmpty() && !(launchSec%2==0)) || (!evenErrList.isEmpty() && launchSec%2==0);
        assertThat(res).isTrue();
        complexProcessor.removeListener(listenerPrinter);
    }
}