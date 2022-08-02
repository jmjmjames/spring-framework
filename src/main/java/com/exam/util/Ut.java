package com.exam.util;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class Ut {
    public static class cls {

        public static <T> T newObj(Class<T> cls, T defaultValue) {
            try {
                return cls.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                return defaultValue;
            }
        }
    }

    public static class str {

        public static String decapitalize(String string) {
            if (string == null || string.length() == 0) {
                return string;
            }

            char c[] = string.toCharArray();
            c[0] = Character.toLowerCase(c[0]);

            return new String(c);
        }
    }

    public static class reflection {

        public static <T> T getFieldValue(Object obj, String fieldName, T defaultValue) {
            Field field = null;
            try {
                field = obj.getClass().getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                return defaultValue;
            }

            field.setAccessible(true);
            try {
                return (T) field.get(obj);
            } catch (IllegalAccessException e) {
                return defaultValue;
            }
        }
    }
}
