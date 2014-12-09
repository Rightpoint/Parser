package com.raizlabs.android.parserapp.app;


import com.raizlabs.android.parser.Parser;
import com.raizlabs.android.parser.JSON;
import com.raizlabs.android.parser.ParserUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
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
    public Object getValue(JSONObject object, String key, Object defValue, boolean required) {
        return JSON.getValue(object, key, defValue, required);
    }

    @Override
    public Object parse(Class returnType, JSONObject object) {
        return ParserUtils.parse(this, returnType, object);
    }

    @Override
    public void parse(Object objectToParse, JSONObject data) {
        ParserUtils.parse(this, objectToParse, data);
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
        return JSON.parseMap(clazzType, HashMap.class, jsonObject);
    }
}
