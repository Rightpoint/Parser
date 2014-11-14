package com.raizlabs.android.parser.processor.validation;

import com.google.common.collect.Lists;
import com.raizlabs.android.parser.processor.ParserManager;
import com.raizlabs.android.parser.processor.definition.KeyDefinition;

import java.util.List;

/**
 * Author: andrewgrosner
 * Contributors: { }
 * Description: Runs a validation on a {@link com.raizlabs.android.parser.processor.definition.KeyDefinition} to ensure it
 * is constructed properly.
 */
public class KeyValidator implements Validator<KeyDefinition> {

    private ParserManager manager;

    private List<String> mKeyList = Lists.newArrayList();

    public KeyValidator(ParserManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean validate(ParserManager parserManager, KeyDefinition keyDefinition) {
        if(isNull(keyDefinition.keyName, keyDefinition.variableTypeElement)) {
            manager.logError("Key was found null for %1s or variable type %1s is not currently supported.", keyDefinition.keyName, keyDefinition.element.asType().toString());
            return false;
        }
        boolean success = false;
        if(mKeyList.contains(keyDefinition.keyName)) {
            success = false;
            manager.logError("Duplicate fields from %1s request the same key %1s", keyDefinition.variableName, keyDefinition.keyName);
        } else  {
            mKeyList.add(keyDefinition.keyName);
            if(keyDefinition.type.equals(KeyDefinition.Type.NORMAL)) {
                success = validateNormalType(keyDefinition);
            } else if(keyDefinition.type.equals(KeyDefinition.Type.ARRAY)) {
                success = validateArrayType(keyDefinition);
            } else if(keyDefinition.type.equals(KeyDefinition.Type.LIST)) {
                success = validateListType(keyDefinition);
            } else if(keyDefinition.type.equals(KeyDefinition.Type.MAP)) {
                success = validateMapType(keyDefinition);
            }
        }

        return success;
    }

    /**
     * @param objects
     * @return true if any of the objects are null
     */
    private static boolean isNull(Object...objects) {
        for(Object ob: objects) {
            if(ob == null) {
                return true;
            }
        }
        return false;
    }

    private boolean validateNormalType(KeyDefinition keyDefinition) {
        return true;
    }

    private boolean validateMapType(KeyDefinition keyDefinition) {
        if(isNull(keyDefinition.componentTypeElement, keyDefinition.secondComponentTypeElement)) {
            manager.logError("Map type for %1s should define type parameters.", keyDefinition.variableName);
            return false;
        } else {
            return true;
        }
    }

    private boolean validateListType(KeyDefinition keyDefinition) {
        if(isNull(keyDefinition.componentTypeElement)) {
            manager.logError("List type for %1s has a non-supported type element", keyDefinition.variableName);
            return false;
        } else {
            return true;
        }
    }

    private boolean validateArrayType(KeyDefinition keyDefinition) {
        if(isNull(keyDefinition.variableTypeElement)) {
            manager.logError("Array type for %1s has a non-supported component type", keyDefinition.variableName);
            return false;
        } else {
            return true;
        }
    }


}
