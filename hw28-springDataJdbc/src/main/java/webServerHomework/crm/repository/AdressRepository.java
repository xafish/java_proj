package webServerHomework.crm.repository;

import org.springframework.data.repository.CrudRepository;
import webServerHomework.crm.model.Adress;

import java.util.List;


public interface AdressRepository extends CrudRepository<Adress, String> {

    // закоментируйте, чтобы получить N+1
    @Override
    /*@Query(value = """
            select m.id    as manager_id,
                   m.label as manager_label,
                   c.id    as client_id,
                   c.name  as client_name,
                   c.order_column as order_column
            from manager m
                     left outer join client c
                                     on m.id = c.manager_id
            order by m.id
                                                          """,
            resultSetExtractorClass = ManagerResultSetExtractorClass.class)*/
    List<Adress> findAll();
}
