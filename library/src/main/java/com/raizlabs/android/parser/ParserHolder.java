package com.raizlabs.android.parser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: andrewgrosner
 * Description: The main class to parse data from. It requires at least one {@link com.raizlabs.android.parser.Parser} be defined for this
 * class to work as expected. Also, each {@link com.raizlabs.android.parser.Parser} must not reuse similar classes such as two using {@link org.json.JSONObject}.
 */
public class ParserHolder {

    /**
     * Filled in by the compiler as a mapping between object data and what parser it corresponds to.
     */
    private static Map<Class<?>, Parser> mParseMap = new HashMap<>();

    /**
     * The map between a class and it's defined parser that describes how it gets filled in by a {@link com.raizlabs.android.parser.Parser}
     */
    private static Map<Class<?>, ObjectParser> mParseableMap = new HashMap<>();

    /**
     * Simply, when created, will fill this class with all of the {@link com.raizlabs.android.parser.Parser} and {@link com.raizlabs.android.parser.ObjectParser} needed.
     */
    private static ParserManagerInterface manager;

    /**
     * Creates the manager, which in turn will fill this class with all of the {@link com.raizlabs.android.parser.Parser} and {@link com.raizlabs.android.parser.ObjectParser} needed.
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
     * Creates and returns an instance of the class specified with the data passed in. Look to this method for singular object types such
     * as {@link org.json.JSONObject}
     *
     * @param returnTypeClass The class that's required to use the {@link com.raizlabs.android.parser.core.Parseable} annotation
     * @param object          The type of data to parse such as {@link org.json.JSONObject}
     * @param <ReturnType>    The class that's required to use the {@link com.raizlabs.android.parser.core.Parseable} annotation
     * @return singular {@link ReturnType}
     */
    @SuppressWarnings("unchecked")
    public static <ReturnType> ReturnType parse(Class<ReturnType> returnTypeClass, Object object) {
        getManager();
        return (ReturnType) getParser(object.getClass()).parse(returnTypeClass, object);
    }

    /**
     * Creates and returns an array of the class specified with the arrayType passed in. Look to this method for array object types
     * such as {@link org.json.JSONArray}
     *
     * @param returnTypeClass The class that's required to use the {@link com.raizlabs.android.parser.core.Parseable} annotation
     * @param arrayType       The type of data to parse such as {@link org.json.JSONArray}
     * @param <ReturnType>    The class that's required to use the {@link com.raizlabs.android.parser.core.Parseable} annotation
     * @return array of {@link ReturnType}
     */
    @SuppressWarnings("unchecked")
    public static <ReturnType> ReturnType[] parseArray(Class<ReturnType> returnTypeClass, Object arrayType) {
        getManager();
        return (ReturnType[]) getParser(arrayType.getClass()).parseArray(returnTypeClass, arrayType);
    }

    /**
     * Creates and returns a List of the class specified with the arrayType passed in. Look to this method for array object types
     * such as {@link org.json.JSONArray}
     *
     * @param returnTypeClass The class that's required to use the {@link com.raizlabs.android.parser.core.Parseable} annotation
     * @param listType        The type of data to parse such as {@link org.json.JSONArray}
     * @param <ReturnType>    The class that's required to use the {@link com.raizlabs.android.parser.core.Parseable} annotation
     * @return array of {@link ReturnType}
     */
    @SuppressWarnings("unchecked")
    public static <ReturnType> List<ReturnType> parseList(Class<ReturnType> returnTypeClass, Object listType) {
        getManager();
        return (List<ReturnType>) getParser(listType.getClass()).parseList(returnTypeClass, listType);
    }

    /**
     * Creates and returns a Map of the class specified with the data passed in. Look to this method for singular object types
     * such as {@link org.json.JSONObject}
     *
     * @param keyTypeClass   The class that (so far) requires to be a String
     * @param valueTypeClass The class that's required to use the {@link com.raizlabs.android.parser.core.Parseable} annotation
     * @param object         The type of data to parse such as {@link org.json.JSONObject}
     * @param <ReturnType>   The class that's required to use the {@link com.raizlabs.android.parser.core.Parseable} annotation
     * @return map of {@link KeyType} and {@link ValueType}
     */
    @SuppressWarnings("unchecked")
    public static <KeyType, ValueType> Map<KeyType, ValueType> parseMap(Class<KeyType> keyTypeClass, Class<ValueType> valueTypeClass, Object object) {
        getManager();
        return (Map<KeyType, ValueType>) getParser(object.getClass()).parseMap(valueTypeClass, object);
    }

    /**
     * Returns the {@link com.raizlabs.android.parser.ObjectParser} for the specified class.
     *
     * @param parseableClass
     * @return
     */
    public static ObjectParser getParseable(Class<?> parseableClass) {
        getManager();
        return mParseableMap.get(parseableClass);
    }

    /**
     * Returns the {@link com.raizlabs.android.parser.Parser} for the specified data class such as {@link org.json.JSONObject}
     *
     * @param objectType
     * @return
     */
    public static Parser getParser(Class<?> objectType) {
        getManager();
        Parser parser = mParseMap.get(objectType);
        if (parser == null) {
            throw new RuntimeException("No parser has been created for: " + objectType);
        }
        return parser;
    }

    static void addParseable(Class<?> parseableClass, ObjectParser objectParser) {
        mParseableMap.put(parseableClass, objectParser);
    }

    static void addParseInterface(Class<?> clazz, Parser parser) {
        mParseMap.put(clazz, parser);
    }
}
