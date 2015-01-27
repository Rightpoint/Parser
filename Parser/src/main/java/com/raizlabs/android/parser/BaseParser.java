package com.raizlabs.android.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: Provides a base implementation for parsers.
 */
public abstract class BaseParser<ObjectType, ListType> implements Parser<ObjectType, ListType> {


    /**
     * Use this method to specify how to retrieve data from the object. It defaults required to false,
     * so if not found, no {@link com.raizlabs.android.parser.ParseException} is thrown.
     *
     * @param object   The singular object we return data from.
     * @param key      The key to look up the value for
     * @param defValue Used when the value is not in the object passed in.
     * @return The value from the underlying parse object.
     */
    public Object getValue(ObjectType object, String key, Object defValue) {
        return getValue(object, key, defValue, false);
    }

    @Override
    public Object parse(Class returnType, ObjectType object) {
        return ParserUtils.parse(this, returnType, object);
    }

    @Override
    public void parse(Object objectToParse, ObjectType data) {
        ParserUtils.parse(this, objectToParse, data);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List parseList(Class returnType, ListType inData) {
        return ParserUtils.parseList(this, returnType, ArrayList.class, inData);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object[] parseArray(Class returnType, ListType inData) {
        return ParserUtils.parseArray(this, returnType, inData);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map parseMap(Class clazzType, ObjectType objectType) {
        return ParserUtils.parseMap(this, clazzType, HashMap.class, objectType);
    }
}
