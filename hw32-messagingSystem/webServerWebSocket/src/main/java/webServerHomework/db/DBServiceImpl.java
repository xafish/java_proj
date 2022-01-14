package webServerHomework.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webServerHomework.convert.AllClientData;
import webServerHomework.crm.model.Client;
import webServerHomework.crm.service.DBServiceClient;

import java.util.List;


public class DBServiceImpl implements DBService {
    private static final Logger logger = LoggerFactory.getLogger(DBServiceImpl.class);
    private final List<Client> dbData;

    private final DBServiceClient dbServiceClient;

    public DBServiceImpl(DBServiceClient dbServiceClient) {
        this.dbServiceClient = dbServiceClient;
        this.dbData = dbServiceClient.findAll();
    }

    public List<Client> getAllData() {
        logger.info("get data");
        return dbData;
    }

}
