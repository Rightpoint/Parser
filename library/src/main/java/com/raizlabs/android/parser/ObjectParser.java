package com.raizlabs.android.parser;

/**
 * Author: andrewgrosner
 * Contributors: { }
 * Description: Internal usage that will generate how to parse an object
 */
public interface ObjectParser<ParseableClass> {

    /**
     * Returns a new instance of the {@link ParseableClass} without using reflection.
     *
     * @return
     */
    public ParseableClass getInstance();

    /**
     * Will set all of the fields for the {@link ParseableClass} that define a {@link com.raizlabs.android.parser.core.Key} attribute.
     *
     * @param parsible     The instance that we are parsing
     * @param dataInstance The singular data type that we're parsing. E.g. {@link org.json.JSONObject}
     * @param parser       The parser we're using to parse the dataInstance into the parsible
     */
    public void parse(ParseableClass parsible, Object dataInstance, Parser parser);


}
