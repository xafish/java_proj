package ru.otus.handler;

import ru.otus.listener.Listener;
import ru.otus.model.Message;
import ru.otus.processor.Processor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class ComplexProcessor implements Handler {
//new HistoryListener()
    private final List<Listener> listeners = new ArrayList<>();
    private final List<Processor> processors;
    private final Consumer<Exception> errorHandler;
    // лог запуска процессоров
    //private final List<DateTimeProvider> dateTimeProvider = new ArrayList<>();
    private final HashMap<Object,DateTimeProvider> logLaunch = new HashMap<>();
    //private final DateTimeProvider dateTimeProvider  = new ArrayList<>();

    public ComplexProcessor(List<Processor> processors, Consumer<Exception> errorHandler) {
        this.processors = processors;
        this.errorHandler = errorHandler;
    }

    @Override
    public Message handle(Message msg) {
        Message newMsg = msg;
        for (Processor pros : processors) {
            // логируем запуск
            logLaunch.put(pros.getClass(),LocalDateTime::now);
            System.out.println("logLaunch="+logLaunch.get(pros.getClass()));
            try {
                newMsg = pros.process(newMsg);
            } catch (Exception ex) {
                errorHandler.accept(ex);
            }
        }
        notify(newMsg);
        return newMsg;
    }

    @Override
    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(Listener listener) {
        listeners.remove(listener);
    }

    public Consumer<Exception> getErrorHandler() {
        return this.errorHandler;
    }

    private void notify(Message msg) {
        listeners.forEach(listener -> {
            try {
                listener.onUpdated(msg);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    // получение времени запуска по классу процессора
    public DateTimeProvider getLog(Object prosClass) {
        return logLaunch.get(prosClass);
    }
}
