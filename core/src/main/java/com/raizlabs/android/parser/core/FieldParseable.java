package com.raizlabs.android.parser.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author: andrewgrosner
 * Description: Marks a field as subscribing to the parse events of a parent {@link com.raizlabs.android.parser.core.Parseable}.
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.FIELD)
public @interface FieldParseable {

    /**
     * This will instantiate the field, rather than retrieve it from the data.
     * @return true if the class definition should create it, false if it will be parsed from the data.
     */
    boolean shouldCreateClass() default false;
}
