/*
 * The MIT License
 *
 * Copyright 2018 Karus Labs.
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
package com.karuslabs.commons.command.annotation.checkers;

import com.karuslabs.commons.command.CommandExecutor;
import com.karuslabs.commons.command.annotation.*;

import java.util.Set;
import javax.annotation.processing.*;
import javax.lang.model.element.*;
import javax.lang.model.type.*;

import static com.karuslabs.commons.annotation.checkers.Elements.annotated;
import static javax.tools.Diagnostic.Kind.ERROR;


/**
 * Represents a checker for the annotations specified in {@code SupportedAnnotationTypes}.
 * 
 * Checks if the annotated target is a {@code CommandExecutor} implementation and whether the
 * annotated target has a {@code Namespace} annotation.
 */
@SupportedAnnotationTypes({
    "com.karuslabs.commons.command.annotation.Information",
    "com.karuslabs.commons.command.annotation.Namespace", "com.karuslabs.commons.command.annotation.Namespaces",
    "com.karuslabs.commons.command.annotation.Literal", "com.karuslabs.commons.command.annotation.Literals",
    "com.karuslabs.commons.command.annotation.Registered", "com.karuslabs.commons.command.annotation.Registrations"
})
public class CommandChecker extends AbstractProcessor {
    
    private TypeMirror expected;
    private Messager messager;
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void init(ProcessingEnvironment environment) {
        super.init(environment);
        expected = environment.getElementUtils().getTypeElement(CommandExecutor.class.getName()).asType();
        messager = environment.getMessager();
    }
    
    /**
     * Checks if the annotated targets are {@code CommandExecutor}s and whether the {@code CommandExecutor}s
     * are annotated with {@code Namespace} annotations.
     * 
     * @param annotations the annotation types requested to be processed
     * @param environment the environment for information about the current and prior round
     * @return false
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment environment) {
        for (Element element : annotated(annotations, environment))  {
            checkAssignability(element);
            checkNamespace(element);
        }
        
        return false;
    }
    
    /**
     * Checks if the specified element is a {@code CommandExecutor}.
     * 
     * @param element the annotated element
     */
    protected void checkAssignability(Element element) {
        if (!processingEnv.getTypeUtils().isAssignable(element.asType(), expected)) {
            messager.printMessage(ERROR, "Invalid annotated type: " + element.asType().toString() + ", type must implement " + CommandExecutor.class.getName() , element);
        }
    }
    
    /**
     * Checks if the specified element has a {@code Namespace} annotation.
     * 
     * @param element the annotated element
     */
    protected void checkNamespace(Element element) {
        if (element.getAnnotationsByType(Namespace.class).length == 0) {
            messager.printMessage(ERROR, "Missing namespace: " + element.asType().toString() + ", command must be declared with a namespace", element);
        }
    }
    
    
    /**
     * Returns the expected annotation target type.
     * 
     * @return the expected type
     */
    public TypeMirror getExpected() {
        return expected;
    }
    
    /**
     * Returns the messager.
     * 
     * @return the messager
     */
    public Messager getMessager() {
        return messager;
    }
    
}