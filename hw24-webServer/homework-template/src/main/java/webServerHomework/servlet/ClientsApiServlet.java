package webServerHomework.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.otus.crm.service.DBServiceClient;
import webServerHomework.dao.UserDao;
import webServerHomework.model.User;

import java.io.IOException;


public class ClientsApiServlet extends HttpServlet {

    private static final int ID_PATH_PARAM_POSITION = 1;

    private final DBServiceClient serviceClient;

    public ClientsApiServlet(DBServiceClient serviceClient) {
        this.serviceClient = serviceClient;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //User user = userDao.findById(extractIdFromRequest(request)).orElse(null);
        extractIdFromRequest(request);
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = response.getOutputStream();
        //out.print(gson.toJson(user));
        // переходим на страницу клиента, обновив значения на ней
        response.sendRedirect("/clients");
    }

    private long extractIdFromRequest(HttpServletRequest request) {
        String[] path = request.getPathInfo().split("/");
        String id = (path.length > 1)? path[ID_PATH_PARAM_POSITION]: String.valueOf(- 1);
        System.out.println(id);
        return Long.parseLong(id);
    }

}
