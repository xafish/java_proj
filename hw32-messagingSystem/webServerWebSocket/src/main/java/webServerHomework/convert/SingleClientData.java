package webServerHomework.convert;

import ru.otus.messagesystem.client.ResultDataType;
import webServerHomework.crm.model.Client;

import java.util.List;

public class SingleClientData implements ResultDataType {
    private final Client data;

    public SingleClientData() {
        this.data = null;
    }

    public SingleClientData(Client data) {
        this.data = data;
    }

    public Client getData() {
        return data;
    }

    @Override
    public String toString() {
        return "SingleClientData{" +
                "data='" + data + '\'' +
                '}';
    }
}
