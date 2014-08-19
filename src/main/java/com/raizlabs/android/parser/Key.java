package com.raizlabs.android.parser;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by andrewgrosner
 * Date: 12/25/13
 * Contributors:
 * Description: Describes the key that the field object references.
 * If the key has a different class than the object it references, a handler should be defined.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Key {
    String name() default "";
}

