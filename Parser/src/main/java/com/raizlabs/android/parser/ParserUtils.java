package com.raizlabs.android.parser;

import org.json.JSONObject;

/**
 * Author: andrewgrosner
 * Contributors: { }
 * Description:
 */
public class ParserUtils {
    public static Object parse(Parser parser, Class<?> returnType, Object data) {
        ObjectParser objectParser = ParserHolder.getParseable(returnType);
        Object instance = objectParser.getInstance();
        parse(parser, instance, data);
        return instance;
    }

    public static void parse(Parser parser, Object instance, Object data) {
        ObjectParser objectParser = ParserHolder.getParseable(instance.getClass());
        objectParser.parse(instance, data, parser);
    }
}
