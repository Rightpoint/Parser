package com.raizlabs.android.parser;

/**
 * Author: andrewgrosner
 * Contributors: { }
 * Description: Internal usage that will generate how to parse an object
 */
public interface ObjectParser<ParseableClass>  {

    public ParseableClass getInstance();

    public void parse(ParseableClass parsible, Object instance, Parser parser);


}
