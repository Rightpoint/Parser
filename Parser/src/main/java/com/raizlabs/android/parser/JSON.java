package com.raizlabs.android.parser;

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


    public static JSONObject getJSONObject(JSONArray jsonArray, int index) {
        if(jsonArray == null) {
            throw new ParseException("JSONArray was null");
        }
        try {
            return jsonArray.getJSONObject(index);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
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
