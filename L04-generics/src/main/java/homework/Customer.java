package homework;

import java.util.HashMap;
import java.util.Map;

public class Customer {
    private final long id;
    private String name;
    private long scores;

    //todo: 1. в этом классе надо исправить ошибки

    public Customer(long id, String name, long scores) {
        this.id = id;
        this.name = name;
        this.scores = scores;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getScores() {
        return scores;
    }

    public void setScores(long scores) {
        this.scores = scores;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", scores=" + scores +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        if (id != customer.id) return false;
        // Ошибка
        // поля класса, которые используются для вычисления equals() и hashcode() , должны быть неизменяемыми(то есть ТОЛЬКО наш ID)
        //if (scores != customer.scores) return false;
        return true;//name != null ? name.equals(customer.name) : customer.name == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        // Ошибка
        // Так как уникальным идентификатором должен быть ID - имя и результат не должны влиять на значение hashCode
        /*result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (int) (scores ^ (scores >>> 32));*/
        return result;
    }
}
