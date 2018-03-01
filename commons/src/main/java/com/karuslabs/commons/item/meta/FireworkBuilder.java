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
package com.karuslabs.commons.item.meta;

import com.karuslabs.commons.item.Builder;

import java.util.List;
import javax.annotation.Nonnull;

import org.bukkit.FireworkEffect;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;


/**
 * Represents a builder for items with {@code FireworkMeta}.
 */
public class FireworkBuilder extends Builder<FireworkBuilder, FireworkMeta> {
    
    /**
     * Constructs a {@code FireworkBuilder} for the specified {@code ItemStack}.
     *  
     * @param item the ItemStack
     */
    public FireworkBuilder(ItemStack item) {
        super(item);
    }
    
    /**
     * Constructs a {@code FireworkBuilder} which copies the specified {@code Builder}.
     *  
     * @param builder the Builder to be copied
     */
    public FireworkBuilder(Builder builder) {
        super(builder);
    }
    
    
    /**
     * Adds the specified firework effects.
     * 
     * @param effects the effects
     * @return this
     */
    public FireworkBuilder effects(FireworkEffect... effects) {
        meta.addEffects(effects);
        return this;
    }
    
    /**
     * Adds the specified firework effects.
     * 
     * @param effects the effects
     * @return this
     */
    public FireworkBuilder effects(List<FireworkEffect> effects) {
        meta.addEffects(effects);
        return this;
    }
    
    /**
     * Sets the power.
     * 
     * @param power the power
     * @return this
     */
    public FireworkBuilder power(int power) {
        meta.setPower(power);
        return this;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected @Nonnull FireworkBuilder getThis() {
        return this;
    }
    
}