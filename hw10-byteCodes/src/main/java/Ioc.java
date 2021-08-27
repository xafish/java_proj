import annotations.Log;
import interfaceList.interfaceTest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

class Ioc {

    private Ioc() {
    }

    // статичный мотод для создания нового экземпляра нужного нам класса
    static interfaceTest createTestClass() {
        InvocationHandler handler = new DemoInvocationHandler(new testClass());
        return (interfaceTest) Proxy.newProxyInstance(Ioc.class.getClassLoader(),
                new Class<?>[]{interfaceTest.class}, handler);
    }

    static class DemoInvocationHandler implements InvocationHandler {
        // класс из интерфейса
        private final interfaceTest myClass;
        // методы, помеченные аннотацией Log
        private final Method[] logMethodList;

        // конструктор класса
        DemoInvocationHandler(interfaceTest myClass) {
            this.myClass = myClass;
            // у интерфейса(это важно) получаем массив методов у которых есть аннотация Log
            this.logMethodList = Arrays.stream(interfaceTest.class.getMethods())
                    .filter(method -> method.isAnnotationPresent(Log.class))
                    .toArray(Method[]::new);
        }
        // переопределим выполнение мотода
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            // если метод помечен аннотацией log
            if ( Arrays.stream(logMethodList).anyMatch(method::equals)) {
                String paramlist = "";
                // пробежимся по массиву аргументов и добавим их в текст
                for (Object arg : args) {
                    // если параметр - массив объектов, то пробежимся по нему как по массиву
                    if (arg.getClass() == Object[].class) {
                        for (Object intArg : (Object[]) arg) {
                            paramlist = paramlist + " " + intArg;
                        }
                    } else {
                        paramlist = paramlist + " " + arg;
                    }
                }
                // добавляем логирование
                System.out.println("executed method:" + method.getName() + " param:"+ paramlist);
            }
            return method.invoke(myClass, args);
        }

        @Override
        public String toString() {
            return "DemoInvocationHandler{" +
                    "myClass=" + myClass +
                    '}';
        }
    }
}
