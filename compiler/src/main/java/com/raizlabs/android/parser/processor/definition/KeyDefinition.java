package com.raizlabs.android.parser.processor.definition;

import com.raizlabs.android.parser.core.FieldParseable;
import com.raizlabs.android.parser.core.Key;
import com.raizlabs.android.parser.core.Parseable;
import com.raizlabs.android.parser.processor.ParserManager;
import com.raizlabs.android.parser.processor.ProcessorUtils;
import com.raizlabs.android.parser.processor.writer.Writer;
import com.squareup.javawriter.JavaWriter;

import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeMirror;
import java.io.IOException;
import java.util.List;

/**
 * Author: andrewgrosner
 * Contributors: { }
 * Description:
 */
public class KeyDefinition implements Writer {

    private enum Type {
        ARRAY {
            @Override
            public String getParseMethod() {
                return "parseArray";
            }

            @Override
            public boolean needsComponent() {
                return true;
            }
        },
        NORMAL {
            @Override
            public String getParseMethod() {
                return "parse";
            }

            @Override
            public boolean needsComponent() {
                return false;
            }
        },
        LIST {
            @Override
            public String getParseMethod() {
                return "parseList";
            }

            @Override
            public boolean needsComponent() {
                return true;
            }
        },
        MAP {
            @Override
            public String getParseMethod() {
                return "parseMap";
            }

            @Override
            public boolean needsComponent() {
                return true;
            }
        };

        public abstract String getParseMethod();

        public abstract boolean needsComponent();
    }


    public VariableElement element;

    public String keyName;

    public String variableName;

    public String variableType;

    public String componentType;

    public String secondaryComponentType;

    TypeElement variableTypeElement;

    TypeElement componentTypeElement;

    TypeElement secondComponentTypeElement;

    boolean hasParser = false;

    Type type;

    boolean isPrimitive = false;

    boolean isFieldParser = false;

    boolean shouldCreateFieldParser = false;

    public KeyDefinition(ParserManager manager, VariableElement element) {
        this.element = element;

        Key key = element.getAnnotation(Key.class);
        keyName = key.name();
        variableName = element.getSimpleName().toString();
        if (keyName == null || keyName.isEmpty()) {
            keyName = variableName;
        }

        TypeMirror typeMirror = element.asType();

        if (typeMirror.getKind().isPrimitive()) {
            variableTypeElement = manager.getTypes().boxedClass((PrimitiveType) element.asType());
            type = Type.NORMAL;
            isPrimitive = true;
        } else {
            if (typeMirror instanceof ArrayType) {
                ArrayType arrayType = (ArrayType) typeMirror;
                variableTypeElement = manager.getElements().getTypeElement(arrayType.getComponentType().toString());
                componentTypeElement = variableTypeElement;
                componentType = variableTypeElement.asType().toString();
                type = Type.ARRAY;
            } else {
                variableTypeElement = manager.getElements().getTypeElement(manager.getTypes().erasure(element.asType()).toString());

                if (ProcessorUtils.implementsClass(manager, "java.util.List", variableTypeElement) && typeMirror instanceof DeclaredType) {
                    if (((DeclaredType) typeMirror).getTypeArguments().size() > 0) {
                        componentTypeElement = manager.getElements().getTypeElement(((DeclaredType) typeMirror).getTypeArguments().get(0).toString());
                    } else {
                        componentTypeElement = manager.getElements().getTypeElement("java.lang.Object");
                    }
                    componentType = componentTypeElement.toString();
                    type = Type.LIST;
                } else if (ProcessorUtils.implementsClass(manager, "java.util.Map", variableTypeElement) && typeMirror instanceof DeclaredType) {
                    if (((DeclaredType) typeMirror).getTypeArguments().size() > 0) {
                        List<? extends TypeMirror> typeMirrors = ((DeclaredType) typeMirror).getTypeArguments();
                        componentTypeElement = manager.getElements().getTypeElement(typeMirrors.get(0).toString());
                        secondComponentTypeElement = manager.getElements().getTypeElement(typeMirrors.get(1).toString());

                        componentType = componentTypeElement.asType().toString();
                        secondaryComponentType = secondComponentTypeElement.asType().toString();
                    }
                    type = Type.MAP;
                } else {
                    type = Type.NORMAL;
                }
            }
        }

        if (type.equals(Type.NORMAL)) {
            FieldParseable fieldParseable = element.getAnnotation(FieldParseable.class);
            isFieldParser = ProcessorUtils.implementsClass(manager, Classes.FIELD_PARSIBLE, variableTypeElement) && fieldParseable != null;
            if(fieldParseable != null) {
                shouldCreateFieldParser = fieldParseable.shouldCreateClass();
            }
        }

        variableType = element.asType().toString();
        if (type.equals(Type.MAP)) {
            hasParser = true;
        } else {
            hasParser = type.needsComponent() ? componentTypeElement.getAnnotation(Parseable.class) != null : variableTypeElement.getAnnotation(Parseable.class) != null;
        }
    }

    @Override
    public void write(JavaWriter javaWriter) throws IOException {

        if(!shouldCreateFieldParser) {
            String getValue = String.format("parse.getValue(instance,\"%1s\")", keyName);
            if (!hasParser) {
                javaWriter.emitStatement("parseable.%1s = ((%1s) " + getValue + ")", variableName, variableType);
            } else {
                String parseStatment = "parseable.%1s = ((%1s) %1s.%1s(";
                if (!type.equals(Type.MAP)) {
                    javaWriter.emitStatement(parseStatment + "%1s.class, " + getValue + "))", variableName, variableType,
                            Classes.PARSER_HOLDER, type.getParseMethod(), type.needsComponent() ? componentType : variableType);
                } else {
                    javaWriter.emitStatement(parseStatment + "%1s.class, %1s.class, " + getValue + "))", variableName, variableType,
                            Classes.PARSER_HOLDER, type.getParseMethod(), componentType, secondaryComponentType);
                }
            }
        } else {
            javaWriter.emitStatement("parseable.%1s = new %1s()", variableName, variableType);
        }

        if(isFieldParser) {
            javaWriter.emitStatement("((%1s)parseable.%1s).parse(%1s, %1s)", Classes.FIELD_PARSIBLE, variableName, "instance", "parse");
        }
    }
}
