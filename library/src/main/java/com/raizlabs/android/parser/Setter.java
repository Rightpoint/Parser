package com.raizlabs.android.parser;

import java.lang.reflect.Field;

/**
 * Description:
 */
public class Setter {

    final protected Field mField;
    final protected Object mInstance;

    public Setter(Field field, Object pInstance) {
        mField = field;
        mInstance = pInstance;
    }

    public void set(Object inData) {
        try {
            mField.setAccessible(true);
            mField.set(mInstance, inData);
        } catch (IllegalAccessException e) {

        }
    }
}
