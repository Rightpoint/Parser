package com.raizlabs.android.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: andrewgrosner
 * Description: The main class to parse data from. It requires at least one
 * {@link com.raizlabs.android.parser.Parser} be defined for this class to work as expected.
 * Also, each {@link com.raizlabs.android.parser.Parser} must not reuse similar classes such as two using JSONObject.
 */
public class ParserHolder {

    /**
     * Filled in by the compiler as a mapping between object data and what parser it corresponds to.
     */
    private static Map<Class<?>, Parser> mParseMap = new HashMap<>();

    /**
     * The map between a class and it's defined parser that describes how it gets filled in by a {@link com.raizlabs.android.parser.Parser}
     */
    private static Map<Class<?>, ParseHandler> mParseableMap = new HashMap<>();

    /**
     * Simply, when created, will fill this class with all of the {@link com.raizlabs.android.parser.Parser} and {@link ParseHandler} needed.
     */
    private static ParserManagerInterface manager;

    /**
     * Creates the manager, which in turn will fill this class with all of the {@link com.raizlabs.android.parser.Parser} and {@link ParseHandler} needed.
     *
     * @return
     */
    private static ParserManagerInterface getManager() {
        if (manager == null) {
            try {
                manager = (ParserManagerInterface) Class.forName("com.raizlabs.android.parser.ParserManager").newInstance();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
        return manager;
    }

    /**
     * Creates and returns an instance of the class specified with the data passed in. Look to this method for singular object types from
     * the parser.
     *
     * @param returnTypeClass The class that's required to use the {@link com.raizlabs.android.parser.core.Parseable} annotation
     * @param object          The type of data to parse such as a JSONObject
     * @param <ReturnType>    The class that's required to use the {@link com.raizlabs.android.parser.core.Parseable} annotation
     * @return singular {@link ReturnType}
     */
    @SuppressWarnings("unchecked")
    public static <ReturnType> ReturnType parse(Class<ReturnType> returnTypeClass, Object object) {
        getManager();
        ReturnType returnType = null;
        if (object != null) {
            returnType = (ReturnType) getParser(object.getClass()).parse(returnTypeClass, object);
        }
        return returnType;
    }

    /**
     * Parses data safely by checking to see if the data has a parser. If not, nothing will happen.
     *
     * @param object
     * @param <ReturnType>
     * @return
     */
    static <ReturnType> ReturnType parseSafe(Class<ReturnType> returnTypeClass, Object object) {
        ReturnType retObject = null;
        if (ParserHolder.hasParser(object.getClass())) {
            retObject = (ReturnType) ParserHolder.parse(returnTypeClass, object);
        } else {
            retObject = (ReturnType) object;
        }

        return retObject;
    }

    /**
     * Parses an existing instance of the class with the data passed in.
     *
     * @param returnType   The object that's required to use the {@link com.raizlabs.android.parser.core.Parseable} annotation
     * @param object       The type of data to parse such as JSONObject
     * @param <ReturnType> Type of data to return
     */
    @SuppressWarnings("unchecked")
    public static <ReturnType> void parse(ReturnType returnType, Object object) {
        getManager();
        if (object != null) {
            getParser(object.getClass()).parse(returnType, object);
        }
    }

    /**
     * Creates and returns an array of the class specified with the arrayType passed in. Look to this method for array object types
     * such as JSONArray
     *
     * @param returnTypeClass The class that's required to use the {@link com.raizlabs.android.parser.core.Parseable} annotation
     * @param arrayType       The type of data to parse such as JSONArray
     * @param <ReturnType>    The class that's required to use the {@link com.raizlabs.android.parser.core.Parseable} annotation
     * @return array of {@link ReturnType}
     */
    @SuppressWarnings("unchecked")
    public static <ReturnType> ReturnType[] parseArray(Class<ReturnType> returnTypeClass, Object arrayType) {
        getManager();
        ReturnType[] returnTypes = null;
        if (arrayType != null) {
            returnTypes = (ReturnType[]) getParser(arrayType.getClass()).parseArray(returnTypeClass, arrayType);
        }
        return returnTypes;
    }

    /**
     * Creates and returns a List of the class specified with the arrayType passed in. Look to this method for array object types
     * such as JSONArray
     *
     * @param returnTypeClass The class that's required to use the {@link com.raizlabs.android.parser.core.Parseable} annotation
     * @param listType        The type of data to parse such as JSONArray
     * @param <ReturnType>    The class that's required to use the {@link com.raizlabs.android.parser.core.Parseable} annotation
     * @return array of {@link ReturnType}
     */
    @SuppressWarnings("unchecked")
    public static <ReturnType> List<ReturnType> parseList(Class<ReturnType> returnTypeClass, Object listType) {
        getManager();
        List<ReturnType> returnTypes = null;
        if (listType != null) {
            returnTypes = (List<ReturnType>) getParser(listType.getClass()).parseList(returnTypeClass, listType);
        }
        return returnTypes;
    }

    /**
     * Creates and returns a Map of the class specified with the data passed in. Look to this method for singular object types
     * such as JSONObject
     *
     * @param keyTypeClass   The class that (so far) requires to be a String
     * @param valueTypeClass The class that's required to use the {@link com.raizlabs.android.parser.core.Parseable} annotation
     * @param object         The type of data to parse such as JSONObject
     * @param <KeyType>      The class (mostly a string) that we use for a key
     * @param <ValueType>    The class that's required to use the {@link com.raizlabs.android.parser.core.Parseable} annotation
     * @return map of {@link KeyType} and {@link ValueType}
     */
    @SuppressWarnings("unchecked")
    public static <KeyType, ValueType> Map<KeyType, ValueType> parseMap(Class<KeyType> keyTypeClass, Class<ValueType> valueTypeClass, Object object) {
        getManager();
        Map<KeyType, ValueType> map = null;
        if (object != null) {
            map = (Map<KeyType, ValueType>) getParser(object.getClass()).parseMap(valueTypeClass, object);
        }
        return map;
    }

    /**
     * @param parseableClass The class that is annotated with {@link com.raizlabs.android.parser.core.Parseable}
     * @return the {@link ParseHandler} for the specified class.
     */
    public static ParseHandler getParseable(Class<?> parseableClass) {
        getManager();
        return mParseableMap.get(parseableClass);
    }

    /**
     * Returns the {@link com.raizlabs.android.parser.Parser} for the specified data class such as JSONObject
     *
     * @param objectType The kind of object that is registered for a {@link com.raizlabs.android.parser.Parser}
     * @return The parser for the specified object
     */
    public static Parser getParser(Class<?> objectType) {
        getManager();
        Parser parser = mParseMap.get(objectType);
        if (parser == null) {
            throw new RuntimeException("No parser has been created for: " + objectType);
        }
        return parser;
    }

    /**
     * internal method that will add each {@link ParseHandler} automatically
     *
     * @param parseableClass The class that is annotated with {@link com.raizlabs.android.parser.core.Parseable}
     * @param parseHandler   The corresponding {@link ParseHandler} for that class.
     */
    static void addParseable(Class<?> parseableClass, ParseHandler parseHandler) {
        mParseableMap.put(parseableClass, parseHandler);
    }

    /**
     * Internal method for adding a {@link com.raizlabs.android.parser.Parser} for the specified data type class such as
     * JSONObject.
     *
     * @param clazz  Used when we parse an object of certain type, it is used to retrieve the {@link com.raizlabs.android.parser.Parser} for it.
     * @param parser Describes how to parse the specified data type
     */
    static void addParseInterface(Class<?> clazz, Parser parser) {
        mParseMap.put(clazz, parser);
    }

    /**
     * @param clazz The data type to parse.
     * @return True if there is a {@link com.raizlabs.android.parser.Parser} for the specified type.
     */
    public static boolean hasParser(Class<?> clazz) {
        return mParseMap.containsKey(clazz);
    }


}
