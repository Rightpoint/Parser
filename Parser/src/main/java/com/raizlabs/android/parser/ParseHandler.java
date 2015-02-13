package com.raizlabs.android.parser;

/**
 * Description: Provides an abstraction from the {@link ParseableClass} and the parser itself. Declare
 * this in {@link ParseableClass}
 */
public interface ParseHandler<ParseableClass> {

    /**
     * Called when this is specified within a {@link com.raizlabs.android.parser.core.FieldParseable}
     *
     * @param parseable     The object to set fields on
     * @param objectToParse The object such as a JSON object or FastJson object, or a list type as well
     * @param parser        The parser that is currently used.
     */
    void handleParse(ParseableClass parseable, Object objectToParse, Parser parser);
}
