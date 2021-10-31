package jpql.crm.model;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "client")
public class Client implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "adress_id")
    private Adress adress;

    // делаем двунаправленную связь с указанием сущности-владельца
    @OneToMany(mappedBy = "client", cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    //private List<Phone> phones = new ArrayList<>();
    private Set<Phone> phones = new HashSet<>();

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

    public Client(Long id, String name, Adress adress) {
        this.id = id;
        this.name = name;
        this.adress = adress;
    }

    public Client(Long id, String name, Adress adress, Set<Phone> phones) {
        this.id = id;
        this.name = name;
        this.adress = adress;
        this.phones = phones;
    }

    @Override
    public Client clone() {
        // новый экземпляр-копия
        Client clientCopy = new Client(this.id, this.name, this.adress);
        // Склонируем phones, вызвав метод добавления
        this.phones.forEach(phone -> clientCopy.addPhone(new Phone(phone.getNumber())));
        //return new Client(this.id, this.name, this.adress, this.phones);
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

    // метод для добавления телефонных номеров и установления их связи с клиентом
    public void addPhone(Phone phone) {
        phones.add(phone);
        phone.setClient(this);
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
