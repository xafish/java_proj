package webServerHomework.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import webServerHomework.crm.model.Adress;
import webServerHomework.crm.model.Phone;
import webServerHomework.crm.service.DBServiceClient;
import webServerHomework.crm.model.Client;

import java.util.List;
import java.util.Set;

@Controller
public class ClientController {


    private final DBServiceClient dbServiceClient;

    public ClientController(DBServiceClient dbServiceClient) {
        this.dbServiceClient = dbServiceClient;
    }

    @GetMapping({"/", "/client/list"})
    public String clientsListView(Model model) {
        List<Client> clients = dbServiceClient.findAll();
        model.addAttribute("clients", clients);
        clients.forEach(a->System.out.println(a.getPhones()));
        return "clients";
    }

    @PostMapping("/client/list")
    public RedirectView clientSave(@RequestParam(value = "name", required = false) String name,
                                    @RequestParam(value = "address", required = false) String address,
                                    @RequestParam(value = "phone", required = false) String phone) {
        var client = new Client(name, new Adress(address), Set.of(new Phone(phone)));
        dbServiceClient.saveClient(client);
        return new RedirectView("/", true);
    }

}
