package com.raizlabs.android.parser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Author: andrewgrosner
 * Description: A handy class that assists in creating a {@link com.raizlabs.android.parser.Parser}. Call these methods for
 * your {@link com.raizlabs.android.parser.Parser} class when using JSON.
 */
public class JSON {

    public static Object getValue(JSONObject jsonObject, String key, Object defValue, boolean required) {
        Object value = null;
        if(jsonObject != null) {
            value = jsonObject.opt(key);
        }

        if(value == null) {
            if(required) {
                throw new ParseException("Required Key: " + key + " was missing from "
                        + (jsonObject != null ? jsonObject.toString() : " unspecified"));
            }
            value = defValue;
        }

        return value;
    }


    public static List parseList(Class<?> returnType, Class<? extends List> listClass, JSONArray inData) {
        List list = null;
        try {
            list = listClass.newInstance();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        for(int i = 0; i < inData.length(); i++) {
            list.add(ParserHolder.parse(returnType, inData.opt(i)));
        }

        return list;
    }


    public static Object[] parseArray(Class returnType, JSONArray inData) {

        Object[] array = (Object[]) Array.newInstance(returnType, inData.length());

        for(int i = 0; i < inData.length(); i++) {
            array[i] = ParserHolder.parse(returnType, inData.opt(i));
        }
        return array;
    }

    public static Map parseMap(Class clazzType, Class<? extends Map> mapClass, JSONObject jsonObject) {
        Map map = null;

        try {
            map = mapClass.newInstance();
        } catch (Throwable e) {
        }

        if(map != null) {
            Iterator<String> iterator = jsonObject.keys();
            while (iterator.hasNext()) {
                String key = iterator.next();
                map.put(key, ParserHolder.parse(clazzType, jsonObject.opt(key)));
            }
        }
        return map;
    }
}
