package webServerHomework.crm.repository;

import org.springframework.data.repository.CrudRepository;
import webServerHomework.crm.model.Adress;


public interface AdressRepository extends CrudRepository<Adress, String> {
}
