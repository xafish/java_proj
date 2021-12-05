package webServerHomework.crm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import webServerHomework.crm.model.Adress;
import webServerHomework.sessionmanager.TransactionManager;

import java.util.List;

@Service
public class DbServiceAdressImpl implements DBServiceAdress {
    private static final Logger log = LoggerFactory.getLogger(DbServiceAdressImpl.class);

    private final webServerHomework.crm.repository.AdressRepository AdressRepository;
    private final TransactionManager transactionManager;

    public DbServiceAdressImpl(webServerHomework.crm.repository.AdressRepository AdressRepository, TransactionManager transactionManager) {
        this.AdressRepository = AdressRepository;
        this.transactionManager = transactionManager;
    }

    @Override
    public Adress saveAdress(Adress Adress) {
        return transactionManager.doInTransaction(() -> {
            var savedAdress = AdressRepository.save(Adress);
            log.info("savedAdress Adress: {}", savedAdress);
            return savedAdress;
        });
    }

    @Override
    public List<Adress> findAll() {
        var AdressList = AdressRepository.findAll();
        log.info("AdressList:{}", AdressList);
        return AdressList;
    }
}
