package com.raizlabs.android.parser.processor;

import com.google.auto.service.AutoService;
import com.raizlabs.android.parser.core.Key;
import com.raizlabs.android.parser.core.ObjectMerger;
import com.raizlabs.android.parser.core.ParseInterface;
import com.raizlabs.android.parser.processor.handler.ParseInterfaceHandler;
import com.raizlabs.android.parser.processor.handler.ParseableHandler;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Author: andrewgrosner
 * Contributors: { }
 * Description:
 */
@AutoService(Processor.class)
public class ParserProcessor extends AbstractProcessor {

    private ParserManager manager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        manager = new ParserManager(processingEnv);
        manager.addAnnotationHandler(new ParseInterfaceHandler());
        manager.addAnnotationHandler(new ParseableHandler());
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> classes = new LinkedHashSet<>();
        classes.add(Key.class.getName());
        classes.add(ObjectMerger.class.getName());
        classes.add(ParseInterface.class.getName());
        return classes;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        manager.handle(manager, roundEnv);
        return true;
    }
}
