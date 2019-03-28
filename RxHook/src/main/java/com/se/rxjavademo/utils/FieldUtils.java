package com.se.rxjavademo.utils;

import java.lang.reflect.Field;

/**
 * Author    : garyhu
 * Since     : 2019/3/28
 * Describe  : 类反射获取成员属性并进行操作
 */

public class FieldUtils {

    /**
     * 获取成员属性对象
     * @param clazz 类
     * @param target 对象
     * @param name 属性名称
     * @return
     * @throws Exception
     */
    public static Object getField(Class clazz, Object target, String name) throws Exception {
        Field field = clazz.getDeclaredField(name);
        field.setAccessible(true);
        return field.get(target);
    }

    /**
     * 获取反射的属性
     * @param clazz
     * @param name
     * @return
     * @throws Exception
     */
    public static Field getField(Class clazz, String name) throws Exception{
        Field field = clazz.getDeclaredField(name);
        field.setAccessible(true);
        return field;
    }

    /**
     * 设置属性值
     * @param clazz
     * @param target
     * @param name
     * @param value
     * @throws Exception
     */
    public static void setField(Class clazz, Object target, String name, Object value) throws Exception {
        Field field = clazz.getDeclaredField(name);
        field.setAccessible(true);
        field.set(target, value);
    }

}
