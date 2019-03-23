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
package com.karuslabs.commons.command.suggestions;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.*;

import java.util.concurrent.CompletableFuture;

import org.bukkit.command.CommandSender;


/**
 * A client-side {@code SuggestionProvider}.
 */
public enum ClientsideProvider implements SuggestionProvider<CommandSender> {
    
    ENTITIES,
    RECIPES,
    SOUNDS;
    
    
    /**
     * Returns a {@code CompletableFutre} that contains an empty {@code Suggestions}
     * 
     * @param context the context
     * @param builder the builder
     * @return an empty suggestions
     */
    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<CommandSender> context, SuggestionsBuilder builder) {
        return builder.buildFuture();
    }
    
}
