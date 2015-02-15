package com.raizlabs.android.parser.jsonparser.test;

import com.raizlabs.android.parser.core.Key;
import com.raizlabs.android.parser.core.Parseable;

/**
 * Description:
 */
@Parseable(parseHandler = CustomParseHandler.class)
public class DifferentParseHandlerClass {

    @Key
    String name;
}
