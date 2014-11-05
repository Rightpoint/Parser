package com.raizlabs.android.parser.processor.definition;

import com.raizlabs.android.parser.core.Key;
import com.raizlabs.android.parser.processor.ParserManager;
import com.raizlabs.android.parser.processor.ProcessorUtils;
import com.raizlabs.android.parser.processor.writer.Writer;
import com.squareup.javawriter.JavaWriter;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: andrewgrosner
 * Contributors: { }
 * Description:
 */
public class ParseableDefinition extends BaseDefinition implements Writer {

    public static final String PARSEABLE_CLASS_SUFFIX = "$ParseDefinition";


    private boolean isFieldParser = false;

    private ArrayList<KeyDefinition> keyDefinitions = new ArrayList<>();

    public ParseableDefinition(TypeElement typeElement, ParserManager manager) {
        super(typeElement, manager);
        setDefinitionClassName(PARSEABLE_CLASS_SUFFIX);

        List<? extends Element> elements = typeElement.getEnclosedElements();
        for(Element enclosedElement: elements) {
            if(enclosedElement.getAnnotation(Key.class) != null) {
                keyDefinitions.add(new KeyDefinition(manager, (VariableElement) enclosedElement));
            }
        }

        isFieldParser = ProcessorUtils.implementsClass(manager, Classes.FIELD_PARSIBLE, typeElement);
        manager.addParseableDefinition(typeElement, this);

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
        for(KeyDefinition keyDefinition: keyDefinitions) {
            keyDefinition.write(javaWriter);
        }

        if(isFieldParser) {
            javaWriter.emitStatement("((%1s)parseable).parse(%1s, %1s)", Classes.FIELD_PARSIBLE, "instance", "parse");
        }

        javaWriter.endMethod();
    }

    @Override
    protected String[] getImplementsClasses() {
        String[] implement = new String[1];
        implement[0] = Classes.OBJECT_PARSER + "<" + elementClassName + ">";
        return implement;
    }
}
