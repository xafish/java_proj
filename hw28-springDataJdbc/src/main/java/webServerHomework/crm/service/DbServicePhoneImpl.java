package webServerHomework.crm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import webServerHomework.crm.model.Phone;
import webServerHomework.crm.repository.PhoneRepository;
import webServerHomework.sessionmanager.TransactionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DbServicePhoneImpl implements DBServicePhone {
    private static final Logger log = LoggerFactory.getLogger(DbServicePhoneImpl.class);

    private final PhoneRepository phoneRepository;
    private final TransactionManager transactionManager;

    public DbServicePhoneImpl(PhoneRepository phoneRepository, TransactionManager transactionManager) {
        this.phoneRepository = phoneRepository;
        this.transactionManager = transactionManager;
    }

    @Override
    public Phone savePhone(Phone phone) {
        return transactionManager.doInTransaction(() -> {
            var savedPhone = phoneRepository.save(phone);
            log.info("savedManager phone: {}", savedPhone);
            return savedPhone;
        });
    }

    @Override
    public Optional<Phone> getPhone(long id) {
            var phoneOptional = phoneRepository.findById(id);
            log.info("manager: {}", phoneOptional);
            return phoneOptional;

    }

    @Override
    public List<Phone> findAll() {
        var phoneList = new ArrayList<Phone>();
        phoneRepository.findAll().forEach(phoneList::add);
        log.info("clientList:{}", phoneList);
        return phoneList;
    }
}
