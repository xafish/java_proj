import java.util.NavigableMap;

public class runActions {
    public static void main(String[] args) throws Exception {
        // создадим экземпляр купюрориёмника
        atm moneyholder = new atm();
        System.out.println("RestMoney = "+moneyholder.getRestMoney());
        // тут должны сработать проверки
        moneyholder.addMoney(501,10);
        // кладём деньги в банкомат
        moneyholder.addMoney(500,5);
        moneyholder.addMoney(50,3);
        moneyholder.addMoney(1000,2);
        System.out.println("RestMoney = "+moneyholder.getRestMoney());
        // тут должны сработать проверки
        moneyholder.getMoney(10);
        moneyholder.getMoney(500000);
        // получаем запрошенную сумму
        moneyholder.getMoney(3600);
        // смотрим остаток
        System.out.println("RestMoney = "+moneyholder.getRestMoney());
        // у нас осталась одна купюра по 50, а мы пытаемся снять 100(то есть 2 купюрыпо 50)
        moneyholder.getMoney(100);
        // снимаем 50
        moneyholder.getMoney(50);
        // пробуем снять еш 50, когда купюо больше не осталось
        moneyholder.getMoney(50);
        // получаем запрошенную сумму
        moneyholder.getMoney(1000);
        // смотрим остаток
        System.out.println("RestMoney = "+moneyholder.getRestMoney());
        // пытаемся снять ещё при остатке в 0
        moneyholder.getMoney(500);
    }
}