package com.raizlabs.android.parser;

import android.location.Location;

import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.TypeVariable;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Description:
 */
public class ParserUtils {

    public static Map parseHashMap(Parser parser, Field fld, JSONObject json) throws IllegalAccessException, InstantiationException {
        ParameterizedType type = (ParameterizedType) fld.getGenericType();
        Class c = (Class) type.getActualTypeArguments()[1];
        Map map = (Map) fld.getType().newInstance();
        for (Iterator<String> it = json.keys(); it.hasNext(); ) {
            String key = it.next();
            try {
                map.put(key, parser.parse(c, json.get(key)));
            } catch (Throwable e) {
            }
        }
        return map;
    }

    private static Location parseLocation(String value) {
        String[] split = value.split(",");
        Double lat = Double.valueOf(split[0].trim());
        Double lon = Double.valueOf(split[1].trim());
        Location location = new Location("");
        location.setLatitude(lat);
        location.setLongitude(lon);
        return location;
    }

    /**
     * This method will return a unique key list of keys that are a combination of the JSON object
     * and the fields so we can handle both any field we declared and key within the JSON object.
     *
     * @param jsonObject
     * @param fieldKeys
     * @return
     */
    public static ArrayList<String> mergeKeysAndFields(Iterator<String> dataKeys, Set<String> fieldKeys) {
        ArrayList<String> merged = new ArrayList<String>();
        while (dataKeys.hasNext()) {
            merged.add(dataKeys.next());
        }

        // Ensure unique keys
        for (String fieldKey : fieldKeys) {
            if (!merged.contains(fieldKey)) {
                merged.add(fieldKey);
            }
        }
        return merged;
    }

    public static void parseStringConstructorType(Object inDataObject, Field f, String valueFromJSONString) {
        Constructor constructor = null;
        try {
            if (f.getType().isPrimitive()) {
                String className = f.getType().getName();
                className = className.replaceFirst(className.charAt(0) + "", (className.charAt(0) + "").toUpperCase());
                className = (className.equals("Int")) ? "Integer" : className.equals("Char") ? "Character" : className;
                constructor = Class.forName("java.lang." + className).getConstructor(String.class);
            } else if (f.getType().getName().equals("java.util.Date")) {
                f.set(inDataObject, DateFormat.getInstance().parse(valueFromJSONString));
                return;
            } else {
                constructor = f.getType().getConstructor(String.class);
            }
            f.set(inDataObject, constructor.newInstance(valueFromJSONString));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static <ListInputType> void parseArrayType(Object inDataObject, Field f,
                                                      Parser<?, ?, ListInputType> parser, ListInputType valueFromJSON) {
        Class clazz = null;
        try {
            if (f.getType().isArray()) {
                clazz = (Class) f.getType().getComponentType();
                f.set(inDataObject, parser.parseArray(clazz, valueFromJSON));
            } else if (f.getGenericType() instanceof TypeVariable) {
                ParameterizedType type = (ParameterizedType) getClass(inDataObject.getClass());
                clazz = (Class) type.getActualTypeArguments()[0];
                Class listClazz = (Class) type.getRawType();
                f.set(inDataObject, parser.parseList(listClazz, clazz, valueFromJSON));
            } else {
                ParameterizedType type = (ParameterizedType) f.getGenericType();
                clazz = (Class) type.getActualTypeArguments()[0];
                f.set(inDataObject, parser.parseList(f.getType(), clazz, valueFromJSON));
            }
        } catch (ClassCastException c) {
            c.printStackTrace();
        } catch (IllegalAccessException c) {
            c.printStackTrace();
        }
    }

    public static <InputType> void parseObjectType(Object inDataObject, Field f,
                                                   Parser<?, InputType, ?> parser, InputType valueFromJSON) {
        try {
            if (ReflectionUtils.implementsInterface(f, "java.util.Map")) {
                f.set(inDataObject, parser.parseMap(f, valueFromJSON));
            } else {
                if (f.getGenericType() instanceof TypeVariable) {
                    Class clazz = (Class) ParserUtils.getClass(inDataObject.getClass());
                    f.set(inDataObject, parser.parse(clazz, valueFromJSON));
                } else {

                    Object value = parser.parse(f.getType(), valueFromJSON);
                    f.set(inDataObject, value);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static Object getClass(Class clazz) {
        Class cl = clazz;
        if (Object.class.getSimpleName().equals(cl.getSuperclass().getSimpleName())) {
            throw new IllegalArgumentException(
                    "Default constructor does not support direct instantiation");
        }

        while (!Object.class.getSimpleName().equals(cl.getSuperclass().getSimpleName())) {
            // case of multiple inheritance, we are trying to get the first available generic info
            if (cl.getGenericSuperclass() instanceof ParameterizedType) {
                break;
            }
            cl = cl.getSuperclass();
        }

        if (cl.getGenericSuperclass() instanceof ParameterizedType) {
            ParameterizedType type = (ParameterizedType) cl.getGenericSuperclass();
            return type.getActualTypeArguments()[0];
        }
        return null;
    }

    public static <Result> Result getNewInstance(Class inDataClass) {
        Result objectToParse = null;
        try {
            objectToParse = (Result) inDataClass.newInstance();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return objectToParse;
    }
}
