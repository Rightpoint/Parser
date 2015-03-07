package com.raizlabs.android.parser.jsonparser.test;

import android.test.AndroidTestCase;

import com.raizlabs.android.parser.ParserHolder;

import org.json.JSONObject;

/**
 * Description:
 */
public class MergeableTest extends AndroidTestCase {


    public void testMergeable() throws Exception {
        MergeableClass mergeableClass = new MergeableClass();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "OldName");
        jsonObject.put("notMergeable", true);

        ParserHolder.parse(mergeableClass, jsonObject);

        jsonObject = new JSONObject();
        jsonObject.put("isSet", true);

        ParserHolder.parse(mergeableClass, jsonObject);

        assertTrue(mergeableClass.isSet);
        assertEquals("OldName", mergeableClass.name);
        assertFalse(mergeableClass.notMergeable);
    }
}
