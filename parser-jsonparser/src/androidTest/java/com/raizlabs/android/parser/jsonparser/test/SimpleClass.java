package com.raizlabs.android.parser.jsonparser.test;

import com.raizlabs.android.parser.ParseListener;
import com.raizlabs.android.parser.Parser;
import com.raizlabs.android.parser.core.Key;
import com.raizlabs.android.parser.core.Parseable;

/**
 * Author: andrewgrosner
 * Contributors: { }
 * Description:
 */
@Parseable
public class SimpleClass implements ParseListener {

    @Key
    String name;

    @Override
    public void parse(Object dataInstance, Parser parser) {
        name = (String) parser.getValue(dataInstance, "name", "", false);
    }
}
