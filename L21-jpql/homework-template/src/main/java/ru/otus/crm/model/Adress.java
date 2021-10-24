package ru.otus.crm.model;


import javax.persistence.*;

@Entity
@Table(name = "adress")
public class Adress implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "street")
    private String street;

    public Adress() {
    }

    public Adress(String street) {
        this.id = null;
        this.street = street;
    }

    public Adress(Long id, String street) {
        this.id = id;
        this.street = street;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public String toString() {
        return "Adress{" +
                "id=" + id +
                ", street='" + street + '\'' +
                '}';
    }
}
