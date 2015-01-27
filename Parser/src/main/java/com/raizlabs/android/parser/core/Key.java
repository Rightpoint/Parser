package com.raizlabs.android.parser.core;

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
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.FIELD)
public @interface Key {
    String name() default "";

    /**
     * The default value of the object. This is a literal string that will be placed into the class so
     * it can be anything. If using an object, make sure to use its fully qualified class name.
     *
     * <p></p>
     * For any non primitive type it will set to null, while primitives will be corresponding "0" for int,
     * "false" for boolean, etc.
     * @return The default value in string format to be pasted in the Parse definition
     */
    String defValue() default "";

    /**
     * Specifies the field is required and thus a ParseException will thrown.
     * @return true if the field is required and parsing should fail.
     */
    boolean required() default false;
}

