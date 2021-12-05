package webServerHomework.crm.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import webServerHomework.crm.model.Phone;

import java.util.Optional;


public interface PhoneRepository extends CrudRepository<Phone, Long> {


    //Optional<Phone> findByNumber(String name);

    @Query("select * from phone where upper(number) = upper(:number)")
    Optional<Phone> findByNumber(@Param("number") String number);

    @Modifying
    @Query("update phone set number = :newNumber where id = :id")
    void updateNumber(@Param("id") long id, @Param("newNumber") String newNumber);
}
