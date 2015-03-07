package com.raizlabs.android.parser.processor;

import com.raizlabs.android.parser.core.Parseable;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
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

    public static String getClassFromAnnotation(Parseable annotation) {
        String clazz = null;
        if (annotation != null) {
            try {
                annotation.parseHandler();
            } catch (MirroredTypeException mte) {
                clazz = mte.getTypeMirror().toString();
            }
        }
        return clazz;
    }
}
