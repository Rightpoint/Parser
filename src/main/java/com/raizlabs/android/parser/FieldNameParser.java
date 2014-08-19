package com.raizlabs.android.parser;

/**
 * Description: This provides a handy way to parse field names into JSON data keys
 * without having to specify keys for all the data.
 */
public interface FieldNameParser {

    /**
     * Returns the key that we use to find the correct data.
     * @param inKey - the name of key
     * @return the name of the data key
     */
    public String getKey(String inKey);
}
