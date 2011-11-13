/**
 * Copyright 2011 The EzPlayN Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.gamedevcorner.ezplayn.core;

import playn.core.*;

/**
 * An interface that can be used for all drawing objects.
 * Assumes the object is in 2D, uses the concept of {@link playn.core.Layer} in PlayN.
 * @author Prageeth Silva
 * @see playn.core.Layer@playn.core.Layer
 */
public interface Drawable
{

    /**
     * A method that can be called to initialise the object.
     * Due to the interfacing with the callback, threads can be used if/when
     * it's made available later.
     * @param callback The callback that handles the succession/failure of the action.
     * @param obj Any object that needs to be passed down; type-casting will be required.
     */
    public void init(final ActionCallback<Void> callback, Object obj);

    /**
     * The update method that will get called by the cascading of the main loop.
     * @param delta Time in milliseconds since the last update.
     */
    public void update(float delta);

    /**
     * The paint method that will get called by the cascading of the main loop.
     * @param alpha Time in milliseconds since the last paint.
     */
    public void paint(float alpha);

    /**
     * Gets the top-most/root layer that holds the entire drawable object.
     * This allows multi-layers objects with the use of a playn.core.GroupLayer
     * @return The root layer of the object
     */
    public Layer getRootLayer();

    /**
     * Gets the width of the object.
     * @return The width in pixels.
     */
    public int getWidth();

    /**
     * Gets the height of the object.
     * @return The height in pixels.
     */
    public int getHeight();

}
