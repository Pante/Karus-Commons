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
package com.karuslabs.puff.match;

import com.karuslabs.puff.Texts;
import com.karuslabs.puff.type.*;

import java.util.*;
import java.util.function.BiConsumer;
import javax.lang.model.element.Element;
import javax.lang.model.type.*;

import org.checkerframework.checker.nullness.qual.Nullable;

abstract class TypeMatch implements Match<TypeMirror> {
    
    final Relation relation;
    final List<TypeMirror> mirrors;
    final String list;
    
    TypeMatch(Relation relation, List<TypeMirror> mirrors, String list) {
        this.relation = relation;
        this.mirrors = mirrors;
        this.list = list;
    }
    
    @Override
    public String describe(Element element) {
        return TypePrinter.simple(element.asType());
    }
    
    @Override
    public String expectation() {
        return relation.relation() + list;
    }
    
    @Override
    public String expectations() {
        return relation.relations() + list;
    }
    
}

class PrimitiveMatch implements Match<TypeMirror> {

    final TypeKind primitive;
    
    PrimitiveMatch(TypeKind primitive) {
        this.primitive = primitive;
    }
    
    @Override
    public boolean match(TypeMirrors types, Element element) {
        return primitive == element.asType().getKind();
    }

    @Override
    public String describe(Element element) {
        return TypePrinter.simple(element.asType());
    }

    @Override
    public String expectation() {
        return "";
    }
    
}

class TypeMirrorMatch extends TypeMatch {
    
    static final BiConsumer<TypeMirror, StringBuilder> FORMAT = (type, builder) -> type.accept(TypePrinter.SIMPLE, builder);
    
    TypeMirrorMatch(Relation relation, TypeMirror... mirrors) {
        super(relation, List.of(mirrors), Texts.and(mirrors, FORMAT));
    }
    
    @Override
    public boolean match(TypeMirrors types, Element element) {
        return relation.test(types, element.asType(), mirrors);
    }
    
}

class ClassMatch extends TypeMatch {

    static final BiConsumer<Class<?>, StringBuilder> FORMAT = (type, builder) -> builder.append(type.getSimpleName());
    
    @Nullable List<Class<?>> classes;
    
    ClassMatch(Relation relation, Class<?>... classes) {
        super(relation, new ArrayList<>(), Texts.and(classes, FORMAT));
        this.classes = List.of(classes);
    }
    
    @Override
    public boolean match(TypeMirrors types, Element element) {
        if (classes != null) {
            for (var type : classes) {
                mirrors.add(types.type(type));
            }
            classes = null;
        }
        
        return relation.test(types, element.asType(), mirrors);
    }
    
}


abstract class Relation {
    
    static final Relation EXACTLY = new Exactly();
    static final Relation SUBTYPE = new Subtype();
    static final Relation SUPERTYPE = new Supertype();
    
    boolean test(TypeMirrors types, TypeMirror type, List<TypeMirror> expected) {
        for (var mirror : expected) {
            if (!test(types, type, expected)) {
                return false;
            }
        }
        
        return true;
    }
    
    abstract boolean test(TypeMirrors types, TypeMirror type, TypeMirror expected);
    
    abstract String relation();
    
    abstract String relations();
    
}

class Exactly extends Relation {

    @Override
    boolean test(TypeMirrors types, TypeMirror type, TypeMirror expected) {
        return types.isSameType(type, expected);
    }

    @Override
    String relation() {
        return "";
    }

    @Override
    String relations() {
        return "";
    }
    
}

class Subtype extends Relation {
    
    @Override
    boolean test(TypeMirrors types, TypeMirror type, TypeMirror expected) {
        return types.isSubtype(type, expected);
    }
    
    @Override
    String relation() {
        return "subtype of ";
    }

    @Override
    String relations() {
        return "subtypes of ";
    }
    
}

class Supertype extends Relation {

    @Override
    boolean test(TypeMirrors types, TypeMirror type, TypeMirror expected) {
        return types.isSubtype(expected, type);
    }

    @Override
    String relation() {
        return "supertype of ";
    }

    @Override
    String relations() {
        return "supertypes of ";
    }
    
}
