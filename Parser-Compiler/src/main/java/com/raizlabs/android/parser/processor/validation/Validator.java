package com.raizlabs.android.parser.processor.validation;

import com.raizlabs.android.parser.processor.ParserManager;

/**
 * Author: andrewgrosner
 * Contributors: { }
 * Description:
 */
public interface Validator<ValidationDefinition> {

    public boolean validate(ParserManager parserManager, ValidationDefinition validationDefinition);
}
