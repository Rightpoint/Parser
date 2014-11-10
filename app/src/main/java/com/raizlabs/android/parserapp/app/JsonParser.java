package com.raizlabs.android.parserapp.app;


import com.raizlabs.android.parser.Parser;
import com.raizlabs.android.parser.JSON;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author: andrewgrosner
 * Contributors: { }
 * Description:
 */
@com.raizlabs.android.parser.core.ParseInterface
public class JsonParser implements Parser<JSONObject, JSONArray> {
    @Override
    public Object getValue(JSONObject object, String key) {
        return object.opt(key);
    }

    @Override
    public Object parse(Class returnType, JSONObject object) {
        return JSON.parse(this, returnType, object);
    }

    @Override
    public void parse(Object objectToParse, JSONObject data) {
        JSON.parse(this, objectToParse, data);
    }

    @Override
    public List parseList(Class returnType, JSONArray inData) {
        return JSON.parseList(returnType, ArrayList.class, inData);
    }

    @Override
    public Object[] parseArray(Class returnType, JSONArray inData) {
        return JSON.parseArray(returnType, inData);
    }

    @Override
    public Map parseMap(Class clazzType, JSONObject jsonObject) {
        return null;
    }
}
