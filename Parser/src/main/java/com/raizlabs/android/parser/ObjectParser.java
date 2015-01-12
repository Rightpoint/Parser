package com.raizlabs.android.parser;

/**
 * Author: andrewgrosner
 * Description: Internal usage that will generate how to parse an object
 */
public interface ObjectParser<ParseableClass> {

    /**
     * @return a new instance of the {@link ParseableClass} without using reflection.
     */
    public ParseableClass getInstance();

    /**
     * Will set all of the fields for the {@link ParseableClass} that define a {@link com.raizlabs.android.parser.core.Key} attribute.
     *
     * @param parseable     The instance that we are parsing
     * @param dataInstance The singular data type that we're parsing. E.g. JSONObject
     * @param parser       The parser we're using to parse the dataInstance into the parsible
     */
    public void parse(ParseableClass parseable, Object dataInstance, Parser parser);


}
