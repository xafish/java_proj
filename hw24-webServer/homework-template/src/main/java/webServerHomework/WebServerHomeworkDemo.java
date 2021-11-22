package webServerHomework;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.LoginService;
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
import ru.otus.demo.DbServiceDemo;
import webServerHomework.dao.InMemoryUserDao;
import webServerHomework.dao.UserDao;
import webServerHomework.helpers.FileSystemHelper;
import webServerHomework.server.HomeworkWebServer;
import webServerHomework.server.UsersWebServer;
import webServerHomework.server.UsersWebServerWithFilterBasedSecurity;
import webServerHomework.services.*;
import org.eclipse.jetty.security.*;

import java.io.*;
import java.nio.file.Files;
import java.util.Properties;

/*
    Полезные для демо ссылки

    // Стартовая страница
    http://localhost:8080

    // Страница пользователей
    http://localhost:8080/clients

*/
public class WebServerHomeworkDemo {
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";
    private static final String HASH_LOGIN_SERVICE_CONFIG_NAME = "admin.properties";
    private static final String REALM_NAME = "AnyRealm";

    // Hibernate
    private static final Logger log = LoggerFactory.getLogger(DbServiceDemo.class);
    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    public static void main(String[] args) throws Exception {
        // Hibernate
        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

        var dbUrl = configuration.getProperty("hibernate.connection.url");
        var dbUserName = configuration.getProperty("hibernate.connection.username");
        var dbPassword = configuration.getProperty("hibernate.connection.password");

        new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();

        var sessionFactory = HibernateUtils.buildSessionFactory(configuration, Client.class, Adress.class, Phone.class);
        var transactionManager = new TransactionManagerHibernate(sessionFactory);
        var clientTemplate = new DataTemplateHibernate<>(Client.class);
        var dbServiceClient = new DbServiceClientImpl(transactionManager, clientTemplate);

        // Добавим значения для примера
        // добавление улицы
        var address = new Adress("St. Patric street");
        // создание экземпляра Client
        var clientEkz = new Client("dbServiceFirst", address);
        // добавление нескольких телефонов
        clientEkz.addPhone(new Phone("89994444444"));
        clientEkz.addPhone(new Phone("89995555555"));
        clientEkz.addPhone(new Phone("89997777777"));
        // сохранение клиента в базе со всеми связанными сущностями
        dbServiceClient.saveClient(clientEkz);

        // берём пароль админа из файла в ресурсах
        String loginAdmin;
        String passwordAdmin;
        // файл, где будем хранить логин и пароль
        FileInputStream fileInputStream;
        // инициализируем объект Properties
        Properties prop = new Properties();
        try {
            // обращаемся к файлу и получаем данные
            fileInputStream = new FileInputStream(WebServerHomeworkDemo.class.getClassLoader().getResource(HASH_LOGIN_SERVICE_CONFIG_NAME).getPath());
            prop.load(fileInputStream);

            loginAdmin = prop.getProperty("login");
            passwordAdmin = prop.getProperty("password");

        } catch (IOException e) {
            System.out.println("Ошибка в программе: файла " + HASH_LOGIN_SERVICE_CONFIG_NAME + " не обнаружено");
            throw new RuntimeException(e);
        }
        // Сервис для авторизации
        ClientAuthServiceImpl authService = new ClientAuthServiceImpl(loginAdmin,passwordAdmin);

        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);
        // передаём в вэб. сервер экземпляр dbServiceClient для работы с базой в сервлетах
        UsersWebServer usersWebServer = new HomeworkWebServer(WEB_SERVER_PORT,
                authService, dbServiceClient, templateProcessor);

        usersWebServer.start();
        usersWebServer.join();
    }
}
