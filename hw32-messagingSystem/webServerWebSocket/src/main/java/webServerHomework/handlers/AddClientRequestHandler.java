package webServerHomework.handlers;

import ru.otus.messagesystem.RequestHandler;
import ru.otus.messagesystem.client.ResultDataType;
import ru.otus.messagesystem.message.Message;
import ru.otus.messagesystem.message.MessageBuilder;
import webServerHomework.convert.AllClientData;
import webServerHomework.crm.service.DBServiceClient;

import java.util.Optional;


public class AddClientRequestHandler implements RequestHandler {
    private final DBServiceClient dbServiceClient;

    public AddClientRequestHandler(DBServiceClient dbServiceClient) {
        this.dbServiceClient = dbServiceClient;
    }

    @Override
    public <T extends ResultDataType> Optional<Message<T>> handle(Message<T> msg) {
        var data = new AllClientData(dbServiceClient.findAll());
        return Optional.of(MessageBuilder.buildReplyMessage(msg, (T) data));
    }
}
