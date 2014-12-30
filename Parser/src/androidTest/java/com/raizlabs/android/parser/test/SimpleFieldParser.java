package com.raizlabs.android.parser.test;

import com.raizlabs.android.parser.FieldParseable;
import com.raizlabs.android.parser.Parser;

/**
 * Author: andrewgrosner
 * Contributors: { }
 * Description:
 */
public class SimpleFieldParser implements FieldParseable {

    String hidden;

    @Override
    public void parse(Object instance, Parser parser) {
        hidden = (String) parser.getValue(instance, "hidden", "", false);
    }
}
