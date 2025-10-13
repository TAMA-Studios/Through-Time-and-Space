/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class AnnotationUtils {
    public static boolean fieldHasAnnotation(
            Class<?> clazz, String fieldName, Class<? extends Annotation> annotationClass) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            return hasAnnotation(field, annotationClass);
        } catch (NoSuchFieldException e) {
            Logger.warn("Field %s not found in class %s", fieldName, clazz.getSimpleName());
            return false;
        }
    }

    public static <T extends Annotation> T getAnnotation(Field field, Class<T> annotationClass) {
        if (field == null || annotationClass == null) return null;
        return field.getAnnotation(annotationClass);
    }

    public static boolean hasAnnotation(Class<? extends Annotation> clazz, Object object) {
        for (Field f : clazz.getDeclaredFields()) {
            try {
                Object TestField = f.get(Object.class);

                if (TestField == object) {
                    if (f.isAnnotationPresent(clazz)) {
                        return true;
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    public static boolean hasAnnotation(Class<?> clazz, Class<? extends Annotation> annotationClass) {
        if (clazz == null || annotationClass == null) return false;
        return clazz.isAnnotationPresent(annotationClass);
    }

    public static boolean hasAnnotation(Field field, Class<? extends Annotation> annotationClass) {
        if (field == null || annotationClass == null) return false;
        return field.isAnnotationPresent(annotationClass);
    }

    public static boolean hasAnnotation(Method method, Class<? extends Annotation> annotationClass) {
        if (method == null || annotationClass == null) return false;
        return method.isAnnotationPresent(annotationClass);
    }

    public static void logFieldsWithAnnotation(Class<?> clazz, Class<? extends Annotation> annotationClass) {
        for (Field field : clazz.getDeclaredFields()) {
            if (hasAnnotation(field, annotationClass)) {
                Logger.info(
                        "Field %s in %s has annotation %s",
                        field.getName(), clazz.getSimpleName(), annotationClass.getSimpleName());
            }
        }
    }
}
