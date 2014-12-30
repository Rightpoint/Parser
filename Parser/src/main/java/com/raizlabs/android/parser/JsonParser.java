package com.raizlabs.android.parser;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Author: andrewgrosner
 * Description: JSON parser that you extend to get it's implementation.
 */
@com.raizlabs.android.parser.core.ParseInterface
public class JsonParser extends BaseParser<JSONObject, JSONArray> {
    @Override
    public Object getValue(JSONObject object, String key, Object defValue, boolean required) {
        return JSON.getValue(object, key, defValue, required);
    }

    @Override
    public <ReturnType> ReturnType getObject(Class<ReturnType> returnType, JSONArray jsonArray, int index) {
        return JSON.get(returnType, jsonArray, index);
    }

    @Override
    public List<String> keys(JSONObject jsonObject) {
        return JSON.keys(jsonObject);
    }

    @Override
    public int count(JSONArray jsonArray) {
        return JSON.count(jsonArray);
    }
}
