package com.raizlabs.android.parser;

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
}
