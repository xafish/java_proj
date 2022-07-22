package datasrc.dbService.crm.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import datasrc.dbService.crm.repository.ClientRepository;
import datasrc.dbService.sessionmanager.TransactionClient;
import datasrc.producer.StringValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import datasrc.dbService.crm.model.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DbServiceClientImpl implements DBServiceClient {
    private static final Logger log = LoggerFactory.getLogger(DbServiceClientImpl.class);

    private final TransactionClient transactionClient;
    private final ClientRepository clientRepository;

    public DbServiceClientImpl(TransactionClient transactionClient, ClientRepository clientRepository) {
        this.transactionClient = transactionClient;
        this.clientRepository = clientRepository;
    }

    @Override
    public Client saveClient(Client client) {
        return transactionClient.doInTransaction(() -> {
            var savedClient = clientRepository.save(client);
            log.info("saved client: {}", savedClient);
            return savedClient;
        });
    }

    @Override
    public Optional<Client> getClient(long id) {
        var clientOptional = clientRepository.findById(id);
        log.info("client: {}", clientOptional);
        return clientOptional;
    }

    @Override
    public List<Client> findAll() {
        var clientList = new ArrayList<Client>();
        clientRepository.findAll().forEach(clientList::add);
        log.info("clientList:{}", clientList);
        return clientList;
    }

    @Override
    public StringValue findAllString() {
        var clientList = new ArrayList<Client>();
        clientRepository.findAll().forEach(clientList::add);
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        String json = gson.toJson(clientList);
        return new StringValue(String.format("%s", json));
    }
    /*public StringValue findAllString() {
        var clientList = new ArrayList<Client>();
        //clientRepository.findAll().forEach(a ->{clientListString.concat(a.getName() + " ," + a.getAdress() + " ," + a.getPhones().toString()  + "\\n");});
        clientRepository.findAll().forEach(clientList::add);
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        String json = gson.toJson(clientList);
        return new StringValue(json);
    }*/
}
