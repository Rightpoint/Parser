package com.raizlabs.android.parser.processor.validation;

import com.raizlabs.android.parser.processor.ParserManager;
import com.raizlabs.android.parser.processor.definition.ParseInterfaceDefinition;

/**
 * Author: andrewgrosner
 * Contributors: { }
 * Description:
 */
public class ParseInterfaceValidator implements Validator<ParseInterfaceDefinition> {
    @Override
    public boolean validate(ParserManager parserManager, ParseInterfaceDefinition parseInterfaceDefinition) {
        if(parseInterfaceDefinition.listObjectType == null || parseInterfaceDefinition.objectType == null) {
            parserManager.logError("There was a problem with the specified type arguments for %1s ", parseInterfaceDefinition.definitionClassName);
            return false;
        }
        return true;
    }
}
