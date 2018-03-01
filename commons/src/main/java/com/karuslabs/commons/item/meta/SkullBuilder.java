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

import java.util.UUID;
import javax.annotation.Nonnull;

import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import static org.bukkit.Bukkit.getOfflinePlayer;


/**
 * Represents a builder for items with {@code SkullMeta}.
 */
public class SkullBuilder extends Builder<SkullBuilder, SkullMeta> {
    
    /**
     * Constructs a {@code SkullBuilder} for the specified {@code ItemStack}.
     *  
     * @param item the ItemStack
     */
    public SkullBuilder(ItemStack item) {
        super(item);
    }
    
    /**
     * Constructs a {@code SkullBuilder} which copies the specified {@code Builder}.
     *  
     * @param builder the Builder to be copied
     */
    public SkullBuilder(Builder builder) {
        super(builder);
    }
    
    /**
     * Sets the head of the skull owner.
     * 
     * @param head the head of the skull owner
     * @return this
     */
    public SkullBuilder owner(Head head) {
        return owner(head.getID());
    }
    
    /**
     * Sets the id of the skull owner.
     * 
     * @param id the id of the skull owner
     * @return this
     */
    public SkullBuilder owner(UUID id) {
        return owner(getOfflinePlayer(id));
    }
    
    /**
     * Sets the {@code OfflinePlayer} of the skull owner.
     * 
     * @param player the offline player
     * @return this
     */
    public SkullBuilder owner(OfflinePlayer player) {
        meta.setOwningPlayer(player);
        return this;
    }
    
    /**
     * Sets the name of the skull owner.
     * 
     * @param name the name of the skull owner
     * @return this
     */
    public SkullBuilder owner(String name) {
        meta.setOwner(name);
        return this;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected @Nonnull SkullBuilder getThis() {
        return this;
    }
    
}