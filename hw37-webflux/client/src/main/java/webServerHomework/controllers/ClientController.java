package webServerHomework.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.view.RedirectView;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;

@RestController
public class ClientController {


    private final WebClient client;

    public ClientController(WebClient.Builder builder) {
        client = builder
                .baseUrl("http://localhost:8081")
                .build();
    }

    @GetMapping(value = {"/client/list"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List> getClient() {
        return  client.get().uri("/data-mono/getAll")
                .retrieve()
                .bodyToMono(List.class);
    }

    @PostMapping("/client/save")
    public RedirectView clientSave(@RequestParam(value = "name", required = false) String name,
                                   @RequestParam(value = "address", required = false) String address,
                                   @RequestParam(value = "phone", required = false) String phone) {
        client.post().uri(uriBuilder -> uriBuilder
                    .path("/data-mono/saveClient")
                    .queryParam("name", name)
                    .queryParam("address", address)
                    .queryParam("phone", phone)
                    .build()
                )
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return new RedirectView("/", true);
    }

}
