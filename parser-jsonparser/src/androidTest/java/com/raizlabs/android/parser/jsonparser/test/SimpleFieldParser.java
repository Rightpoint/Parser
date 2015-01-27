package com.raizlabs.android.parser.jsonparser.test;

import com.raizlabs.android.parser.ParseListener;
import com.raizlabs.android.parser.Parser;

/**
 * Author: andrewgrosner
 * Description:
 */
public class SimpleFieldParser implements ParseListener {

    String hidden;

    @Override
    public void parse(Object instance, Parser parser) {
        hidden = (String) parser.getValue(instance, "hidden", "", false);
    }
}
