package com.raizlabs.android.parser;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * Author: Andrew Grosner
 * Description:
 */
public interface Parser<Result, InputType, ArrayInputType> {

    /**
     * The main method by which we parse data, implement this method to gather information from the inData object
     * @param  inDataClass the object of which we fill in the inData with
     * @param inData the object of data we want to parse
     */
    public Result parse(Class inDataClass, InputType inData);

    /**
     * Parse an already existing object passing in the type of object to parse from
     * @param objectToParse - the object where we fill the data into
     * @param data - the data that we gather information from. i.e: JsonObject
     */
    public void parse(Result objectToParse, InputType data);

    /**
     * This will handle list objects
     * @param listClass
     * @param inDataClass
     * @param inData
     * @param <P>
     * @return
     */
    public <P extends List<Result>> P parseList(Class listClass, Class inDataClass, ArrayInputType inData);

    /**
     * This handles data where there's keys and values
     * @param field
     * @param inData
     * @param <Q>
     * @return
     */
    <Q extends Map> Q parseMap(Field field, InputType inData);

    /**
     * This returns an array of objects
     * @param arrayClass
     * @param inData
     * @param <P>
     * @return
     */
    public <P> P[] parseArray(Class<P> arrayClass, ArrayInputType inData);
}
