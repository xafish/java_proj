package webServerHomework.handlers;

import ru.otus.messagesystem.RequestHandler;
import ru.otus.messagesystem.client.ResultDataType;
import ru.otus.messagesystem.message.Message;
import ru.otus.messagesystem.message.MessageBuilder;
import webServerHomework.convert.AllClientData;
import webServerHomework.db.DBService;

import java.util.Optional;


public class GetClientDataRequestHandler implements RequestHandler {
    private final DBService dbService;

    public GetClientDataRequestHandler(DBService dbService) {
        this.dbService = dbService;
    }

    @Override
    public <T extends ResultDataType> Optional<Message<T>> handle(Message<T> msg) {
        var data = new AllClientData(dbService.getAllData());
        return Optional.of(MessageBuilder.buildReplyMessage(msg, (T) data));
    }
}
