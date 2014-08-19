package com.raizlabs.android.parser;

/**
 * Description: This provides a handy way to parse field names into JSON data keys
 * without having to specify keys for all the data.
 */
public interface FieldNameParser {

    /**
     * Returns the key that we use to find the JSON data.
     * @param fieldName - the name of field
     * @return the name of the JSON key
     */
    public String getJsonKey(String fieldName);
}
