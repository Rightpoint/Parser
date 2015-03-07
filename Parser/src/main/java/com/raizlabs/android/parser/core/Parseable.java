package com.raizlabs.android.parser.core;

import com.raizlabs.android.parser.ParseHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author: andrewgrosner
 * Contributors: { }
 * Description: Marks a class as being parseable
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface Parseable {

    /**
     * @return Define a custom {@link com.raizlabs.android.parser.ParseHandler} to provide a custom parse implementation to complement this parse.
     */
    Class<? extends ParseHandler> parseHandler() default ParseHandler.class;

    /**
     * @return True if we don't generate a parse definition and instead use the custom {@link com.raizlabs.android.parser.ParseHandler} .
     * If false, the normal parse definition is done first, then the handler will be called.
     */
    boolean parseHandlerOverridesGenerated() default false;
}
