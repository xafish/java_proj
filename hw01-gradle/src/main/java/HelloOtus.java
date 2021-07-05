package hw01;

import static com.google.common.base.Objects.equal;

public class HelloOtus {
    public static void main(String[] args) {
        Boolean test;
        test = equal(null, null);
        if (test) {
            System.out.println("null = null");
        } else {
            System.out.println("null != null");
        }
    }
}
