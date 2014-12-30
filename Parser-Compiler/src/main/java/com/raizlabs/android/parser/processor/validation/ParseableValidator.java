package com.raizlabs.android.parser.processor.validation;

import com.raizlabs.android.parser.processor.ParserManager;
import com.raizlabs.android.parser.processor.definition.ParseableDefinition;

/**
 * Author: andrewgrosner
 * Contributors: { }
 * Description:
 */
public class ParseableValidator implements Validator<ParseableDefinition> {

    @Override
    public boolean validate(ParserManager manager, ParseableDefinition parseableDefinition) {
        boolean success = true;

        if(parseableDefinition.keyDefinitions.isEmpty() && !parseableDefinition.isFieldParser) {
            manager.logError("The parseable class %1s must have at least one valid field with the Key annotation " +
                    "or implement the FieldParser interface.", parseableDefinition.elementClassName);
            success = false;
        }
        return success;
    }
}
