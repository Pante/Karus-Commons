/*
 * The MIT License
 *
 * Copyright 2019 Matthias.
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
package com.karuslabs.annotations.filters;

import java.util.stream.Stream;
import javax.lang.model.element.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.of;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class FilterTest {
    
    static final Element MODULE;
    static final Element PACKAGE;
    static final Element TYPE;
    static final Element DEFAULT;
    static final Element ENCLOSED;
    
    static {
        MODULE = when(mock(ModuleElement.class).accept(any(), any())).then(
            invocation -> invocation.getArgument(0, ElementVisitor.class).visitModule((ModuleElement) invocation.getMock(), null)
        ).getMock();
        PACKAGE = when(mock(PackageElement.class).accept(any(), any())).then(
            invocation -> invocation.getArgument(0, ElementVisitor.class).visitPackage((PackageElement) invocation.getMock(), null)
        ).getMock();
        TYPE = when(mock(TypeElement.class).accept(any(), any())).then(
            invocation -> invocation.getArgument(0, ElementVisitor.class).visitType((TypeElement) invocation.getMock(), null)
        ).getMock();
        
        DEFAULT = when(mock(VariableElement.class).accept(any(), any())).then(
            invocation -> invocation.getArgument(0, ElementVisitor.class).visitVariable((VariableElement) invocation.getMock(), null)
        ).getMock();
        
        ENCLOSED = when(mock(VariableElement.class).accept(any(), any())).then(
            invocation -> invocation.getArgument(0, ElementVisitor.class).visitVariable((VariableElement) invocation.getMock(), null)
        ).getMock();
        
        when(ENCLOSED.getEnclosingElement()).thenReturn(MODULE);
    }
    
    
    @ParameterizedTest
    @MethodSource("visit_parameters")
    void visit(ElementVisitor<Element, Void> visitor, Element element, Element expected) {
        assertEquals(expected, element.accept(visitor, null));
    }
    
    
    static Stream<Arguments> visit_parameters() {
        return Stream.of(
            of(ClassFilter.FILTER, MODULE, null),
            of(ClassFilter.FILTER, PACKAGE, null),
            of(ClassFilter.FILTER, TYPE, TYPE),
            of(ClassFilter.FILTER, DEFAULT, null),
            of(ClassFilter.FILTER, ENCLOSED, null),
            
            of(PackageFilter.FILTER, MODULE, null),
            of(PackageFilter.FILTER, PACKAGE, PACKAGE),
            of(PackageFilter.FILTER, TYPE, null),
            of(PackageFilter.FILTER, ENCLOSED, null),
            
            of(ModuleFilter.FILTER, MODULE, MODULE),
            of(ModuleFilter.FILTER, PACKAGE, null),
            of(ModuleFilter.FILTER, TYPE, null),
            of(ModuleFilter.FILTER, ENCLOSED, MODULE)
        );
    }

} 