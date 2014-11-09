package com.raizlabs.android.parser.test;

import com.raizlabs.android.parser.FieldParsible;
import com.raizlabs.android.parser.Parser;

/**
 * Author: andrewgrosner
 * Contributors: { }
 * Description:
 */
public class SimpleFieldParser implements FieldParsible {

    String hidden;

    @Override
    public void parse(Object instance, Parser parser) {
        hidden = (String) parser.getValue(instance, "hidden");
    }
}
