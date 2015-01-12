package com.raizlabs.android.parser.jsonparser;

import com.raizlabs.android.parser.ParseException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
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


    @SuppressWarnings("unchecked")
    public static <ReturnType> ReturnType get(Class<ReturnType> returnType, JSONArray jsonArray, int index) {
        if(jsonArray == null) {
            throw new ParseException("JSONArray was null");
        }
        Object value;
        if(returnType.equals(Boolean.class) || returnType.equals(boolean.class)) {
            value = jsonArray.optBoolean(index);
        } else if(returnType.equals(String.class)) {
            value = jsonArray.optString(index);
        } else if(returnType.equals(Long.class) || returnType.equals(long.class)) {
            value = jsonArray.optLong(index);
        } else if(returnType.equals(Integer.class) || returnType.equals(int.class)){
            value = jsonArray.optInt(index);
        } else if(returnType.equals(Double.class) || returnType.equals(double.class)) {
            value = jsonArray.optDouble(index);
        } else {
            value = jsonArray.opt(index);
        }
        return (ReturnType) value;
    }

    public static int count(JSONArray jsonArray) {
        if(jsonArray == null) {
            throw new ParseException("JSONArray was null");
        }
        return jsonArray.length();
    }

    public static List<String> keys(JSONObject jsonObject) {
        if(jsonObject == null) {
            throw new ParseException("JSONObject was null");
        }
        Iterator<String> keys = jsonObject.keys();
        List<String> keyList = new ArrayList<>();
        while(keys.hasNext()) {
            keyList.add(keys.next());
        }
        return keyList;
    }
}
