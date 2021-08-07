import tastAnnotations.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;


public class TestAnalyzer {
    int total = 0;
    int pass = 0;
    int fail = 0;
    private String Stream;

    public void parse(String className) throws Exception {
        // получаем класс по имени
        Class clazz = Class.forName(className);

        // получаем массив методов у которых есть аннотация Before
        Method[] methodsBefore = Arrays.stream(clazz.getMethods())
                .filter(method -> method.isAnnotationPresent(Before.class))
                .toArray(Method[]::new);
        // получаем массив методов у которых есть аннотация Test
        Stream<Method> methodsTest = Arrays.stream(clazz.getMethods())
                .filter(method -> method.isAnnotationPresent(Test.class));
        // получаем массив методов у которых есть аннотация After
        Method[] methodsAfter = Arrays.stream(clazz.getMethods())
                .filter(method -> method.isAnnotationPresent(After.class))
                .toArray(Method[]::new);
        // проходимся по каждому методу Test
        methodsTest.forEach(method -> this.modifyMethod(method, methodsBefore, methodsAfter, clazz));

        // формируем результат
        System.out.println("total="+total+"; pass="+pass+"; fail="+fail);
    }

    public void modifyMethod(Method methodTest, Method[] methodsBefore, Method[] methodsAfter, Class clazz) {
        total++;
        // создаём экземпляр найденного объекта(для каждой тройки)
        try {
            Object exClass = clazz.newInstance();
            // Методы Before
            Arrays.stream(methodsBefore).forEach(method -> {
                try {
                    //method.invoke(exClass,10,11);
                    this.callMethod(exClass, method);
                } catch (Exception e) {
                    // ошибка
                    fail++;
                    return;
                }
            });
            // Сам переданный метод Test
            this.callMethod(exClass, methodTest);
            // Методы After
            Arrays.stream(methodsAfter).forEach(method -> {
                try {
                    this.callMethod(exClass, method);
                } catch (Exception e) {
                    // ошибка
                    fail++;
                    return;
                }
            });
            pass++;
        } catch (Exception e) {
            // ошибка
            fail++;
            System.out.println("error="+e);
        }
    }
    // метод для вызова переданного метода
    public static Object callMethod(Object object, Method method) {
        try {
            method.setAccessible(true);
            List<Object> paramList = new ArrayList<>();
            Parameter[] params = method.getParameters();
            // соберём массив параметров
            // реализовал проверку только двух типов
            for (Parameter param : params) {
                if (param.getParameterizedType().toString().equals("int")) {
                    // присвоим int'ам случайные значения в диапазоне
                    paramList.add(rnd(1, 100));
                } else if (param.getParameterizedType().toString().equals("string")) {
                    paramList.add("TestStr");
                }
            }
            return method.invoke(object,paramList.toArray());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Метод получения случайного целого числа от min до max (включая max);
    public static int rnd(int min, int max)
    {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }
}
