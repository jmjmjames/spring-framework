package com.exam;

import com.exam.annotation.Controller;
import com.exam.article.ArticleController;
import com.exam.home.HomeController;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Container {
    private static final ArticleController articleController;
    private static final HomeController homeController;

    static {
        articleController = Ut.cls.newObj(ArticleController.class, null);
        homeController = Ut.cls.newObj(HomeController.class, null);
    }

    public static ArticleController getArticleController() {
        return articleController;
    }

    public static HomeController getHomeController() {
        return homeController;
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
