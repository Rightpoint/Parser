package com.raizlabs.android.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: Provides a base implementation for parsers.
 */
public abstract class BaseParser<ObjectType, ListType> implements Parser<ObjectType, ListType> {

    @Override
    public Object parse(Class returnType, ObjectType object) {
        return ParserUtils.parse(this, returnType, object);
    }

    @Override
    public void parse(Object objectToParse, ObjectType data) {
        ParserUtils.parse(this, objectToParse, data);
    }

    @Override
    public List<ObjectType> parseList(Class<ObjectType> returnType, ListType inData) {
        return ParserUtils.parseList(this, returnType, ArrayList.class, inData);
    }

    @Override
    public ObjectType[] parseArray(Class<ObjectType> returnType, ListType inData) {
        return ParserUtils.parseArray(this, returnType, inData);
    }

    @Override
    public Map<String, ObjectType> parseMap(Class<ObjectType> clazzType, ObjectType objectType) {
        return ParserUtils.parseMap(this, clazzType, HashMap.class, objectType);
    }
}
