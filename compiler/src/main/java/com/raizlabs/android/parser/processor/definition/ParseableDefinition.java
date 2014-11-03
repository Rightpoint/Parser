package com.raizlabs.android.parser.processor.definition;

import com.raizlabs.android.parser.core.Key;
import com.raizlabs.android.parser.processor.ParserManager;
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


    private ArrayList<KeyDefinition> keyDefinitions = new ArrayList<>();

    public ParseableDefinition(TypeElement typeElement, ParserManager manager) {
        super(typeElement, manager);
        setDefinitionClassName(PARSEABLE_CLASS_SUFFIX);

        List<? extends Element> elements = typeElement.getEnclosedElements();
        boolean isFirst = true;
        for(Element enclosedElement: elements) {
            if(enclosedElement.getAnnotation(Key.class) != null) {
                keyDefinitions.add(new KeyDefinition(isFirst, manager, (VariableElement) enclosedElement));

                if(isFirst) {
                    isFirst = false;
                }
            }
        }

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
        javaWriter.beginMethod("void", "setValue", METHOD_MODIFIERS, elementClassName, "parseable", "String", "key", "Object" , "value");
        for(KeyDefinition keyDefinition: keyDefinitions) {
            keyDefinition.write(javaWriter);
        }
        javaWriter.endControlFlow();
        javaWriter.endMethod();
    }

    @Override
    protected String[] getImplementsClasses() {
        String[] implement = new String[1];
        implement[0] = Classes.OBJECT_PARSER + "<" + elementClassName + ">";
        return implement;
    }
}
