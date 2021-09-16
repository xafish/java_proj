package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.model.Message;

import java.util.*;

public class HistoryListener implements Listener, HistoryReader {
    private NavigableMap<Long, Message> loglist = new TreeMap<>(Comparator.comparingLong(o ->o));

    @Override
    public void onUpdated(Message msg) {
        // используем конструктор-копию
        Message tmpRec = new Message(msg);
        // создаём запись
        loglist.put(msg.getId(), tmpRec);
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        // проерка для предотвращения nullPointer
        if (loglist.isEmpty()) {
            return Optional.empty();
        } else{
            return Optional.of(loglist.get(id));
        }
    }
}
