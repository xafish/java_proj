package webServerHomework;

import webServerHomework.crm.model.Adress;
import webServerHomework.crm.model.Client;
import webServerHomework.crm.model.Phone;
import webServerHomework.crm.repository.AdressRepository;
import webServerHomework.crm.repository.ClientRepository;
import webServerHomework.crm.repository.PhoneRepository;
import webServerHomework.crm.service.DBServiceAdress;
import webServerHomework.crm.service.DBServiceClient;
import webServerHomework.crm.service.DBServicePhone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Set;


@Component("actionDemo")
public class ActionDemo {
    private static final Logger log = LoggerFactory.getLogger(ActionDemo.class);

    private final ClientRepository clientRepository;
    //private final ManagerRepository managerRepository;
    private final PhoneRepository phoneRepository;
    private final AdressRepository adressRepository;
    private final DBServiceClient dbServiceClient;
    //private final DBServiceManager dbServiceManager;
    private final DBServicePhone dbServicePhone;
    private final DBServiceAdress dbServiceAdress;

    public ActionDemo(ClientRepository clientRepository, PhoneRepository phoneRepository, AdressRepository adressRepository,
                      DBServiceClient dbServiceClient, DBServicePhone dbServicePhone, DBServiceAdress dbServiceAdress) {
        this.clientRepository = clientRepository;
        this.phoneRepository = phoneRepository;
        this.adressRepository = adressRepository;
        this.dbServiceClient = dbServiceClient;
        this.dbServicePhone = dbServicePhone;
        this.dbServiceAdress = dbServiceAdress;
    }

    void action() {

//// создаем Manager
        // Добавим значения для примера
        // добавление улицы
        var address = new Adress("St. Patric street");
        // создание экземпляра Client
        var clientEkz = new Client("dbServiceFirst", address, Set.of(new Phone("89994444444"), new Phone("89995555555"), new Phone("89997777777")));
        // добавление нескольких телефонов
        /*clientEkz.addPhone(new Phone("89994444444"));
        clientEkz.addPhone(new Phone("89995555555"));
        clientEkz.addPhone(new Phone("89997777777"));*/

        log.info(">>> manager creation");
        dbServiceClient.saveClient(clientEkz);
        dbServiceClient.findAll();
        /*dbServiceManager.saveManager(new Manager("m:" + System.currentTimeMillis(), "ManagerFirst", new HashSet<>(), new ArrayList<>(), true));


        var managerName = "m:" + System.currentTimeMillis();
        var managerSecond = dbServiceManager.saveManager(new Manager(managerName, "ManagerSecond",
                Set.of(new Client("managClient1", managerName, 1), new Client("managClient2", managerName, 2)), new ArrayList<>(), true));
        var managerSecondSelected = dbServiceManager.getManager(managerSecond.getId())
                .orElseThrow(() -> new RuntimeException("Manager not found, id:" + managerSecond.getId()));
        log.info(">>> managerSecondSelected:{}", managerSecondSelected);*/

//// обновляем Manager с удалением его клиентов
        /*dbServiceManager.saveManager(new Manager(managerSecondSelected.getId(), "dbServiceSecondUpdated", new HashSet<>(), new ArrayList<>(), false));
        var managerUpdated = dbServiceManager.getManager(managerSecondSelected.getId())
                .orElseThrow(() -> new RuntimeException("Manager not found, id:" + managerSecondSelected.getId()));
        log.info(">>> managerUpdated:{}", managerUpdated);

/// создаем Client
        var firstClient = dbServiceClient.saveClient(new Client("dbServiceFirst" + System.currentTimeMillis(), managerSecond.getId(), 1));

        var clientSecond = dbServiceClient.saveClient(new Client("dbServiceSecond" + System.currentTimeMillis(), managerSecond.getId(), 2));
        var clientSecondSelected = dbServiceClient.getClient(clientSecond.getId())
                .orElseThrow(() -> new RuntimeException("Client not found, id:" + clientSecond.getId()));
        log.info(">>> clientSecondSelected:{}", clientSecondSelected);

/// обновляем Client
        dbServiceClient.saveClient(new Client(clientSecondSelected.getId(), "dbServiceSecondUpdated", managerSecond.getId(), clientSecondSelected.getOrderColumn()));
        var clientUpdated = dbServiceClient.getClient(clientSecondSelected.getId())
                .orElseThrow(() -> new RuntimeException("Client not found, id:" + clientSecondSelected.getId()));
        log.info("clientUpdated:{}", clientUpdated);


/// получаем все сущности
        log.info(">>> All clients");
        dbServiceClient.findAll().forEach(client -> log.info("client:{}", client));

        log.info(">>> All managers");
        dbServiceManager.findAll().forEach(manager -> log.info("manager:{}", manager));

/// применяем переопределенные методы репозитариев
        var clientFoundByName = clientRepository.findByName(firstClient.getName())
                .orElseThrow(() -> new RuntimeException("client not found, name:" + firstClient.getName()));
        log.info("clientFoundByName:{}", clientFoundByName);

        var clientFoundByNameIgnoreCase = clientRepository.findByNameIgnoreCase(firstClient.getName().toLowerCase())
                .orElseThrow(() -> new RuntimeException("client not found, name:" + firstClient.getName()));
        log.info("clientFoundByNameIgnoreCase:{}", clientFoundByNameIgnoreCase);

        clientRepository.updateName(firstClient.getId(), "newName");
        var updatedClient = clientRepository.findById(firstClient.getId())
                .orElseThrow(() -> new RuntimeException("client not found"));

        log.info(">>> updatedClient:{}", updatedClient);

/// проверяем проблему N+1
        log.info(">>> checking N+1 problem");
        var managerN = managerRepository.findById(managerSecond.getId())
                .orElseThrow(() -> new RuntimeException("Manager not found, name:" + managerSecond.getId()));
        log.info(">>> managerN:{}", managerN);*/

        /*
        получаем основную сущность:
        [SELECT "manager"."id" AS "id", "manager"."label" AS "label" FROM "manager" WHERE "manager"."id" = ?]
        получаем дочерние:
        [SELECT "client"."id" AS "id", "client"."name" AS "name", "client"."manager_id" AS "manager_id" FROM "client" WHERE "client"."manager_id" = ?]
        */
        /*log.info(">>> select all");
        var allManagers = managerRepository.findAll();
        log.info(">>> allManagers.size():{}", allManagers.size());
        log.info(">>> allManagers:{}", allManagers);*/
    }
}
