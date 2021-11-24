package webServerHomework.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.otus.crm.model.Adress;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.crm.service.DBServiceClient;
import webServerHomework.dao.UserDao;
import webServerHomework.services.TemplateProcessor;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;


public class ClientsServlet extends HttpServlet {

    private static final String CLIENTS_PAGE_TEMPLATE = "clients.html";
    private static final String TEMPLATE_ATTR_CLIENTS = "clients";

    private static final String PARAM_CLIENT_NAME = "name";
    private static final String PARAM_CLIENT_ADDRESS = "address";
    private static final String PARAM_CLIENT_PHONE = "phone";

    private final DBServiceClient serviceClient;
    private final TemplateProcessor templateProcessor;

    public ClientsServlet(TemplateProcessor templateProcessor, DBServiceClient serviceClient) {
        this.templateProcessor = templateProcessor;
        this.serviceClient = serviceClient;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();
        // Список клиентов
        paramsMap.put(TEMPLATE_ATTR_CLIENTS, serviceClient.findAll());

        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(CLIENTS_PAGE_TEMPLATE, paramsMap));
    }

    // добавление клиента
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String name = request.getParameter(PARAM_CLIENT_NAME);
        String address = request.getParameter(PARAM_CLIENT_ADDRESS);
        String phone = request.getParameter(PARAM_CLIENT_PHONE);

        // добавление адреса
        var addressEks = new Adress(address);
        // создание экземпляра Client
        var clientEkz = new Client(name, addressEks);
        // добавление телефона
        clientEkz.addPhone(new Phone(phone));
        // сохранение клиента в БД
        serviceClient.saveClient(clientEkz);
        // переходим на страницу клиента, обновив значения
        response.sendRedirect("/clients");
    }

}
