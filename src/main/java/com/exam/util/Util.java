package com.exam.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;

public class Util {
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

        public static String beforeFrom(String str, String fromStr, int matchCount) {
            StringBuilder sb = new StringBuilder();

            String[] bits = str.split(fromStr);
            for (int i = 0; i < matchCount; i++) {
                sb.append(bits[i]);
                if ( i + 1 < matchCount ) {
                    sb.append(fromStr);
                }
            }
            return sb.toString();
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

    public static Map<String, Object> mapOf(Object... args) {
        int dataSize = args.length / 2;

        Map<String, Object> map = new LinkedHashMap<>();

        for (int i = 0; i < dataSize; i++) {
            int keyIndex = i * 2 + 0;
            int valueIndex = i * 2 + 1;

            String key = (String) args[keyIndex];
            Object value = args[valueIndex];

            map.put(key, value);
        }

        return map;
    }

    public static class json {

        private static final ObjectMapper mapper;

        static {
            mapper = new ObjectMapper();
        }

        public static String toJsonStr(Object obj, String defaultValue) {

            try {
                return mapper.writeValueAsString(obj);
            } catch (JsonProcessingException e) {
                return defaultValue;
            }
        }

        public static <T> T toObj(String jsonStr, Class<T> cls, T defaultValue) {
            try {
                return mapper.readValue(jsonStr, cls);
            } catch (JsonProcessingException e) {
                return defaultValue;
            }
        }

        // TypeReference Jackson 에서 제공하는 클래스
        public static <T> T toObj(String jsonStr, TypeReference<T> typeReference, T defaultValue) {
            try {
                return mapper.readValue(jsonStr, typeReference);
            } catch (JsonProcessingException e) {
                return defaultValue;
            }
        }
    }
}
