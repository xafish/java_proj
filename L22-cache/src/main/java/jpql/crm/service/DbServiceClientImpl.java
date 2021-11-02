package jpql.crm.service;

import jpql.core.repository.DataTemplate;
import jpql.core.sessionmanager.TransactionManager;
import jpql.crm.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cachehw.HwCache;
import ru.otus.cachehw.MyCache;

import java.util.List;
import java.util.Optional;

public class DbServiceClientImpl implements DBServiceClient {
    private static final Logger log = LoggerFactory.getLogger(DbServiceClientImpl.class);

    private final DataTemplate<Client> clientDataTemplate;
    private final TransactionManager transactionManager;
    // кэш
    private final HwCache<String, Optional<Client>> cache = new MyCache<>();

    public DbServiceClientImpl(TransactionManager transactionManager, DataTemplate<Client> clientDataTemplate) {
        this.transactionManager = transactionManager;
        this.clientDataTemplate = clientDataTemplate;
    }

    @Override
    public Client saveClient(Client client) {
        return transactionManager.doInTransaction(session -> {
            var clientCloned = client.clone();
            if (client.getId() == null) {
                clientDataTemplate.insert(session, clientCloned);
                log.info("created client: {}", clientCloned);
            } else {
                clientDataTemplate.update(session, clientCloned);
                log.info("updated client: {}", clientCloned);
            }
            // запишем клиента в кэш
            cache.put(String.valueOf(clientCloned.getId()),Optional.ofNullable(clientCloned));
            return clientCloned;
        });
    }

    @Override
    public Optional<Client> getClient(long id) {
        return transactionManager.doInTransaction(session -> {
            var clientOptional = clientDataTemplate.findById(session, id);
            log.info("client: {}", clientOptional);
            // запишем клиента в кэш
            cache.put(String.valueOf(clientOptional.get().getId()),clientOptional);
            return clientOptional;
        });
    }

    @Override
    public Optional<Client> getClientWithCache(long id) {
        Optional<Client> client = cache.get(String.valueOf(id));
        // если записи нет в КЭШе, то создаём
        if (client == null) {
            return transactionManager.doInTransaction(session -> {
                var clientOptional = clientDataTemplate.findById(session, id);
                log.info("client: {}", clientOptional);
                // запишем клиента в кэш
                cache.put(String.valueOf(clientOptional.get().getId()),clientOptional);
                return clientOptional;
            });
        // иначе просто возвращаем клиента по ID
        } else {
            return client;
        }
    }

    @Override
    public List<Client> findAll() {
        return transactionManager.doInTransaction(session -> {
            var clientList = clientDataTemplate.findAll(session);
            log.info("clientList:{}", clientList);
            return clientList;
       });
    }
}
