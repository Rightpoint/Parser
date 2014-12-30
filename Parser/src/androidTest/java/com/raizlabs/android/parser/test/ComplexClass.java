package com.raizlabs.android.parser.test;

import com.raizlabs.android.parser.ParseListener;
import com.raizlabs.android.parser.Parser;
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
public class ComplexClass implements ParseListener {

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
    SimpleClass otherClass;

    @Key
    SimpleClass[] simpleClasses;

    @Key
    List<SimpleClass> simpleClassList;

    @Key
    Map<String, SimpleClass> simpleClassMap;

    String hidden;

    @Key
    List<String> stringList;

    @Key
    String[] stringArray;

    @Key
    @com.raizlabs.android.parser.core.FieldParseable(shouldCreateClass = true)
    SimpleFieldParser simpleFieldParser;

    @Override
    public void parse(Object dataInstance, Parser parser) {
        hidden = (String) parser.getValue(dataInstance, "hidden", "", false);
    }
}
