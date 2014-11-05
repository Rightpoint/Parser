package com.raizlabs.android.parser;

/**
 * Author: andrewgrosner
 * Contributors: { }
 * Description: Implement this interface to mark your {@link com.raizlabs.android.parser.core.Parseable} or {@link com.raizlabs.android.parser.core.Key}
 * for manual parsing of the object. This is useful when you want to separately parse out data from an object in a custom way.
 */
public interface FieldParsible {

    /**
     * Called when the top-level object is parsed. The data passed in should not be accessed directly.
     * Instead use {@link com.raizlabs.android.parser.Parser#getValue(Object, String)} on the object to get the data. If you know the
     * data will be of another instance of the dataInstance, use the {@link com.raizlabs.android.parser.ParserHolder}'s methods for specific types.
     *
     * @param dataInstance The data that is getting parsed.
     * @param parser       The {@link com.raizlabs.android.parser.Parser} that is used to interface with the data.
     */
    public void parse(Object dataInstance, Parser parser);
}
