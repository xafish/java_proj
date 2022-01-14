package webServerHomework.db;

import webServerHomework.crm.model.Client;

import java.util.List;

public interface DBService {
    List<Client> getAllData();
}
