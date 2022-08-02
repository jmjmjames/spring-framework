package com.exam;

import com.exam.annotation.Controller;
import com.exam.article.ArticleController;
import com.exam.home.HomeController;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Container {
    private static Map<Class, Object> objects;

    static {
        objects = new HashMap<>();
        objects.put(ArticleController.class, new ArticleController());
        objects.put(HomeController.class, new HomeController());
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
