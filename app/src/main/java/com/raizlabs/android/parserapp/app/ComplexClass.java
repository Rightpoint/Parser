package com.raizlabs.android.parserapp.app;

import com.raizlabs.android.parser.FieldParsible;
import com.raizlabs.android.parser.Parser;
import com.raizlabs.android.parser.core.FieldParseable;
import com.raizlabs.android.parser.core.Key;
import com.raizlabs.android.parser.core.Parseable;

import java.util.List;
import java.util.Map;

/**
 * Author: andrewgrosner
 * Contributors: { }
 * Description:
 */
@Parseable
public class ComplexClass implements FieldParsible {

    @Key
    String name;

    @Key
    long date;

    @Key
    double math;

    @Key
    int number;

    @Key
    boolean truth;

    @Key
    @FieldParseable
    SimpleClass otherClass;

    @Key
    SimpleClass[] simpleClasses;

    @Key
    List<SimpleClass> simpleClassList;

    @Key
    Map<String, SimpleClass> simpleClassMap;

    @Override
    public void parse(Object dataInstance, Parser parser) {

    }
}
