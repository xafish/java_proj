import tastAnnotations.*;

public class reflectionTest {
    private int number1, number2, result;
    @Test
    public void errorMethod() {
        System.out.println("запущен errorMethod");
        var zeroNumber = 0;
        result = number1/zeroNumber;
    }
    @After
    public int afterMethod() {
        System.out.println("запущен afterMethod");
        return result;
    }
    @Test
    public void succesMethod() {
        System.out.println("запущен succesMethod");
        result = number1/number2;
    }
    @Before
    public void beforeMethod(int num1, int num2) {
        System.out.println("запущен beforeMethod");
        number1 = num1;
        number2 = num2;
    }
    public void notTestedMethod() {
        System.out.println("you can't see me");
    }
}
