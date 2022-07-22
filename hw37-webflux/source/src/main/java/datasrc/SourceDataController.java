package datasrc;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import datasrc.dbService.crm.model.Adress;
import datasrc.dbService.crm.model.Client;
import datasrc.dbService.crm.model.Phone;
import datasrc.dbService.crm.service.DBServiceClient;
import datasrc.producer.StringValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
public class SourceDataController {
    private static final Logger log = LoggerFactory.getLogger(SourceDataController.class);

    private final DBServiceClient dbServiceClient;

    private final ExecutorService executor = Executors.newFixedThreadPool(4);

    public SourceDataController(DBServiceClient dbServiceClient) {
        this.dbServiceClient = dbServiceClient;
    }

    @GetMapping(value = "/data-mono/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<Client>> dataMono() {
        log.info("Method request for string data done");

        var future = CompletableFuture
                .supplyAsync(() -> dbServiceClient.findAll(), executor);
        return Mono.fromFuture(future);
    }

    @PostMapping(value = "/data-mono/saveClient", produces = MediaType.APPLICATION_JSON_VALUE)
    public void clientSave(@RequestParam(value = "name", required = false) String name,
                                   @RequestParam(value = "address", required = false) String address,
                                   @RequestParam(value = "phone", required = false) String phone) {
        log.info("Method saveClient");
        var client = new Client(name, new Adress(address), Set.of(new Phone(phone)));
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        var future = CompletableFuture
                .supplyAsync(() -> dbServiceClient.saveClient(client), executor);
    }

}
