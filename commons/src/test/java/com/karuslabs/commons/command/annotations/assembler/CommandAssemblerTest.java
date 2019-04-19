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
package com.karuslabs.commons.command.annotations.assembler;

import com.karuslabs.commons.command.annotations.*;
import com.karuslabs.commons.command.annotations.assembler.CommandAssembler;
import com.karuslabs.commons.util.collections.TokenMap;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.tree.RootCommandNode;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.of;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CommandAssemblerTest {
    
    @Literal(namespace = {"a", "b", "c1"}, aliases = {"c1a", "c1b"})
    @Argument(namespace = {"a", "b", "c2"}, aliases = {"c2a", "c2b"}, type = "bird")
    @Argument(namespace = {"a", "b", "c3"}, aliases = {"c3a", "c3b"}, type = "is", suggestions = "the")
    static class Type {
        
    }
    
    
    CommandAssembler<Object> assembler = new CommandAssembler<>(new RootCommandNode<>(), TokenMap.of());
    Command<Object> execution = context -> 0;
    
    
    @Test
    void assemble_literals() {
        assembler.assemble(Type.class, Type.class.getAnnotationsByType(Literal.class), execution);
        var parent = assembler.container.getChild("a").getChild("b");
        var child = parent.getChild("c1");
        
        assertEquals(execution, child);
        assertEquals(execution, parent.getChild("c1a").getCommand());
        assertEquals(execution, parent.getChild("c1b").getCommand());
    }
    
} 
