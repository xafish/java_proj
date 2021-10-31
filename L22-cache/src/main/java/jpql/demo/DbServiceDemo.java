package jpql.demo;

import jpql.core.repository.DataTemplateHibernate;
import jpql.core.repository.HibernateUtils;
import jpql.core.sessionmanager.TransactionManagerHibernate;
import jpql.crm.dbmigrations.MigrationsExecutorFlyway;
import jpql.crm.model.*;
import jpql.crm.service.DbServiceClientImpl;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.references.ReferenceDemo;

import javax.persistence.*;
import java.util.ArrayList;

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
/// Тестирование КЭШа
        var dbServiceClient = new DbServiceClientImpl(transactionManager, clientTemplate);
        // список id созданных клиентов
        ArrayList<Long> clientIds = new ArrayList();
        Long clientId;
        // сохдаём 100 клиентов
        for (var idx = 0; idx < 100; idx++) {
            // добавление улицы
            var adress = new Adress("St. Patric street" + idx);
            // создание экземпляра Client
            Client clientEkz = new Client("dbServiceFirst"+ idx, adress);
            // добавление нескольких телефонов
            clientEkz.addPhone(new Phone("89994444444"+ idx));
            clientEkz.addPhone(new Phone("89995555555"+ idx));
            clientEkz.addPhone(new Phone("89997777777"+ idx));
            // сохранение клиента в базе со всеми связанными сущностями(в нём же и происходит КЭШирование)
            clientId = dbServiceClient.saveClient(clientEkz).getId();
            clientIds.add(clientId);
        }
        // Попытка чтения списка клиентов БЕЗ кэша
        log.info("Start without cahse");
        clientIds.forEach(cId -> {log.info(dbServiceClient.getClient(cId).get().getName());});
        log.info("End without cahse");
        // Попытка чтения списка клиентов С кэшом
        log.info("Start with cahse");
        clientIds.forEach(cId -> {log.info(dbServiceClient.getClientWithCache(cId).get().getName());});
        log.info("End with cahse");
        // Попытка чтения списка клиентов С кэшом НО после отчистки GC
        System.gc();
        log.info("Start with cahse after clear GC");
        clientIds.forEach(cId -> {log.info(dbServiceClient.getClientWithCache(cId).get().getName());});
        log.info("End with cahse after clear GC");
    }
}
