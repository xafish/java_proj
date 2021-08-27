import interfaceList.*;

public class testClass implements interfaceTest {
    private int result;

    public void interfaceTest() {
        result = 0;
    }

    public int getResult() {
        return result;
    }

    public void calculation(int num1) {
        System.out.println("launch calculation 1 params");
        result = num1++;
    }

    public void calculation(int num1, int num2) {
        System.out.println("launch NO LOG calculation 2 params");
        result = num1+num2;
    }

    public void calculation(int num1, int num2, String str3) {
        System.out.println("launch calculation 2 params and text "+str3);
        result = num1+num2;
    }

    public void calculationCommon(Object... param) {
        System.out.println("launch calculation with random Param");
    }

}
