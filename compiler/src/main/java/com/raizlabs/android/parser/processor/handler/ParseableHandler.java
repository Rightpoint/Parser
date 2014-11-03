package com.raizlabs.android.parser.processor.handler;

import com.raizlabs.android.parser.core.Parseable;
import com.raizlabs.android.parser.processor.ParserManager;
import com.raizlabs.android.parser.processor.definition.BaseDefinition;
import com.raizlabs.android.parser.processor.definition.ParseableDefinition;
import com.squareup.javawriter.JavaWriter;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.io.IOException;

/**
 * Author: andrewgrosner
 * Contributors: { }
 * Description:
 */
public class ParseableHandler extends BaseHandler {


    public ParseableHandler() {
        super(Parseable.class);
    }

    @Override
    protected BaseDefinition createDefinition(TypeElement typeElement, ParserManager manager) {
        return new ParseableDefinition(typeElement, manager);
    }
}
