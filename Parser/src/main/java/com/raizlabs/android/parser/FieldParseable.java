package com.raizlabs.android.parser;

/**
 * Author: andrewgrosner
 * Description: Class has been deprecated. Use {@link com.raizlabs.android.parser.ParseListener} instead.
 */
@Deprecated
public interface FieldParseable {

    public void parse(Object dataInstance, Parser parser);
}
