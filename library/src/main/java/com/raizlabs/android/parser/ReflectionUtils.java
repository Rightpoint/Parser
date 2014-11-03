package com.raizlabs.android.parser;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Description:
 */
public class ReflectionUtils {

    public static boolean implementsInterface(Field field, String interfaceName) {
        if (field.getType().getName().equalsIgnoreCase("java.lang.Object") ||
                isPrimitiveType(field.getType().getName())) {
            return false;
        }
        return implementsInterface(field.getType(), field.getType().getInterfaces(), interfaceName);
    }

    private static boolean implementsInterface(Class clazz, Class[] classes, String interfaceName) {
        //TODO: sometimes all of the interfaces are not present when getting the classes

        boolean implemented = false;
        for (int i = 0; i < classes.length; i++) {
            if (classes[i].getName().equals(interfaceName)) {
                implemented = true;
                break;
            }
        }

        if (!implemented && !clazz.getSuperclass().getName().equals("java.lang.Object")) {
            Class superclazz = clazz.getSuperclass();
            Class[] clazzes = superclazz.getInterfaces();
            implemented = implementsInterface(superclazz, clazzes, interfaceName);
        }

        return implemented;
    }

    /**
     * Gets all fields from the inClass and returns a List
     *
     * @param outFields must be an empty list of fields (non-null)
     * @param inClass   the class object of the type we want to get information from (non-null)
     * @return list of all declared fields within the class specified.
     */
    public static List<Field> getAllFields(List<Field> outFields, Class<?> inClass) {
        for (Field field : inClass.getDeclaredFields()) {
            outFields.add(field);
        }
        if (inClass.getSuperclass() != null) {
            outFields = getAllFields(outFields, inClass.getSuperclass());
        }
        return outFields;
    }

    public static boolean isPrimitiveType(String inTypeName) {
        String type = inTypeName.toLowerCase();
        return type.equals("boolean")
                || type.equals("int") || type.equals("char")
                || type.equals("long") || type.equals("double")
                || type.equals("string") || type.contains("float")
                || type.equalsIgnoreCase("[I") || type.equalsIgnoreCase("[F")
                || type.equalsIgnoreCase("[S") || type.equalsIgnoreCase("[D")
                || type.equalsIgnoreCase("[C") || type.equalsIgnoreCase("[L")
                || type.equals("short");
    }


    @SuppressWarnings("rawtypes")
    public static boolean isClassChild(Class inClass, String inClassPackageName) {
        if (inClass.getName().equalsIgnoreCase(inClassPackageName)) {
            return true;
        }
        Class subclass = inClass;
        Class superclass = subclass.getSuperclass();
        while (superclass != null) {
            String className = superclass.getName();
            if (className.equalsIgnoreCase(inClassPackageName)) {
                return true;
            }
            subclass = superclass;
            superclass = subclass.getSuperclass();
        }
        return false;
    }

}
