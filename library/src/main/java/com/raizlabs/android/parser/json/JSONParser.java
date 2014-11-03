package com.raizlabs.android.parser.json;

import com.raizlabs.android.parser.ClassFieldMap;
import com.raizlabs.android.parser.FieldParsible;
import com.raizlabs.android.parser.Ignore;
import com.raizlabs.android.parser.ObjectMerger;
import com.raizlabs.android.parser.Parser;
import com.raizlabs.android.parser.ParserUtils;
import com.raizlabs.android.parser.Setter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 */
public class JSONParser<Result, InputType, ListInputType> implements Parser<Result, InputType, ListInputType> {

    @Override
    public Result parse(Class inDataClass, InputType inData) {
        Result objectToParse = ParserUtils.getNewInstance(inDataClass);
        parse(objectToParse, inData);
        return objectToParse;
    }

    @Override
    public void parse(Result objectToParse, InputType data) {
        JSONObject object = null;
        if( data instanceof String ){
            try {
                object = new JSONObject((String) data);
            } catch (JSONException e) {

            }
        }
        else if(data instanceof JSONObject){
            object = (JSONObject) data;
        }
        if ( object != null && objectToParse != null ) {
            processJson(objectToParse, object, ClassFieldMap.getFieldMap(objectToParse.getClass()));
        }
    }

    @Override
    public <P extends List<Result>> P parseList(Class listClass, Class inDataClass, ListInputType inData) {
        JSONArray jsonArray = null;
        if( inData instanceof String ){
            try {
                jsonArray = new JSONArray((String) inData);
            } catch (JSONException e) {

            }
        } else if( inData instanceof JSONArray ){
            jsonArray = (JSONArray) inData;
        }

        P list = null;
        try {
            list = (P)listClass.newInstance();
            if( jsonArray != null ){
                for( int i = 0; i < jsonArray.length(); i++ ){
                    try {
                        if(inDataClass.getName().startsWith("java.lang")){
                            list.add((Result) jsonArray.get(i));
                        } else{
                            list.add(parse(inDataClass, (InputType)jsonArray.get(i)));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (InstantiationException e) {
        } catch (IllegalAccessException e) {
        }

        return list;
    }

    @Override
    public <Q extends Map> Q parseMap(Field field, InputType inData) {
        Q map  = null;
        if( inData instanceof JSONObject )
            try {
                map = (Q) ParserUtils.parseHashMap(this, field, (JSONObject) inData);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        return map;
    }

    @Override
    public <P> P[] parseArray(Class<P> arrayClass, ListInputType inData) {
        JSONArray jsonArray = (JSONArray) inData;
        P[] array = (P[]) Array.newInstance(arrayClass, jsonArray.length());
        for(int i = 0; i < jsonArray.length(); i++){
            try {
                if(arrayClass.getName().startsWith("java.lang")){
                    Array.set(array, i, jsonArray.get(i));
                } else{
                    Array.set(array, i, parse(arrayClass, (InputType) jsonArray.get(i)));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return array;
    }

    Result processJson(Result inDataObject, JSONObject inJSONObject, HashMap<String,Field> inFieldsMap) {

        // Check to see if the object is an Object Merger, we will merge the data here
        if(inDataObject instanceof ObjectMerger) {
            merge(inJSONObject, ((ObjectMerger) inDataObject));
        }

        // Loop through the rest of the fields
        ArrayList<String> keys = ParserUtils.mergeKeysAndFields(inJSONObject.keys(), inFieldsMap.keySet());
        for (String key: keys) {
            Object valueFromJSON = null;
            try {
                Field f = inFieldsMap.get(key);
                if(f!=null) {
                    f.setAccessible(true);
                    if(f.isAnnotationPresent(Ignore.class)){
                        // Ignoring the specified field
                        continue;
                    }
                }

                // If a field is an Object Merger, it will handle some of the keys
                if(f != null && ObjectMerger.class.isAssignableFrom(f.getType())) {
                    ObjectMerger merger = (ObjectMerger) f.get(inDataObject);
                    merge(inJSONObject, merger);
                } else {

                    // Wrap the field name in the Field name parser
                    valueFromJSON = inJSONObject.opt(ClassFieldMap
                            .getFieldNameParser(inDataObject).getKey(key));
                    if(valueFromJSON != null && (!(inDataObject instanceof FieldParsible) ||
                            !((FieldParsible) inDataObject).parseField(key, new Setter(f, inDataObject),
                                    valueFromJSON)) && f != null) {
                        if (valueFromJSON instanceof JSONObject) {
                            ParserUtils.parseObjectType(inDataObject, f, this, ((InputType) valueFromJSON));
                        } else if (valueFromJSON instanceof JSONArray) {
                            ParserUtils.parseArrayType(inDataObject, f, this, (ListInputType) valueFromJSON);
                        } else {
                            ParserUtils.parseStringConstructorType(inDataObject, f, valueFromJSON.toString());
                        }
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return inDataObject;
    }

    private void merge(JSONObject inJSONObject, ObjectMerger merger) {
        ArrayList<String> keys = merger.getKeys();
        for(String mergeKey: keys) {
            merger.merge(mergeKey, inJSONObject.opt(mergeKey));
        }
    }

}
