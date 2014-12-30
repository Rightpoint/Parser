package com.raizlabs.android.parser;

import java.util.List;

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
}
