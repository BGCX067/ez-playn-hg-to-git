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

/**
 * An extension of the {@link Drawable} interface to allow user interaction.
 * @author Prageeth Silva
 */
public interface InteractiveDrawable extends Drawable
{

    /**
     * Called when the pointer is pressed down.
     * @param x The x-coordinate in pixels
     * @param y The y-coordinate in pixels
     */
    public void onPointerDown(int x, int y);

    /**
     * Called when the pointer is released.
     * @param x The x-coordinate in pixels
     * @param y The y-coordinate in pixels
     */
    public void onPointerUp(int x, int y);

    /**
     * Called when the pointer moves.
     * @param x The x-coordinate in pixels
     * @param y The y-coordinate in pixels
     */
    public void onPointerMove(int x, int y);

    /**
     * Called when the pointer scrolls.
     * @param velocity The velocity in pixels/sec.
     */
    public void onPointerScroll(int velocity);

    /**
     * Called when a key is pressed down.
     * @param keyCode The ASCII key code of the key being pressed.
     * @see playn.core.Keyboard
     */
    public void onKeyDown(int keyCode);

    /**
     * Called when a key is released.
     * @param keyCode The ASCII key code of the key being pressed.
     * @see playn.core.Keyboard
     */
    public void onKeyUp(int keyCode);

}
