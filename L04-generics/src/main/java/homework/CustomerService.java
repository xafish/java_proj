package homework;


import java.security.Key;
import java.util.*;

public class CustomerService {
    private NavigableMap<Customer, String> map = new TreeMap<>(Comparator.comparingLong(o ->o.getScores()));

    //todo: 3. надо реализовать методы этого класса
    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны

    public Map.Entry<Customer, String> getSmallest() {
        //return map.entrySet().stream().min(Comparator.comparingLong(o -> o.getKey().getScores())).get();
        // находим наименьший элемент
        //Map.Entry<Customer, String>minCustomer = map.entrySet().stream().min(Comparator.comparingLong(o -> o.getKey().getScores())).get();
        Map.Entry<Customer, String>minCustomer = map.firstEntry();
        // проерка для предотвращения nullPointer
        if (minCustomer == null) {
            return null;
        } else{
            // новый экземпляр объекта Customer для того, чтобы в случае изменения меняли именно его, а не объект в map'е
            Customer tmpCustomer = new Customer(minCustomer.getKey().getId(), minCustomer.getKey().getName(), minCustomer.getKey().getScores());
            return new AbstractMap.SimpleEntry<Customer, String>(tmpCustomer, minCustomer.getValue());
        }
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        // находим следующий элемент после переданного
        Map.Entry<Customer, String>higher_entry = map.higherEntry(customer);
        // проерка для предотвращения nullPointer
        if (higher_entry == null) {
            return null;
        } else{
            // новый экземпляр объекта Customer для того, чтобы в случае изменения меняли именно его, а не объект в map'е
            Customer tmpCustomer = new Customer(higher_entry.getKey().getId(), higher_entry.getKey().getName(), higher_entry.getKey().getScores());
            return new AbstractMap.SimpleEntry<Customer, String>(tmpCustomer, higher_entry.getValue());
        }
    }

    public void add(Customer customer, String data) {
        map.put(customer,data);
    }
}
