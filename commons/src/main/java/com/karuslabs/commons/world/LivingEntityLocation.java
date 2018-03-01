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
package com.karuslabs.commons.world;

import com.karuslabs.commons.util.Weak;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;


/**
 * A concrete subclass of {@code EntityLocation} which is bound to a {@code LivingEntity} and may be dynamically updated.
 * 
 * @param <GenericEntity> the subclass of LivingEntity
 */
public class LivingEntityLocation<GenericEntity extends LivingEntity> extends EntityLocation<GenericEntity> {
    
    /**
     * Constructs a {@code LivingEntityLocation} for the specified {@code LivingEntity}, which copies the specified
     * location.
     * 
     * @param entity the entity
     * @param location the location
     */
    public LivingEntityLocation(GenericEntity entity, LivingEntityLocation<GenericEntity> location) {
        super(entity, location);
    }
    
    /**
     * Constructs a {@code LivingEntityLocation} with the specified entity, offset, offset relativity, entity nullability and
     * whether to update the location.
     * 
     * @param entity the entity
     * @param offset the offset
     * @param relative true if the offset is relative to the direction of this location; else false
     * @param nullable true if the entity may be null; else false
     * @param update true if the location should be updated to reflect the current location of the entity; else false
     */
    public LivingEntityLocation(GenericEntity entity, Position offset, boolean relative, boolean nullable, boolean update) {
        super(entity, offset, nullable, relative, update);
    }
    
    /**
     * Constructs a {@code LivingEntityLocation} with the specified entity, offset, offset relativity, entity nullability and
     * whether to update the location. Adds the distance between the entity and the location to the offset.
     * 
     * @param entity the entity
     * @param location the location
     * @param offset the offset
     * @param relative true if the offset is relative to the direction of this location; else false
     * @param nullable true if the entity may be null; else false
     * @param update true if the location should be updated to reflect the current location of the entity; else false
     */
    public LivingEntityLocation(GenericEntity entity, Location location, Position offset, boolean relative, boolean nullable, boolean update) {
        super(new Weak<>(entity), location, offset.add(location.toVector().subtract(entity.getEyeLocation().toVector())), nullable, relative, update);
    }
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {
        entity.ifPreset(entity -> update(entity.getLocation(current).add(0, entity.getEyeHeight(), 0)));
    }
    
    
    /**
     * Creates a {@code LivingEntityLocation} builder with the specified {@code LivingEntity}.
     * 
     * @param <GenericEntity> the subclass of LivingEntity
     * @param entity the entity
     * @return the builder
     */
    public static<GenericEntity extends LivingEntity> LivingEntityBuilder<GenericEntity> builder(GenericEntity entity) {
        return new LivingEntityBuilder<>(new LivingEntityLocation<>(entity, new Position(), false, false, false));
    }
    
    /**
     * Creates a {@code LivingEntityLocation} builder with the specified {@code LivingEntity} and locations.
     * 
     * @param <GenericEntity> the subclass of LivingEntity
     * @param entity the entity
     * @param location the location
     * @return the builder
     */
    public static<GenericEntity extends LivingEntity> LivingEntityBuilder<GenericEntity> builder(GenericEntity entity, Location location) {
        return new LivingEntityBuilder<>(new LivingEntityLocation<>(entity, location, new Position(), false, false, false));
    }
    
    /**
     * Represents a builder for {@code LivingEntityLocation}s.
     * 
     * @param <GenericEntity> the subclass of LivingEntity
     */
    public static class LivingEntityBuilder<GenericEntity extends LivingEntity> extends AbstractBuilder<LivingEntityBuilder<GenericEntity>, LivingEntityLocation<?>> {

        private LivingEntityBuilder(LivingEntityLocation location) {
            super(location);
        }
        
        /**
         * {@inheritDoc} 
         */
        @Override
        protected LivingEntityBuilder<GenericEntity> getThis() {
            return this;
        }
        
        /**
         * Builds the {@code LivingEntityLocation}.
         * 
         * @return the LivingEntityLocation
         */
        @Override
        public LivingEntityLocation<GenericEntity> build() {
            return (LivingEntityLocation<GenericEntity>) location;
        }
        
    }
    
}