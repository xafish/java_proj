package jpql.crm.service;

import jpql.crm.model.*;

import java.util.List;
import java.util.Optional;

public interface DBServiceClient {

    Client saveClient(Client client);

    Optional<Client> getClient(long id);

    Optional<Client> getClientWithCache(long id);

    List<Client> findAll();
}
