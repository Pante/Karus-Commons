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
package com.karuslabs.commons.command.types;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import java.util.List;

import org.bukkit.enchantments.Enchantment;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class EnchantmentTypeTest {
    
    EnchantmentType type = new EnchantmentType();
    
    
    @Test
    void parse() throws CommandSyntaxException {
        Enchantment enchantment = mock(Enchantment.class);
        EnchantmentType.ENCHANTMENTS.put("channeling", enchantment);
                
        assertEquals(enchantment, type.parse(new StringReader("CHANNELING")));
    }
    
    
    @Test
    void parse_throws_exception() throws CommandSyntaxException {
        assertEquals(
            "Unknown enchantment: invalid",
            assertThrows(CommandSyntaxException.class, () -> type.parse(new StringReader("invalid"))).getRawMessage().toString()
        );
    }
    
    
    @Test
    void listSuggestions() {
        EnchantmentType.ENCHANTMENTS.put("arrow_damage", mock(Enchantment.class));
        EnchantmentType.ENCHANTMENTS.put("arrow_fire", mock(Enchantment.class));
        
        SuggestionsBuilder builder = when(mock(SuggestionsBuilder.class).getRemaining()).thenReturn("arro").getMock();
        type.listSuggestions(null, builder);
        
        verify(builder).suggest("arrow_damage");
        verify(builder).suggest("arrow_fire");
    }
    
    
    @Test
    void getExamples() {
        assertEquals(List.of("arrow_damage", "channeling"), type.getExamples());
    }

} 
