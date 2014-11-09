package com.raizlabs.android.parser.processor.handler;

import com.raizlabs.android.parser.processor.ParserManager;

import javax.annotation.processing.RoundEnvironment;

/**
 * Author: andrewgrosner
 * Contributors: { }
 * Description:
 */
public interface Handler {

    public void handle(ParserManager manager, RoundEnvironment roundEnvironment);
}
