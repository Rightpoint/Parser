package com.raizlabs.android.parser.test;

import android.test.AndroidTestCase;
import com.raizlabs.android.parser.ParserHolder;
import com.raizlabs.android.parser.json.JSONParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Author: andrewgrosner
 * Contributors: { }
 * Description:
 */
public class ParserTest extends AndroidTestCase {

    private static JSONParser<SimpleClass, JSONObject, JSONArray> parser = new JSONParser<>();

    public void testSimpleClass() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", "Test");
            jsonObject.put("date", 1000l);
            jsonObject.put("math", 2.5d);
            jsonObject.put("truth", true);
            jsonObject.put("number", 5);

            JSONObject simpleClass = new JSONObject();
            simpleClass.put("name", "Testy");
            jsonObject.put("otherClass", simpleClass);

            JSONArray jsonArray = new JSONArray();
            jsonArray.put(simpleClass);
            jsonArray.put(simpleClass);
            jsonObject.put("simpleClasses", jsonArray);
            jsonObject.put("simpleClassList", jsonArray);

            JSONObject mapTest = new JSONObject();
            mapTest.put("This", simpleClass);
            mapTest.put("Is", simpleClass);
            jsonObject.put("simpleClassMap", mapTest);
        } catch (JSONException e) {
        }

        ComplexClass complexClass = ParserHolder.parse(ComplexClass.class, jsonObject);
        testComplexClass(complexClass);
        testSimpleClass(complexClass.otherClass);
        SimpleClass[] simpleClasses = complexClass.simpleClasses;
        for(SimpleClass simpleClass: simpleClasses) {
            testSimpleClass(simpleClass);
        }

        List<SimpleClass> simpleClassList = complexClass.simpleClassList;
        for(SimpleClass simpleClass: simpleClassList) {
            testSimpleClass(simpleClass);
        }

        Map<String, SimpleClass> simpleClassMap = complexClass.simpleClassMap;
        Set<String> keys = simpleClassMap.keySet();
        for(String key: keys) {
            testSimpleClass(simpleClassMap.get(key));
        }
    }

    private void testSimpleClass(SimpleClass simpleClass) {
        assertEquals(simpleClass.name, "Testy");
    }

    private void testComplexClass(ComplexClass complexClass) {
        assertEquals(complexClass.name, "Test");
        assertEquals(complexClass.date, 1000);
        assertEquals(complexClass.math, 2.5d);
        assertEquals(complexClass.truth, true);
        assertEquals(complexClass.number, 5);
    }
}
