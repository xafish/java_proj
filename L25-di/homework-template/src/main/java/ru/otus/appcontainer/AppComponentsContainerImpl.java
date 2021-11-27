package ru.otus.appcontainer;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.stream.Collectors;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        // You code here...
        // создаём инстанс конфигурационного класса
        try {
            Object exClass = configClass.newInstance();
            // проходимся по методам, отсортированным по полю order в аннотации
            for ( Method method : Arrays.stream(configClass.getMethods())
                    .filter(method -> method.isAnnotationPresent(AppComponent.class))
                    .sorted(Comparator.comparingInt(o ->o.getAnnotation(AppComponent.class).order()))
                    .toArray(Method[]::new))
            {
                // получаем аннотацию
                AppComponent appComponent = method.getAnnotation(AppComponent.class);
                // создаём экземпляр класса посредством вызова конструктора метода
                Object exMethod = callMethod(exClass,method);
                // добавляем метод в список
                appComponentsByName.put(appComponent.name(), exMethod);
                appComponents.add(exMethod);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    // получение интерфейса по классу
    private String getInterfaceName(Object obj) {
        return obj.getClass().getInterfaces()[0].getName();
    }

    // получение метода из списка компонентов по имени интерфейса
    private Object getMethodFromInterfaceName(String interfaceName) {
        return appComponents.stream().filter(p ->
                getInterfaceName(p).equals(interfaceName)
        ).findFirst().get();
    }

    // метод для вызова переданного метода
    public Object callMethod(Object object, Method method) {
        try {
            method.setAccessible(true);
            List<Object> paramList = new ArrayList<>();
            Parameter[] params = method.getParameters();
            // соберём массив параметров(считаем, что все параметры уже есть в списке объектов)
            for (Parameter param : params) {
                // выберем из списка компонентов метод, имя интерфейса которого совпадает с именем интерфейса параметра
                paramList.add(
                        getMethodFromInterfaceName(param.getParameterizedType().getTypeName())
                );
            }
            return method.invoke(object,paramList.toArray());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        Object result;
        // если объект является интерфейсом
        if (componentClass.isInterface()) {
            // пытаемся найти по имени класса, если передали аннотацию
            result = getMethodFromInterfaceName(componentClass.getName());
        }
        // иначе достаём имя интерфейса
        else {
            result = getMethodFromInterfaceName(componentClass.getInterfaces()[0].getName());
        }
        return (C) result;
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        return (C) appComponentsByName.get(componentName);
    }
}
