package com.raizlabs.android.parser.processor;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

/**
 * Author: andrewgrosner
 * Contributors: { }
 * Description:
 */
public class ProcessorUtils {
    public static boolean implementsClass(ParserManager manager, String fqTn, TypeElement element) {
        TypeElement typeElement = manager.getElements().getTypeElement(fqTn);
        if (typeElement == null) {
            manager.logError("Type Element was null for: " + fqTn + "" +
                    "ensure that the visibility of the class is not private.");
            return false;
        } else {
            TypeMirror classMirror = typeElement.asType();
            return manager.getTypes().isAssignable(element.asType(), classMirror);
        }
    }
}
