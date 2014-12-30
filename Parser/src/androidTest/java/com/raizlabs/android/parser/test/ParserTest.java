package com.raizlabs.android.parser.test;

import android.test.AndroidTestCase;
import com.raizlabs.android.parser.ParserHolder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Description: Tests the basic functionality of the library
 */
public class ParserTest extends AndroidTestCase {

    public void testSimpleClass() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", "Test");
            jsonObject.put("date", 1000l);
            jsonObject.put("math", 2.5d);
            jsonObject.put("truth", true);
            jsonObject.put("number", 5);
            jsonObject.put("hidden", "Hidden Here");

            JSONObject simpleClass = new JSONObject();
            simpleClass.put("name", "Testy");
            jsonObject.put("otherClass", simpleClass);

            JSONArray jsonArray = new JSONArray();
            jsonArray.put(simpleClass);
            jsonArray.put(simpleClass);
            jsonObject.put("simpleClasses", jsonArray);
            jsonObject.put("simpleClassList", jsonArray);

            JSONArray stringList = new JSONArray();
            jsonObject.put("stringList", stringList);
            stringList.put("This");
            stringList.put("Is");
            stringList.put("A");
            stringList.put("Test");

            JSONObject mapTest = new JSONObject();
            mapTest.put("This", simpleClass);
            mapTest.put("Is", simpleClass);
            jsonObject.put("simpleClassMap", mapTest);
        } catch (JSONException e) {
        }

        ComplexClass complexClass = ParserHolder.parse(ComplexClass.class, jsonObject);
        testComplexClass(complexClass);
        assertEquals(complexClass.otherClass.name, "Testy");
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
        assertEquals(complexClass.hidden, "Hidden Here");
        assertEquals(complexClass.name, "Test");
        assertEquals(complexClass.date, 1000);
        assertEquals(complexClass.math, 2.5d);
        assertEquals(complexClass.truth, true);
        assertEquals(complexClass.number, 5);
        assertEquals(complexClass.simpleFieldParser.hidden, "Hidden Here");
        assertNotNull(complexClass.stringList);
    }
}
