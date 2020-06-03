/*
 * The MIT License
 *
 * Copyright 2020 Karus Labs.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.karuslabs.scribe.standalone;

import com.karuslabs.scribe.core.Environment;
import com.karuslabs.scribe.core.Processor;
import com.karuslabs.scribe.core.parsers.PluginParser;

import java.lang.annotation.Annotation;
import java.util.stream.Stream;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.util.*;

import org.checkerframework.checker.nullness.qual.Nullable;


/**
 * A {@code Processor} that builds a plugin.yml using annotations in {@link com.karuslabs.scribe.annotations}
 * on {@link Element}s.
 */
public class StandaloneProcessor extends Processor<Element> {
    
    @Nullable RoundEnvironment round;
    
    
    /**
     * Creates a {@code StandaloneProcessor} with the given parameters.
     * 
     * @param environment the environment
     * @param elements the elements
     * @param types the types.
     */
    public StandaloneProcessor(Environment<Element> environment, Elements elements, Types types) {
        super(environment, PluginParser.element(environment, elements, types));
    }
    
    
    /**
     * Initializes this {@code StandaloneProcessor} with the given round.
     * 
     * @param round the round
     */
    public void initialize(RoundEnvironment round) {
        this.round = round;
    }
    
    
    @Override
    protected Stream<Element> annotated(Class<? extends Annotation> annotation) {
        return round.getElementsAnnotatedWith(annotation).stream().map(element -> element);
    }

}
