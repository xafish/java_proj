import interfaceList.interfaceTest;

public class testLaunch {
    public static void main(String[] args) throws Exception {
        // создаём класс через метод прокси класса
        interfaceTest myClass = Ioc.createTestClass();
        // Запускаем метод 1
        myClass.calculation(1);
        // Запускаем метод 2(в интерфейсе этот метод НЕ помечен @Log, поэтому логироваться не должен)
        myClass.calculation(5, 3);
        // Запускаем метод 3
        myClass.calculation(6, 7, "testMess");
        // Запускаем общий метод с переменным числом параметров
        myClass.calculationCommon(2, 3, 6, 7, "testMess");
        myClass.calculationCommon(6, 7, "testMess");
    }
}
