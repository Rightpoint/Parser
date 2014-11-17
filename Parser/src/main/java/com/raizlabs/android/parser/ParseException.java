package com.raizlabs.android.parser;

/**
 * Author: andrewgrosner
 * Description: Will be thrown whenever data fails to parse for some reason. Throw this
 * in your {@link com.raizlabs.android.parser.Parser} definition when a required field is missing.
 */
public class ParseException extends RuntimeException {

    public ParseException() {
    }

    public ParseException(String detailMessage) {
        super(detailMessage);
    }

    public ParseException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public ParseException(Throwable throwable) {
        super(throwable);
    }
}
