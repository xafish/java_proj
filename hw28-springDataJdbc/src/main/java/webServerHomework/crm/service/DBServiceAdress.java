package webServerHomework.crm.service;

import webServerHomework.crm.model.Adress;

import java.util.List;

public interface DBServiceAdress {

    Adress saveAdress(Adress client);

    //Optional<Adress> getAdress(long id);

    List<Adress> findAll();
}
