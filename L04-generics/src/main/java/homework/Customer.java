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

    public static void main( String[] args ) {
        final long customerId = 1L;
        Customer customer = new Customer(customerId, "Ivan", 233);
        Map<Customer, String> map = new HashMap<>();

        String expectedData = "data";
        map.put(customer, expectedData);

        System.out.println(new Customer(customerId, "Ivan", 233));
        System.out.println(new Customer(customerId, "Ivan23132", 233));
        System.out.println(map.get(new Customer(customerId, "Ivan", 233)));
        System.out.println(map.get(new Customer(customerId, "Ivan123", 233)));
        //
        Customer customer2 = new Customer(2, "Jax", 300);
        System.out.println(customer.hashCode());
        System.out.println(new Customer(customerId, "Ivan23132", 233).hashCode());
        /*Customer Customer = new Customer(1, "Alex", 500);
        Customer Customer2 = new Customer(2, "Jax", 300);
        System.out.println(Customer);
        System.out.println(Customer2);
        Customer.setName("Josh");
        System.out.println(Customer);
        System.out.println(Customer2);*/
        /*System.out.println(Customer);
        System.out.println(Customer.getId());
        System.out.println(Customer.getName());
        Customer.setName("Josh");
        System.out.println(Customer.getName());
        System.out.println(Customer.getScores());
        Customer.setScores(666);
        System.out.println(Customer.getScores());
        System.out.println(Customer.toString());
        System.out.println(Customer.equals(Customer));
        System.out.println(Customer.equals(Customer2));
        System.out.println(Customer.hashCode());
        System.out.println(Customer2.hashCode());*/
    }
}
