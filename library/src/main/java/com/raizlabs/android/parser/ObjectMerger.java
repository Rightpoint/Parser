package com.raizlabs.android.parser;

import java.util.ArrayList;

/**
 * Provides a nice way to merge specific keys into an object
 * without declaring a model implementation or needing a nested object.
 */
public abstract class ObjectMerger<Result> implements ObjectMergerInterface {

    /**
     * The data as a consequence of the merge process. This can be any object.
     */
    private Result mData;

    /**
     * The keys from data that this object corresponds to.
     */
    protected ArrayList<String> mKeys;

    public ObjectMerger() {}

    /**
     * Set the data contained in the merged object. This is not recommended as you should use {@link #merge(String, Object)}
     * @param data
     */
    public void setData(Result data) {
        mData = data;
    }

    /**
     * Returns the data as a result of data merge.
     * @return
     */
    public Result getData() {
        return mData;
    }


    @Override
    public ArrayList<String> getKeys(){
        if(mKeys == null) {
            mKeys = new ArrayList<String>();
            buildKeys(mKeys);
        }
        return mKeys;
    }

    /**
     * Add the keys this object is associated with in this method.
     * @param keys
     */
    protected abstract void buildKeys(ArrayList<String> keys);

}
