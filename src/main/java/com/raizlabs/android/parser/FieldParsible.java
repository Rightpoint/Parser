package com.raizlabs.android.parser;

/**
 * This enables certain data objects to listen for when the parsing occurs and provide some kind of custom
 * handling of certain fields.
 */
public interface FieldParsible<T> {
    /**
     * Allows a class getting parsed to provide a custom parse to the type of data coming in.
     * @param inFieldName - name of the field
     * @param inSetter - set the field value here
     * @param inData - the data coming from parse
     * @return true if we handled the field manually, false to parse it automatically
     */
    boolean parseField(String inFieldName, Setter inSetter, T inData);
}
