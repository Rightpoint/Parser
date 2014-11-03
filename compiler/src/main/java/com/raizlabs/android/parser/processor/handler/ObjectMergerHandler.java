package com.raizlabs.android.parser.processor.handler;

import com.raizlabs.android.parser.core.ObjectMerger;
import com.raizlabs.android.parser.processor.ParserManager;
import com.raizlabs.android.parser.processor.definition.BaseDefinition;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

/**
 * Author: andrewgrosner
 * Contributors: { }
 * Description:
 */
public class ObjectMergerHandler extends BaseHandler {

    public ObjectMergerHandler() {
        super(ObjectMerger.class);
    }


    @Override
    protected BaseDefinition createDefinition(TypeElement typeElement, ParserManager manager) {
        return null;
    }
}
