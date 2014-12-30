package com.raizlabs.android.parser;

import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Author: andrewgrosner
 * Description: Provides some handy ways of processing items.
 */
public class ParserUtils {

    /**
     * An interface which is executed when looping through the keys of an ObjectType Parser object.
     */
    public static interface KeyDelegate {

        /**
         * @param objectToRunOver The object data to use to retrieve the data using the specified key from the parser.
         * @param parser          The parser to retrieve data from
         * @param key             The key that is currently processed
         */
        public void execute(Object objectToRunOver, Parser parser, String key);
    }

    /**
     * An interface which is executed when looping through the ListType of a Parser Object
     */
    public static interface ListKeyDelegate {

        /**
         * @param objectToRunOver The ListType object
         * @param parser          The parser to retrieve data from
         * @param index           The index currently on
         */
        public void execute(Object objectToRunOver, Parser parser, int index);
    }

    @SuppressWarnings("unchecked")
    public static Object parse(Parser parser, Class<?> returnType, Object data) {
        ObjectParser objectParser = ParserHolder.getParseable(returnType);
        Object instance = objectParser.getInstance();
        objectParser.parse(instance, data, parser);
        return instance;
    }

    @SuppressWarnings("unchecked")
    public static void parse(Parser parser, Object instance, Object data) {
        ObjectParser objectParser = ParserHolder.getParseable(instance.getClass());
        objectParser.parse(instance, data, parser);
    }

    /**
     * Runs through the keys of the object specified. It must be the single object type of the specified
     * parser.
     *
     * @param objectToRunOver The object that contains a mapping of <String, Object></String,> values
     * @param keyDelegate     The delegate to execute with
     */
    @SuppressWarnings("unchecked")
    public static void processKeys(Object objectToRunOver, KeyDelegate keyDelegate) {
        if (objectToRunOver != null) {
            Parser parser = ParserHolder.getParser(objectToRunOver.getClass());
            List<String> keys = parser.keys(objectToRunOver);
            for (String key : keys) {
                keyDelegate.execute(objectToRunOver, parser, key);
            }
        }
    }

    /**
     * Runs through the indexes of the list object.
     *
     * @param listObjectToRunOver This will be passed into the delegate for processing
     * @param listKeyDelegate     The delegate to execute with`
     */
    @SuppressWarnings("unchecked")
    public static void processList(Object listObjectToRunOver, ListKeyDelegate listKeyDelegate) {
        if (listObjectToRunOver != null) {
            Parser parser = ParserHolder.getParser(listObjectToRunOver.getClass());
            int count = parser.count(listObjectToRunOver);
            for (int i = 0; i < count; i++) {
                listKeyDelegate.execute(listObjectToRunOver, parser, i);
            }
        }
    }

    /**
     * Retrieves the list object from the objectToRetrieveFrom and then will run through indexes of the list object.
     *
     * @param key                  The key to retrieve value with
     * @param objectToRetrieveFrom The object to retrieve the value with the specified key
     * @param listKeyDelegate      The delegate to execute with
     */
    public static void processList(String key, Object objectToRetrieveFrom, ListKeyDelegate listKeyDelegate) {
        if (objectToRetrieveFrom != null) {
            Parser parser = ParserHolder.getParser(objectToRetrieveFrom.getClass());
            Object listObjectToRunOver = parser.getValue(objectToRetrieveFrom, key, null, false);
            if (listObjectToRunOver != null) {
                processList(listObjectToRunOver, listKeyDelegate);
            }
        }
    }

    /**
     * Parses a listObject into a list of {@link ObjectType}
     *
     * @param parser          The parser to use
     * @param returnType      The {@link ObjectType} class
     * @param listClass       The list class to create
     * @param listObjectToUse The list type used in a Parser
     * @param <ObjectType>
     * @return A list of {@link ObjectType}
     */
    @SuppressWarnings("unchecked")
    public static <ObjectType, ListType> List<ObjectType> parseList(Parser<ObjectType, ListType> parser,
                                                          Class<ObjectType> returnType, Class<? extends List> listClass,
                                                          ListType listObjectToUse) {
        List<ObjectType> list = null;
        try {
            list = listClass.newInstance();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        int count = parser.count(listObjectToUse);
        for (int i = 0; i < count; i++) {
            list.add(ParserHolder.parse(returnType, parser.getObject(listObjectToUse, i)));
        }

        return list;
    }

    /**
     * Parses a ListObject into an Array of {@link ObjectType}
     *
     * @param parser          The parser to use
     * @param returnType      The return type
     * @param listObjectToUse The list type used in a Parser
     * @param <ObjectType>
     * @return An array of {@link ObjectType}
     */
    @SuppressWarnings("unchecked")
    public static <ObjectType, ListType> ObjectType[] parseArray(Parser<ObjectType, ListType> parser,
                                                       Class<ObjectType> returnType, ListType listObjectToUse) {

        int count = parser.count(listObjectToUse);
        ObjectType[] array = (ObjectType[]) Array.newInstance(returnType, count);

        for (int i = 0; i < count; i++) {
            array[i] = ParserHolder.parse(returnType, parser.getObject(listObjectToUse, i));
        }
        return array;
    }

    public static <ObjectType> Map<String, ObjectType> parseMap(Parser<ObjectType, ?> parser, Class<ObjectType> clazzType,
                                            Class<? extends Map> mapClass, ObjectType objectToParse) {
        Map<String, ObjectType> map = null;

        try {
            map = mapClass.newInstance();
        } catch (Throwable e) {
        }

        if(map != null) {
            List<String> keys = parser.keys(objectToParse);
            for(String key: keys) {
                map.put(key, ParserHolder.parse(clazzType, parser.getValue(objectToParse, key, null, true)));
            }
        }
        return map;
    }
}
