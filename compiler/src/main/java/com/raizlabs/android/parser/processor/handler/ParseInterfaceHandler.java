package com.raizlabs.android.parser.processor.handler;

import com.raizlabs.android.parser.core.ParseInterface;
import com.raizlabs.android.parser.processor.ParserManager;
import com.raizlabs.android.parser.processor.definition.BaseDefinition;
import com.raizlabs.android.parser.processor.definition.ParseInterfaceDefinition;

import javax.lang.model.element.TypeElement;

/**
 * Author: andrewgrosner
 * Contributors: { }
 * Description:
 */
public class ParseInterfaceHandler extends BaseHandler {

    public ParseInterfaceHandler() {
        super(ParseInterface.class);
    }

    @Override
    protected BaseDefinition createDefinition(TypeElement typeElement, ParserManager manager) {
        return new ParseInterfaceDefinition(typeElement, manager);
    }
}
