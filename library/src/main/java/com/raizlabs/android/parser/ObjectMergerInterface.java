package com.raizlabs.android.parser;

import java.util.ArrayList;

/**
 * Author: andrewgrosner
 * Contributors: { }
 * Description: The base interface for which we can allow these objects to intercept which keys they want
 * and put them into other objects.
 */
public interface ObjectMergerInterface {

    /**
     * Returns the keys that the object is associated with.
     * @return
     */
    public ArrayList<String> getKeys();

    /**
     * Handles how we want to merge data into this class.
     * @param key - the json key
     * @param data - the value of the data
     */
    public void merge(String key, Object data);
}
