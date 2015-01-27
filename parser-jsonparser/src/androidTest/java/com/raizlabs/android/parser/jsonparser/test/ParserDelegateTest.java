package com.raizlabs.android.parser.jsonparser.test;

import android.test.AndroidTestCase;

import com.raizlabs.android.parser.Parser;
import com.raizlabs.android.parser.ParserUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Description: Tests the basic functionality of the {@link com.raizlabs.android.parser.ParserUtils} delegate classes.
 */
public class ParserDelegateTest extends AndroidTestCase {

    public void testKeyDelegate() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", "Test");
            jsonObject.put("candy", "Test2");
        } catch (JSONException j) {
        }

        ParserUtils.processKeys(jsonObject, new ParserUtils.KeyDelegate() {
            @Override
            public void execute(Object objectToRunOver, Parser parser, String key) {
                Object data = parser.getValue(objectToRunOver, key, null, false);
                assertTrue(data instanceof String);

                assertTrue(data.equals("Test") || data.equals("Test2"));
            }
        });
    }

    public void testListKeyDelegate() {
        JSONArray jsonArray = new JSONArray();
        for(int i = 0; i < 5; i++) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("name", "Test");
                jsonObject.put("candy", "Test2");
            } catch (JSONException j) {
            }
            jsonArray.put(jsonObject);
        }

        ParserUtils.processList(jsonArray, new ParserUtils.ListKeyDelegate() {
            @Override
            public void execute(Object objectToRunOver, Parser parser, int index) {
                Object jsonObject = parser.getObject(JSONObject.class, objectToRunOver, index);
                assertNotNull(jsonObject);

                String name = (String) parser.getValue(jsonObject, "name", "", true);
                String candy = (String) parser.getValue(jsonObject, "candy", "", true);

                assertNotNull(name);
                assertNotNull(candy);

                assertTrue(name.equals("Test"));
                assertTrue(candy.equals("Test2"));
            }
        });
    }
}
