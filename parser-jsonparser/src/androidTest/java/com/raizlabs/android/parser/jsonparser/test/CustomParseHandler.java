package com.raizlabs.android.parser.jsonparser.test;

import com.raizlabs.android.parser.ParseHandler;
import com.raizlabs.android.parser.Parser;

/**
 * Description: Tests a custom parse handler
 */
public class CustomParseHandler implements ParseHandler<DifferentParseHandlerClass> {

    @Override
    public DifferentParseHandlerClass getInstance() {
        return new DifferentParseHandlerClass();
    }

    @Override
    public void parse(DifferentParseHandlerClass parseable, Object dataInstance, Parser parser) {

    }
}
