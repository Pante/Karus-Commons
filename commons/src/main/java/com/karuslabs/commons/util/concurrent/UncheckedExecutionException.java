/*
 * The MIT License
 *
 * Copyright 2017 Karus Labs.
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
package com.karuslabs.commons.util.concurrent;

import java.util.concurrent.ExecutionException;


/**
 * Wraps an {@link java.util.concurrent.ExecutionException} with an unchecked exception.
 */
public class UncheckedExecutionException extends RuntimeException {
    
    private ExecutionException cause;
    
    
    /**
     * Constructs an {@code UncheckedExecutionException} which wraps the specified {@code ExecutionException}.
     * 
     * @param cause the ExecutionException
     */
    public UncheckedExecutionException(ExecutionException cause) {
        this.cause = cause;
    }
    
    
    /**
     * Returns the cause of this exception.
     * 
     * @return the ExecutionException which is the cause of this exception.
     */
    @Override
    public ExecutionException getCause() {
        return cause;
    }
    
}