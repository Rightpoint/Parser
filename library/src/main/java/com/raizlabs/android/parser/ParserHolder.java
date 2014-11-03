package com.raizlabs.android.parser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: andrewgrosner
 * Contributors: { }
 * Description:
 */
public class ParserHolder {

    private static Map<Class<?>, ParseInterface> mParseMap = new HashMap<>();

    private static Map<Class<?>, ObjectParser> mParseableMap = new HashMap<>();

    private static ParserManagerInterface manager;

    public static ParserManagerInterface getManager() {
        if (manager == null) {
            try {
                manager = (ParserManagerInterface) Class.forName("com.raizlabs.android.parser.ParserManager").newInstance();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
        return manager;
    }

    @SuppressWarnings("unchecked")
    public static <ReturnType> ReturnType parse(Class<ReturnType> returnTypeClass, Object object) {
        getManager();
        ReturnType returnType = (ReturnType) mParseMap.get(object.getClass()).parse(returnTypeClass, object);
        return returnType;
    }

    @SuppressWarnings("unchecked")
    public static <ReturnType> ReturnType[] parseArray(Class<ReturnType> returnTypeClass, Object object) {
        getManager();
        ReturnType[] returnTypes = (ReturnType[]) mParseMap.get(object.getClass()).parseArray(returnTypeClass, object);
        return returnTypes;
    }

    @SuppressWarnings("unchecked")
    public static <ReturnType> List<ReturnType> parseList(Class<ReturnType> returnTypeClass, Object object) {
        getManager();
        List<ReturnType> returnTypes = (List<ReturnType>) mParseMap.get(object.getClass()).parseList(returnTypeClass, object);
        return returnTypes;
    }

    public static <KeyType, ValueType> Map<KeyType, ValueType> parseMap(Class<KeyType> keyTypeClass, Class<ValueType> valueTypeClass, Object object) {
        getManager();
        Map<KeyType, ValueType> map = (Map<KeyType, ValueType>) mParseMap.get(object.getClass()).parseMap(valueTypeClass, object);
        return map;
    }

    public static ObjectParser getParseable(Class<?> parseableClass) {
        getManager();
        return mParseableMap.get(parseableClass);
    }

    static void addParseable(Class<?> parseableClass, ObjectParser objectParser) {
        mParseableMap.put(parseableClass, objectParser);
    }

    static void addParseInterface(Class<?> clazz, ParseInterface parseInterface) {
        mParseMap.put(clazz, parseInterface);
    }
}
