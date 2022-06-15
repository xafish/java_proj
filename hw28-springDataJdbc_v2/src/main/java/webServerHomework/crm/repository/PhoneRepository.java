package webServerHomework.crm.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import webServerHomework.crm.model.Phone;

import java.util.Optional;


public interface PhoneRepository extends CrudRepository<Phone, Long> {
}
