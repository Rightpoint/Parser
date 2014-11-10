package com.raizlabs.android.parser.processor.handler;

import com.raizlabs.android.parser.processor.ParserManager;
import com.raizlabs.android.parser.processor.definition.BaseDefinition;
import com.raizlabs.android.parser.processor.validation.Validator;
import com.squareup.javawriter.JavaWriter;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * Author: andrewgrosner
 * Contributors: { }
 * Description:
 */
public abstract class BaseHandler implements Handler {

    private Class<? extends Annotation> mAnnotationClass;

    public BaseHandler(Class<? extends Annotation> annotationClass) {
        mAnnotationClass = annotationClass;
    }

    @Override
    public void handle(ParserManager manager, RoundEnvironment roundEnvironment) {
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(mAnnotationClass);
        if(elements.size() > 0) {
            for(Element element: elements) {
                onProcessElement(manager, element);
            }
        }
    }


    protected void onProcessElement(ParserManager parserManager, Element element) {
        BaseDefinition definition = createDefinition((TypeElement) element, parserManager);
        Validator validator = getValidator();
        if(validator.validate(parserManager, definition)) {
            try {
                JavaWriter javaWriter = new JavaWriter(parserManager.getFiler().createSourceFile(definition.getSourceFileName()).openWriter());
                definition.write(javaWriter);
                javaWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    protected abstract BaseDefinition createDefinition(TypeElement typeElement, ParserManager manager);

    protected abstract Validator getValidator();
}
