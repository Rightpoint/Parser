package com.raizlabs.android.parser.json;

import com.raizlabs.android.parser.ObjectParser;
import com.raizlabs.android.parser.ParserHolder;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Author: andrewgrosner
 * Contributors: { }
 * Description:
 */
public class JSON {

    public static Object parse(Class<?> returnType, JSONObject jsonObject) {
        ObjectParser objectParser = ParserHolder.getParseable(returnType);
        Object instance = objectParser.getInstance();
        parse(instance, jsonObject);
        return instance;
    }


    public static void parse(Object instance, JSONObject jsonObject) {
        ObjectParser objectParser = ParserHolder.getParseable(instance.getClass());
        Iterator<String> keys = jsonObject.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            objectParser.setValue(instance, key, jsonObject.opt(key));
        }
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
