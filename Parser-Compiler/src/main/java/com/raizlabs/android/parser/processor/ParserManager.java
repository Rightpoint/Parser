package com.raizlabs.android.parser.processor;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.raizlabs.android.parser.processor.definition.Classes;
import com.raizlabs.android.parser.processor.definition.Definition;
import com.raizlabs.android.parser.processor.definition.ParseInterfaceDefinition;
import com.raizlabs.android.parser.processor.definition.ParseableDefinition;
import com.raizlabs.android.parser.processor.handler.Handler;
import com.raizlabs.android.parser.processor.writer.Writer;
import com.squareup.javawriter.JavaWriter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

/**
 * Description:
 */
public class ParserManager implements Handler, Writer, Definition {

    private List<Handler> mAnnotationHandlers = Lists.newArrayList();

    private Map<String, ParseInterfaceDefinition> parseInterfaces = Maps.newHashMap();

    private Map<String, ParseableDefinition> parseableDefinitionMap = Maps.newHashMap();

    private final Elements elements;

    private final Types types;

    private final Filer filer;

    private final Messager messager;

    public ParserManager(ProcessingEnvironment processingEnvironment) {
        elements = processingEnvironment.getElementUtils();
        types = processingEnvironment.getTypeUtils();
        filer = processingEnvironment.getFiler();
        messager = processingEnvironment.getMessager();
    }


    public void addAnnotationHandler(Handler handler) {
        mAnnotationHandlers.add(handler);
    }

    @Override
    public void handle(ParserManager manager, RoundEnvironment roundEnvironment) {
        for (Handler handler : mAnnotationHandlers) {
            handler.handle(manager, roundEnvironment);
        }

        if (roundEnvironment.processingOver()) {
            try {
                JavaWriter javaWriter = new JavaWriter(manager.getFiler().createSourceFile(getSourceFileName()).openWriter());
                write(javaWriter);
                javaWriter.close();
            } catch (IOException e) {
            }
        }
    }

    public void addParseInterfaceDefinition(Element element, ParseInterfaceDefinition parseInterfaceDefinition) {
        parseInterfaces.put(element.getSimpleName().toString(), parseInterfaceDefinition);
    }

    public void addParseableDefinition(Element element, ParseableDefinition parseableDefinition) {
        parseableDefinitionMap.put(element.getSimpleName().toString(), parseableDefinition);
    }

    public ParseInterfaceDefinition getParseInterfaceDefinition(String name) {
        return parseInterfaces.get(name);
    }

    public Elements getElements() {
        return elements;
    }

    public Types getTypes() {
        return types;
    }

    public Filer getFiler() {
        return filer;
    }

    public void logError(String error, Object... args) {
        messager.printMessage(Diagnostic.Kind.ERROR, String.format(error, args));
    }

    @Override
    public void write(JavaWriter javaWriter) throws IOException {
        javaWriter.emitPackage(Classes.PARSER_PACKAGE);


        javaWriter.beginType("ParserManager", "class", Sets.newHashSet(Modifier.FINAL), null, Classes.PARSER_MANAGER_INTERFACE);

        javaWriter.beginConstructor(Sets.newHashSet(Modifier.PUBLIC));

        for (ParseableDefinition parseableDefinition : parseableDefinitionMap.values()) {
            javaWriter.emitStatement("ParserHolder.addParseable(%1s.class, new %1s())",
                    parseableDefinition.getFQElementClassName(), parseableDefinition.getSourceFileName());
        }

        for (ParseInterfaceDefinition parseInterfaceDefinition : parseInterfaces.values()) {
            javaWriter.beginInitializer(false);
            javaWriter.emitStatement("%1s parser = new %1s()", parseInterfaceDefinition.getFQElementClassName(),
                    parseInterfaceDefinition.getFQElementClassName());
            javaWriter.emitStatement("ParserHolder.addParseInterface(%1s.class, parser)",
                    parseInterfaceDefinition.objectType);
            javaWriter.emitStatement("ParserHolder.addParseInterface(%1s.class, parser)",
                    parseInterfaceDefinition.listObjectType);
            javaWriter.endInitializer();
        }

        javaWriter.endConstructor();

        javaWriter.endType();
    }

    @Override
    public String getSourceFileName() {
        return Classes.PARSER_PACKAGE + ".ParserManager";
    }
}
