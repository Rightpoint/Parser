package com.raizlabs.android.parser.processor.definition;

import com.raizlabs.android.parser.ParseHandler;
import com.raizlabs.android.parser.core.Key;
import com.raizlabs.android.parser.core.Mergeable;
import com.raizlabs.android.parser.core.Parseable;
import com.raizlabs.android.parser.processor.ParserManager;
import com.raizlabs.android.parser.processor.ProcessorUtils;
import com.raizlabs.android.parser.processor.validation.KeyValidator;
import com.squareup.javawriter.JavaWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

/**
 * Author: andrewgrosner
 * Description:
 */
public class ParseableDefinition extends BaseDefinition {

    public static final String PARSEABLE_CLASS_SUFFIX = "$ParseDefinition";


    public boolean isFieldParser = false;

    public boolean isMergeable = false;

    public String parseHandlerClazz;

    public ArrayList<KeyDefinition> keyDefinitions = new ArrayList<>();

    public ParseableDefinition(TypeElement typeElement, ParserManager manager) {
        super(typeElement, manager);
        setDefinitionClassName(PARSEABLE_CLASS_SUFFIX);

        isMergeable = typeElement.getAnnotation(Mergeable.class) != null;

        Parseable parseable = typeElement.getAnnotation(Parseable.class);

        String clazz = ProcessorUtils.getClassFromAnnotation(parseable);
        if(!ParseHandler.class.getCanonicalName().equals(clazz)) {
            parseHandlerClazz = clazz;
        }

        List<? extends Element> elements = typeElement.getEnclosedElements();
        KeyValidator keyValidator = new KeyValidator(manager);
        for (Element enclosedElement : elements) {
            if (enclosedElement.getAnnotation(Key.class) != null) {
                KeyDefinition keyDefinition = new KeyDefinition(manager, (VariableElement) enclosedElement, isMergeable);
                if (keyValidator.validate(manager, keyDefinition)) {
                    keyDefinitions.add(keyDefinition);
                }
            }
        }

        isFieldParser = ProcessorUtils.implementsClass(manager, Classes.FIELD_PARSIBLE, typeElement);
        manager.addParseableDefinition(typeElement, this);

    }

    @Override
    public String getSourceFileName() {
        if(parseHandlerClazz != null) {
            return parseHandlerClazz;
        }
        return super.getSourceFileName();
    }

    @Override
    public void onWriteDefinition(JavaWriter javaWriter) throws IOException {

        javaWriter.emitEmptyLine();
        javaWriter.emitAnnotation(Override.class);
        javaWriter.beginMethod(elementClassName, "getInstance", METHOD_MODIFIERS);
        javaWriter.emitStatement("return new %1s()", elementClassName);
        javaWriter.endMethod();

        writeSetValue(javaWriter);

    }

    private void writeSetValue(JavaWriter javaWriter) throws IOException {
        javaWriter.emitEmptyLine();
        javaWriter.emitAnnotation(Override.class);
        javaWriter.beginMethod("void", "parse", METHOD_MODIFIERS, elementClassName, "parseable", "Object", "instance",
                Classes.PARSE_INTERFACE, "parse");
        for (KeyDefinition keyDefinition : keyDefinitions) {
            keyDefinition.write(javaWriter);
        }

        if (isFieldParser) {
            javaWriter.emitStatement("((%1s)parseable).parse(%1s, %1s)", Classes.FIELD_PARSIBLE, "instance", "parse");
        }

        javaWriter.endMethod();
    }

    @Override
    protected String[] getImplementsClasses() {
        String[] implement = new String[1];
        implement[0] = Classes.PARSE_HANDLER + "<" + elementClassName + ">";
        return implement;
    }
}
