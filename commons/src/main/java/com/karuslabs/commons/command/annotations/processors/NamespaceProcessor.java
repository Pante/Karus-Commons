/*
 * The MIT License
 *
 * Copyright 2019 Karus Labs.
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
package com.karuslabs.commons.command.annotations.processors;

import com.karuslabs.annotations.processors.AnnotationProcessor;
import com.karuslabs.annotations.filters.ClassFilter;
import com.karuslabs.commons.command.annotations.*;

import com.google.auto.service.AutoService;

import java.util.*;
import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;


@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_11)
@SupportedAnnotationTypes({
    "com.karuslabs.commons.command.annotations.Literal", "com.karuslabs.commons.command.annotations.Literals",
    "com.karuslabs.commons.command.annotations.Argument", "com.karuslabs.commons.command.annotations.Arguments"
})
public class NamespaceProcessor extends AnnotationProcessor {
    
    Map<String, Set<List<String>>> scopes;
    Set<String> existing;
    
    
    public NamespaceProcessor() {
        scopes = new HashMap<>();
        existing = new HashSet<>();
    }
    
    
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment environment) {
        for (var element : environment.getElementsAnnotatedWithAny(annotations.toArray(ARRAY))) {
            process(element, scope(element));
        }
        
        scopes.clear();
        
        return false;
    }
    
    protected Set<List<String>> scope(Element element) {
        var type = element.accept(ClassFilter.FILTER, null).asType().toString();
        var scope = scopes.get(type);
        
        if (scope == null) {
            scope = new HashSet<>();
            scopes.put(type, scope);
        }
        
        return scope;
    }
    
    
    protected void process(Element element, Set<List<String>> scope) {
        for (var literal : element.getAnnotationsByType(Literal.class)) {
            process(element, "Literal", literal.namespace(), literal.aliases());
            process(element, scope, "Literal", literal.namespace());
        }
        
        for (var argument : element.getAnnotationsByType(Argument.class)) {
            process(element, scope, "Argument", argument.namespace());
        }
    }
    
    
    protected void process(Element element, String annotation, String[] namespace, String[] aliases) {
        if (namespace.length > 0 && aliases.length > 0) {
            aliases = Arrays.copyOf(aliases, aliases.length + 1);
            aliases[aliases.length - 1] = namespace[namespace.length - 1];
            
            for (var alias : aliases) {
                if (!existing.add(alias)) {
                    warn(element, "Duplicate alias: " + alias);
                }
            }
            
            existing.clear();
        }
    }
    
    protected void process(Element element, Set<List<String>> scope, String annotation, String[] namespace) {
        if (namespace.length == 0) {
            error(element, "Invalid namespace for @" + annotation + ", namespace cannot be empty");
            
        } else if (!scope.add(List.of(namespace))) {
            error(element, "Invalid namespace for @" + annotation + Arrays.toString(namespace) + ", namespace already exists");
        }
    }
    
}