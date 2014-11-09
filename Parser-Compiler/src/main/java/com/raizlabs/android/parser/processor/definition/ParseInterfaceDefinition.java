package com.raizlabs.android.parser.processor.definition;

import com.raizlabs.android.parser.processor.ParserManager;
import com.squareup.javawriter.JavaWriter;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import java.util.List;

/**
 * Author: andrewgrosner
 * Contributors: { }
 * Description:
 */
public class ParseInterfaceDefinition extends BaseDefinition {

    public static final String INTERFACE_CLASS_SUFFIX = "$ParseInterface";

    public String objectType;

    public String listObjectType;

    public ParseInterfaceDefinition(TypeElement element, ParserManager manager) {
        super(element, manager);
        setDefinitionClassName(INTERFACE_CLASS_SUFFIX);

        DeclaredType typeAdapterInterface = null;
        final DeclaredType typeAdapterType = manager.getTypes().getDeclaredType(
                manager.getElements().getTypeElement(Classes.PARSE_INTERFACE),
                manager.getTypes().getWildcardType(null, null),
                manager.getTypes().getWildcardType(null, null)
        );


        for (TypeMirror superType : manager.getTypes().directSupertypes(element.asType())) {
            if (manager.getTypes().isAssignable(superType, typeAdapterType)) {
                typeAdapterInterface = (DeclaredType) superType;
                break;
            }
        }

        if (typeAdapterInterface != null) {
            final List<? extends TypeMirror> typeArguments = typeAdapterInterface.getTypeArguments();
            objectType = manager.getElements().getTypeElement(typeArguments.get(0).toString()).toString();
            listObjectType = manager.getElements().getTypeElement(typeArguments.get(1).toString()).toString();
        }


        manager.addParseInterfaceDefinition(element, this);
    }

    @Override
    public void onWriteDefinition(JavaWriter javaWriter) {

    }



}