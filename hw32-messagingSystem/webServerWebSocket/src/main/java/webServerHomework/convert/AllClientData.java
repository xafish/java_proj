package webServerHomework.convert;

import ru.otus.messagesystem.client.ResultDataType;
import webServerHomework.crm.model.Client;

import java.util.List;

public class AllClientData implements ResultDataType {
    private final List<Client> data;

    public AllClientData() {
        this.data = null;
    }

    public AllClientData(List<Client> data) {
        this.data = data;
    }

    public List<Client> getData() {
        return data;
    }

    @Override
    public String toString() {
        return "AllClientData{" +
                "data='" + data + '\'' +
                '}';
    }
}
