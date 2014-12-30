package com.raizlabs.android.parser.test;


import com.raizlabs.android.parser.BaseParser;
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
 * Description: Example JSON parser
 */
@com.raizlabs.android.parser.core.ParseInterface
public class JsonParser extends BaseParser<JSONObject, JSONArray> {
    @Override
    public Object getValue(JSONObject object, String key, Object defValue, boolean required) {
        return JSON.getValue(object, key, defValue, required);
    }

    @Override
    public JSONObject getObject(JSONArray jsonArray, int index) {
        return JSON.getJSONObject(jsonArray, index);
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
