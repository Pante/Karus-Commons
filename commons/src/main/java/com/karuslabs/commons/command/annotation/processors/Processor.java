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
package com.karuslabs.commons.command.annotation.processors;

import com.karuslabs.commons.command.*;

import java.util.List;


/**
 * Represents a annotation processor for command-related annotations.
 */
public interface Processor {
    
    /**
     * Processes the annotations for the specified {@code CommandExecutor}.
     * 
     * @param commands the commands to which the executor is registered
     * @param executor the executor which contains the annotations to process
     */
    public void process(List<Command> commands, CommandExecutor executor);
    
    /**
     * Checks if the specified {@code CommandExecutor} has the annotations which
     * this processor processes.
     * 
     * @param executor the CommandExecutor
     * @return true if the executor has the annotations which this processor processes; else false
     */
    public boolean hasAnnotations(CommandExecutor executor);
    
}