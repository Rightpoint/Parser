package com.raizlabs.android.parser;

import java.util.List;
import java.util.Map;

/**
 * Author: andrewgrosner
 * Contributors: { }
 * Description:
 */
public interface Parser<ObjectType, ListType> {

    public Object getValue(ObjectType object, String key);

    public Object parse(Class returnType, ObjectType object);

    public void parse(Object objectToParse, ObjectType data);

    public List parseList(Class returnType, ListType inData);

    public Object[] parseArray(Class returnType, ListType inData);

    /**
     * If a field is a {@link java.util.Map} we will parse json into it.
     * @param clazzType
     * @param objectType
     * @return
     */
    public Map parseMap(Class clazzType, ObjectType objectType);
}
