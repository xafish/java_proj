package datasrc.dbService.crm.service;



import datasrc.dbService.crm.model.Client;
import datasrc.producer.StringValue;

import java.util.List;
import java.util.Optional;

public interface DBServiceClient {

    Client saveClient(Client client);

    Optional<Client> getClient(long id);

    List<Client> findAll();

    StringValue findAllString();
}
