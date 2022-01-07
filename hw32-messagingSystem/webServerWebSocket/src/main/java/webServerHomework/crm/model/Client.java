package webServerHomework.crm.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

@Table("client")
public class Client implements Cloneable {

    @Id
    @Nonnull
    private Long id;

    @Nonnull
    private String name;

    @MappedCollection(idColumn = "id")
    private Adress adress;

    @MappedCollection(idColumn = "client_id")
    private Set<Phone> phones;

    public Client() {
    }

    public Client(String name) {
        this.id = null;
        this.name = name;
    }

    public Client(String name, Adress adress) {
        this.id = null;
        this.name = name;
        this.adress = adress;
    }

    public Client(String name, Adress adress, Set<Phone> phones) {
        this.id = null;
        this.name = name;
        this.adress = adress;
        this.phones = phones;
    }

    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
        this.adress = null;
        this.phones = null;
    }

    public Client(Long id, String name, Adress adress, Set<Phone> phones) {
        this.id = id;
        this.name = name;
        this.adress = adress;
        this.phones = phones;
    }

    @PersistenceConstructor
    private Client(Long id, String name, Adress adress) {
        this(id, name, adress, new HashSet<>());
    }

    @Override
    public Client clone() {
        Client clientCopy = new Client(this.id, this.name, this.adress);
        return clientCopy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Adress getAdress() {
        return adress;
    }

    public void setAdress(Adress adress) {
        this.adress = adress;
    }

    public Set<Phone> getPhones() {
        return phones;
    }

    public void setPhones(Set<Phone> phones) {
        this.phones = phones;
    }

    // метод для добавления телефонных номеров
    public void addPhone(Phone phone) {
        phones.add(phone);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", adress='" + adress + '\'' +
                ", phones='" + phones + '\'' +
                '}';
    }

}
