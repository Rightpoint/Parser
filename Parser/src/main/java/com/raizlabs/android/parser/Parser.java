package com.raizlabs.android.parser;

import java.util.List;
import java.util.Map;

/**
 * Author: andrewgrosner
 * Contributors: { }
 * Description: The main interface where you define how to parse data into other objects. This should never be accessed directly,
 * rather use the provided methods in {@link com.raizlabs.android.parser.ParserHolder}.
 */
public interface Parser<ObjectType, ListType> {

    /**
     * Use this method to specify how to retrieve the key from the object.
     *
     * @param object   The singular object to retrieve data from
     * @param key      The key to look up a value for
     * @param defValue Used when the value is not in the parser's data source
     * @return
     */
    public Object getValue(ObjectType object, String key, Object defValue);

    /**
     * Creates and returns an instance of the return type from the specified object. The returnType must be annotated
     * with {@link com.raizlabs.android.parser.core.Parseable}
     *
     * @param returnType The type of object to create
     * @param object     The data we are parsing.
     * @return new instance of the returnType
     */
    public Object parse(Class returnType, ObjectType object);

    /**
     * Overrides all annotated values from the specified objectToParse using the specified data.
     *
     * @param objectToParse The nonnull object to fill data in
     * @param data          The kind of data to parse into the object
     */
    public void parse(Object objectToParse, ObjectType data);

    /**
     * Creates and returns a list of parseable objects with the specified {@link ListType}.
     *
     * @param returnType The type of object to create.
     * @param inData     The list data we're parsing
     * @return
     */
    public List parseList(Class returnType, ListType inData);

    /**
     * Creates and returns an array of parseable objects with the specified {@link ListType}
     *
     * @param returnType The type of object to create.
     * @param inData     The list data we're parsing
     * @return
     */
    public Object[] parseArray(Class returnType, ListType inData);

    /**
     * Creates and returns a {@link java.util.Map} of a String-to-Parseable using the singular {@link ObjectType}.
     * Pretty much all keys are assumed to be Strings currently, but in the future may support all kinds.
     *
     * @param clazzType  The value-parseable class type
     * @param objectType The object to parse data from
     * @return
     */
    public Map parseMap(Class clazzType, ObjectType objectType);
}
