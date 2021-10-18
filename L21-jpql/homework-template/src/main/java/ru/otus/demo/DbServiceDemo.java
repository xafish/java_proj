package ru.otus.demo;

import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.repository.DataTemplateHibernate;
import ru.otus.core.repository.HibernateUtils;
import ru.otus.core.sessionmanager.TransactionManagerHibernate;
import ru.otus.crm.dbmigrations.MigrationsExecutorFlyway;
import ru.otus.crm.model.Adress;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.crm.service.DbServiceClientImpl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DbServiceDemo {

    private static final Logger log = LoggerFactory.getLogger(DbServiceDemo.class);

    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    public static void main(String[] args) {
        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

        var dbUrl = configuration.getProperty("hibernate.connection.url");
        var dbUserName = configuration.getProperty("hibernate.connection.username");
        var dbPassword = configuration.getProperty("hibernate.connection.password");

        new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();

        var sessionFactory = HibernateUtils.buildSessionFactory(configuration, Client.class, Adress.class, Phone.class);

        var transactionManager = new TransactionManagerHibernate(sessionFactory);
///
        var clientTemplate = new DataTemplateHibernate<>(Client.class);
///
        var dbServiceClient = new DbServiceClientImpl(transactionManager, clientTemplate);
        // добавление улицы
        var adress = new Adress("St. Patric street");
        // создание экземпляра Client
        var clientEkz = new Client("dbServiceFirst", adress);
        // добавление нескольких телефонов
        clientEkz.addPhone(new Phone("89994444444"));
        clientEkz.addPhone(new Phone("89995555555"));
        clientEkz.addPhone(new Phone("89997777777"));
        // сохранение клиента в базе со всеми связанными сущностями
        dbServiceClient.saveClient(clientEkz);

        var adress2 = new Adress("Elm street");
        var clientEkz2 = new Client("dbServiceSecond", adress2);
        clientEkz2.addPhone(new Phone("89996666666"));
        var clientSecond = dbServiceClient.saveClient(clientEkz2);
        var clientSecondSelected = dbServiceClient.getClient(clientSecond.getId())
                .orElseThrow(() -> new RuntimeException("Client not found, id:" + clientSecond.getId()));
        log.info("clientSecondSelected:{}", clientSecondSelected);


///
        log.info("clientSecondSelected.getPhones():{}", clientSecondSelected.getPhones());
        dbServiceClient.saveClient(new Client(clientSecondSelected.getId(), "dbServiceSecondUpdated", clientSecondSelected.getAdress(), clientSecondSelected.getPhones()));
        var clientUpdated = dbServiceClient.getClient(clientSecondSelected.getId())
                .orElseThrow(() -> new RuntimeException("Client not found, id:" + clientSecondSelected.getId()));
        log.info("clientUpdated:{}", clientUpdated);

        log.info("All clients");
        dbServiceClient.findAll().forEach(client -> log.info("client:{}", client));
    }
}
