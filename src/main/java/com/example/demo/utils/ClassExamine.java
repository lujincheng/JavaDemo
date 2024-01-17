package com.example.demo.utils;

import java.lang.reflect.Field;

import cn.hutool.core.util.StrUtil;

public class ClassExamine {
    
    public static <T> void objectOverlap(T origin, T intactObject) throws Exception {
        Field[] fields = origin.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getType() == String.class) {
                if (StrUtil.isEmpty((String) field.get(origin))) {
                    field.set(origin, field.get(intactObject));
                }
            } else {
                if (field.get(origin) == null) {
                    field.set(origin, field.get(intactObject));
                }
            }
        }
    }
}
