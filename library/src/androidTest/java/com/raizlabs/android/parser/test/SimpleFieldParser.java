package com.raizlabs.android.parser.test;

import com.raizlabs.android.parser.FieldParsible;
import com.raizlabs.android.parser.Parser;

/**
 * Author: andrewgrosner
 * Contributors: { }
 * Description:
 */
public class SimpleFieldParser implements FieldParsible<ComplexClass> {

    String hidden;

    @Override
    public void parse(ComplexClass parseable, Object instance, Parser parser) {
        hidden = (String) parser.getValue(instance, "Hidden Here");
    }
}
