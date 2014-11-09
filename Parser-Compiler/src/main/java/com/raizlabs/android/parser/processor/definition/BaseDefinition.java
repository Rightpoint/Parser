package com.raizlabs.android.parser.processor.definition;

import com.google.common.collect.Sets;
import com.raizlabs.android.parser.processor.ParserManager;
import com.raizlabs.android.parser.processor.writer.Writer;
import com.squareup.javawriter.JavaWriter;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import java.io.IOException;
import java.util.Set;

/**
 * Author: andrewgrosner
 * Contributors: { }
 * Description:
 */
public abstract class BaseDefinition implements Definition, Writer {

    public static final Set<Modifier> METHOD_MODIFIERS = Sets.newHashSet(Modifier.PUBLIC, Modifier.FINAL);

    private final ParserManager manager;

    public final String elementClassName;

    public final TypeElement element;

    public String definitionClassName;

    public final String packageName;

    public BaseDefinition(TypeElement typeElement, ParserManager manager) {
        this.manager = manager;
        this.element = typeElement;
        elementClassName = element.getSimpleName().toString();
        packageName = manager.getElements().getPackageOf(typeElement).toString();
    }

    protected void setDefinitionClassName(String definitionClassName) {
        this.definitionClassName = elementClassName + definitionClassName;
    }

    public String getFQElementClassName() {
        return packageName + "." + elementClassName;
    }

    @Override
    public String getSourceFileName() {
        return packageName + "." + definitionClassName;
    }

    public ParserManager getManager() {
        return manager;
    }

    @Override
    public void write(JavaWriter javaWriter) throws IOException {
        javaWriter.emitPackage(packageName);
        javaWriter.beginType(definitionClassName, "class" , Sets.newHashSet(Modifier.PUBLIC, Modifier.FINAL), getExtendsClass(), getImplementsClasses());
        onWriteDefinition(javaWriter);
        javaWriter.endType();
    }

    protected String getExtendsClass() {
        return null;
    }

    protected String[] getImplementsClasses() {
        return new String[0];
    }

    public abstract void onWriteDefinition(JavaWriter javaWriter) throws IOException;
}
