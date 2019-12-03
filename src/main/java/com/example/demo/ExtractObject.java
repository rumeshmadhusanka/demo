package com.example.demo;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ExtractObject {

    public static Map<String, Object> getAttributesAndData(Object o) {
        Class<?> clazz = o.getClass();
        Map<String, Object> map = new HashMap<String, Object>();
        for (Field field : clazz.getDeclaredFields()) {
            //you can also use .toGenericString() instead of .getName(). This will
            //give you the type information as well.
            field.setAccessible(true);
            try {
                map.put(field.getName(), field.get(o));
            } catch (IllegalAccessException e) {
                System.out.println("Wrong data type");
            }
        }
        System.out.println(map.toString());
        return map;
    }
}
