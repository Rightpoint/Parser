package com.raizlabs.android.parser.fastjsonparser;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.raizlabs.android.parser.ParseException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Description: Provides the methods for Fastjson parsing through this library.
 */
public class FastJsonUtils {

    /**
     * @param jsonObject The fastjson object to retrieve value from.
     * @param key        The key to use to retrieve
     * @param defValue   The default value to use if null and not required.
     * @param required   If true and value is null, the {@link com.raizlabs.android.parser.ParseException} will be thrown.
     * @return the value from the specified {@link com.alibaba.fastjson.JSONObject}. If a value is null
     * and required, a {@link com.raizlabs.android.parser.ParseException} will be thrown. If it is just
     * purely null, the default value is assigned to it. The default value is automatically chosen by the
     * compiler or by using the {@link com.raizlabs.android.parser.core.Key#defValue()} method.
     */
    public static Object getValue(JSONObject jsonObject, String key, Object defValue, boolean required) {
        Object value = null;
        if (jsonObject != null) {
            value = jsonObject.get(key);
        }

        // some reason doubles are treated as BigDecimals
        if (value instanceof BigDecimal) {
            value = ((BigDecimal) value).doubleValue();
        }

        if (value == null) {
            if (required) {
                throw new ParseException("Required Key: " + key + " was missing from "
                        + (jsonObject != null ? jsonObject.toString() : " unspecified"));
            }
            value = defValue;
        }

        return value;
    }

    /**
     * @param jsonArray The array to use.
     * @return the count of the {@link com.alibaba.fastjson.JSONArray}. If the array is null,
     * a {@link com.raizlabs.android.parser.ParseException} is thrown.
     */
    public static int count(JSONArray jsonArray) {
        if (jsonArray == null) {
            throw new ParseException("JSONArray was null");
        }
        return jsonArray.size();
    }

    /**
     * @param jsonObject
     * @return The list of keys from the {@link com.alibaba.fastjson.JSONObject} specified. If it
     * is null, {@link com.raizlabs.android.parser.ParseException} is thrown.
     */
    public static List<String> keys(JSONObject jsonObject) {
        if (jsonObject == null) {
            throw new ParseException("JSONObject was null");
        }
        return new ArrayList<>(jsonObject.keySet());
    }

    /**
     * @param returnType   The type of data to return
     * @param jsonArray    The array to use
     * @param index        The index of the array to retrieve
     * @param <ReturnType> The type of object that we return
     * @return the value from the {@link com.alibaba.fastjson.JSONArray}. If the value is a certain kind,
     * we call the appropriate {@link com.alibaba.fastjson.JSONArray} method so it converts the value as expected.
     */
    @SuppressWarnings("unchecked")
    public static <ReturnType> ReturnType get(Class<ReturnType> returnType, JSONArray jsonArray, int index) {
        if (jsonArray == null) {
            throw new ParseException("JSONArray was null");
        }
        Object value;
        if (returnType.equals(Boolean.class)) {
            value = jsonArray.getBoolean(index);
        } else if (returnType.equals(boolean.class)) {
            value = jsonArray.getBooleanValue(index);
        } else if (returnType.equals(String.class)) {
            value = jsonArray.getString(index);
        } else if (returnType.equals(Long.class)) {
            value = jsonArray.getLong(index);
        } else if (returnType.equals(long.class)) {
            value = jsonArray.getLongValue(index);
        } else if (returnType.equals(Integer.class)) {
            value = jsonArray.getInteger(index);
        } else if (returnType.equals(int.class)) {
            value = jsonArray.getIntValue(index);
        } else if (returnType.equals(Double.class)) {
            value = jsonArray.getDouble(index);
        } else if (returnType.equals(double.class)) {
            value = jsonArray.getDoubleValue(index);
        } else if (returnType.equals(BigDecimal.class)) {
            value = jsonArray.getBigDecimal(index);
        } else if (returnType.equals(BigInteger.class)) {
            value = jsonArray.getBigInteger(index);
        } else if (returnType.equals(Float.class)) {
            value = jsonArray.getFloat(index);
        } else if (returnType.equals(float.class)) {
            value = jsonArray.getFloatValue(index);
        } else if (returnType.equals(Short.class)) {
            value = jsonArray.getShort(index);
        } else if (returnType.equals(short.class)) {
            value = jsonArray.getShortValue(index);
        } else if (returnType.equals(Byte.class)) {
            value = jsonArray.getByte(index);
        } else if (returnType.equals(byte.class)) {
            value = jsonArray.getByteValue(index);
        } else {
            value = jsonArray.get(index);
        }
        return (ReturnType) value;
    }
}
