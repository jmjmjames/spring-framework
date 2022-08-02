package com.exam;

import com.exam.annotation.AutoWired;
import com.exam.annotation.Controller;
import com.exam.annotation.Service;
import org.reflections.Reflections;

import java.util.*;

public class Container {
    private static Map<Class, Object> objects;

    static {
        objects = new HashMap<>();
        scanComponents();
    }

    private static void scanComponents() {
        scanService();
        scanController();

        // 레고 조립
        resolveDependenciesAllComponents();
    }

    private static void resolveDependenciesAllComponents() {
        for (Class cls : objects.keySet()) {
            Object o = objects.get(cls);
            resolveDependencies(o);
        }
    }

    private static void resolveDependencies(Object obj) {
        Arrays.stream(obj.getClass().getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(AutoWired.class))
                .peek(field -> field.setAccessible(true))
                .forEach(field -> {
                    Class<?> cls = field.getType();
                    Object dependency = objects.get(cls);
                    try {
                        field.set(obj, dependency);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
    }

    private static void scanService() {
        Reflections reflections = new Reflections("com.exam");
        for (Class<?> cls : reflections.getTypesAnnotatedWith(Service.class)) {
            objects.put(cls, Ut.cls.newObj(cls, null));
        }
    }

    private static void scanController() {
        Reflections reflections = new Reflections("com.exam");
        for (Class<?> cls : reflections.getTypesAnnotatedWith(Controller.class)) {
            objects.put(cls, Ut.cls.newObj(cls, null));
        }
    }

    public static <T> T getObj(Class<T> cls) {
        return (T) objects.get(cls);
    }

    public static List<String> getControllerNames() {
        ArrayList<String> names = new ArrayList<>();
        Reflections reflections = new Reflections("com.exam");
        for (Class<?> cls : reflections.getTypesAnnotatedWith(Controller.class)) {
            String name = cls.getSimpleName();
            name = name.replace("Controller", "");
            name = Ut.str.decapitalize(name);
            names.add(name);
        }
        return names;
    }
}
