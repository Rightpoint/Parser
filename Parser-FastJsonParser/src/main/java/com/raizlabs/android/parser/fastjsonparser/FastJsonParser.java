package com.raizlabs.android.parser.fastjsonparser;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.raizlabs.android.parser.BaseParser;

import java.util.List;

/**
 * Description: Defines the {@link com.raizlabs.android.parser.Parser} for FastJSON types.
 */
public class FastJsonParser extends BaseParser<JSONObject, JSONArray> {

    @Override
    public Object getValue(JSONObject jsonObject, String key, Object defValue, boolean required) {
        return FastJsonUtils.getValue(jsonObject, key, defValue, required);
    }

    @Override
    public <ReturnType> ReturnType getObject(Class<ReturnType> returnType, JSONArray jsonArray, int index) {
        return FastJsonUtils.get(returnType, jsonArray, index);
    }

    @Override
    public List<String> keys(JSONObject jsonObject) {
        return FastJsonUtils.keys(jsonObject);
    }

    @Override
    public int count(JSONArray jsonArray) {
        return FastJsonUtils.count(jsonArray);
    }
}
