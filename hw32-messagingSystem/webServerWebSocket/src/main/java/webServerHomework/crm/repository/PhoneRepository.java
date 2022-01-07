package webServerHomework.crm.repository;

import org.springframework.data.repository.CrudRepository;
import webServerHomework.crm.model.Phone;


public interface PhoneRepository extends CrudRepository<Phone, Long> {
}
