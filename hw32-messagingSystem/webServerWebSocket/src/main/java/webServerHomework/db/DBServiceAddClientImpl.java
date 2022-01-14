package webServerHomework.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webServerHomework.crm.model.Client;
import webServerHomework.crm.service.DBServiceClient;

import java.util.ArrayList;
import java.util.List;


public class DBServiceAddClientImpl implements DBService {
    private static final Logger logger = LoggerFactory.getLogger(DBServiceAddClientImpl.class);
    private final List<Client> dbData = new ArrayList<>();

    private final DBServiceClient dbServiceClient;

    public DBServiceAddClientImpl(DBServiceClient dbServiceClient, Client client) {
        this.dbServiceClient = dbServiceClient;
        this.dbData.add(dbServiceClient.saveClient(client));
    }

    public List<Client> getAllData() {
        logger.info("get data");
        return dbData;
    }

}
