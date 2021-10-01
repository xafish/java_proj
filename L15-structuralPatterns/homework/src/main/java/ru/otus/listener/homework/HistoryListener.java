package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.model.Message;

import java.util.*;

public class HistoryListener implements Listener, HistoryReader {
    //private NavigableMap<Long, Message> loglist = new TreeMap<>(Comparator.comparingLong(o ->o));
    private HashMap<Long, Message> loglist = new HashMap<>();

    @Override
    public void onUpdated(Message msg) {
        // используем конструктор-копию
        //Message tmpRec = new Message(msg);
        // Используем метод clone
        Message tmpRec = msg.clone();
        // создаём запись
        loglist.put(msg.getId(), tmpRec);
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return Optional.ofNullable(loglist.get(id));
    }
}
