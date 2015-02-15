package com.raizlabs.android.parser.jsonparser.test;

import com.raizlabs.android.parser.core.Key;
import com.raizlabs.android.parser.core.Mergeable;
import com.raizlabs.android.parser.core.NotMergeable;
import com.raizlabs.android.parser.core.Parseable;

/**
 * Description:
 */
@Parseable
@Mergeable
public class MergeableClass {

    @Key
    String name;

    @Key
    boolean isSet;

    @NotMergeable
    @Key
    boolean notMergeable;

}
