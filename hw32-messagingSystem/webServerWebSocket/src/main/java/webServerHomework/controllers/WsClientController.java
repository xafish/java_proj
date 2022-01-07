package webServerHomework.controllers;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import ru.otus.messagesystem.HandlersStoreImpl;
import ru.otus.messagesystem.MessageSystemImpl;
import ru.otus.messagesystem.client.MessageCallback;
import ru.otus.messagesystem.client.MsClientImpl;
import ru.otus.messagesystem.message.MessageType;
import webServerHomework.convert.AllClientData;
import webServerHomework.crm.model.Adress;
import webServerHomework.crm.model.Client;
import webServerHomework.crm.model.Phone;
import webServerHomework.crm.service.DBServiceClient;
import webServerHomework.db.DBServiceAddClientImpl;
import webServerHomework.db.DBServiceImpl;
import webServerHomework.handlers.GetClientDataRequestHandler;
import webServerHomework.handlers.GetDataResponseHandler;

import java.util.List;
import java.util.Set;


@Controller
public class WsClientController {
    private static final Logger logger = LoggerFactory.getLogger(WsClientController.class);

    private final DBServiceClient dbServiceClient;

    private static final String DATABASE_SERVICE_CLIENT_NAME = "databaseService";
    private static final String FRONTEND_SERVICE_CLIENT_NAME = "frontendService";

    public WsClientController(DBServiceClient dbServiceClient) {
        this.dbServiceClient = dbServiceClient;
    }

    @Autowired
    private SimpMessagingTemplate template;

    @MessageMapping("/clientSave")
    public void clientSave(String clientStr) throws JSONException {
        JSONObject clientJson = new JSONObject(clientStr);
        var client = new Client(clientJson.get("name").toString(), new Adress(clientJson.get("address").toString()), Set.of(new Phone(clientJson.get("phone").toString())));

        logger.info("save client:{}", client);

        MessageSystemImpl messageSystem = new MessageSystemImpl();

        var requestHandlerDatabaseStore = new HandlersStoreImpl();
        requestHandlerDatabaseStore.addHandler(MessageType.USER_DATA, new GetClientDataRequestHandler(new DBServiceAddClientImpl(dbServiceClient,client)));
        var databaseMsClient = new MsClientImpl(DATABASE_SERVICE_CLIENT_NAME, messageSystem, requestHandlerDatabaseStore);
        messageSystem.addClient(databaseMsClient);

        var requestHandlerFrontendStore = new HandlersStoreImpl();
        requestHandlerFrontendStore.addHandler(MessageType.USER_DATA, new GetDataResponseHandler());
        var frontendMsClient = new MsClientImpl(FRONTEND_SERVICE_CLIENT_NAME, messageSystem, requestHandlerFrontendStore);

        messageSystem.addClient(frontendMsClient);

        MessageCallback<AllClientData> dataConsumer = data -> {
            logger.info("got data:{}", data);
            template.convertAndSend("/topic/response", data.getData());
            try {
                messageSystem.dispose();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        var outMsg = frontendMsClient.produceMessage(DATABASE_SERVICE_CLIENT_NAME, new AllClientData(),
                MessageType.USER_DATA, dataConsumer);
        frontendMsClient.sendMessage(outMsg);

        logger.info("done");
    }

    @MessageMapping("/clientLoad")
    public void clientLoad() {
        MessageSystemImpl messageSystem = new MessageSystemImpl();

        var requestHandlerDatabaseStore = new HandlersStoreImpl();
        requestHandlerDatabaseStore.addHandler(MessageType.USER_DATA, new GetClientDataRequestHandler(new DBServiceImpl(dbServiceClient)));
        var databaseMsClient = new MsClientImpl(DATABASE_SERVICE_CLIENT_NAME, messageSystem, requestHandlerDatabaseStore);
        messageSystem.addClient(databaseMsClient);

        var requestHandlerFrontendStore = new HandlersStoreImpl();
        requestHandlerFrontendStore.addHandler(MessageType.USER_DATA, new GetDataResponseHandler());
        var frontendMsClient = new MsClientImpl(FRONTEND_SERVICE_CLIENT_NAME, messageSystem, requestHandlerFrontendStore);
        messageSystem.addClient(frontendMsClient);

        MessageCallback<AllClientData> dataConsumer = data -> {
            logger.info("got data:{}", data);
            template.convertAndSend("/topic/load", data.getData());
            try {
                messageSystem.dispose();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        var outMsg = frontendMsClient.produceMessage(DATABASE_SERVICE_CLIENT_NAME, new AllClientData(),
                MessageType.USER_DATA, dataConsumer);
        frontendMsClient.sendMessage(outMsg);

        logger.info("done");
    }

}
