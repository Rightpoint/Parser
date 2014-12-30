package com.raizlabs.android.parserapp.app;


import com.raizlabs.android.parser.BaseParser;
import com.raizlabs.android.parser.JSON;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Author: andrewgrosner
 * Description:
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
