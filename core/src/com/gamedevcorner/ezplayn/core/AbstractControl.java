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

import static playn.core.PlayN.*;

import playn.core.*;

/**
 * A control is an drawable that responds to user interaction.
 * @author Prageeth Silva
 * @see InteractiveDrawable
 * @see Drawable
 */
public abstract class AbstractControl implements InteractiveDrawable
{

    private int width = 0;
    private int height = 0;

    private int x = 0;
    private int y = 0;

    protected GroupLayer rootLayer = null;

    protected ImageLayer backgroundLayer = null;
    protected Image backgroundImage = null;

    private boolean autoCentring = true;

    /**
     * A constructor that should be called by the extending child.
     * @param width The width of the control
     * @param height The height of the control
     * @param backgroundImage An image to be used as the background,
     *        use <code>null</code> to leave the background empty.
     */
    protected AbstractControl(int width, int height, Image backgroundImage)
    {
        this.setWidth(width);
        this.setHeight(height);
        this.backgroundImage = backgroundImage;
        this.rootLayer = graphics().createGroupLayer();
        if (this.backgroundImage != null)
        {
            this.backgroundLayer = graphics().createImageLayer(this.backgroundImage);
            this.rootLayer.add(this.backgroundLayer);
        }
    }

    /**
     * A constructor that should be called by the extending child.
     * Assumes the background is empty, but can be set later.
     * @param width The width of the control
     * @param height The height of the control
     */
    protected AbstractControl(int width, int height)
    {
        this(width, height, null);
    }

    @Override
    public Layer getRootLayer()
    {
        return this.rootLayer;
    }

    @Override
    public int getWidth()
    {
        return this.width;
    }

    @Override
    public int getHeight()
    {
        return this.height;
    }

    public abstract void onPointerLeave(int x, int y);

    /**
     * Sets the width of the object.
     * @param width The new width
     */
    public void setWidth(int width)
    {

        this.width = width;

        if (this.autoCentring)
        {
            this.x = (AbstractExtendedGame.screenWidth-width)/2;
        }

    }

    /**
     * Sets the height of the object.
     * @param height The new height
     */
    public void setHeight(int height)
    {

        this.height = height;

        if (autoCentring)
        {
            this.y = (AbstractExtendedGame.screenHeight-height)/2;
        }

    }

    /**
     * <b>Required at the end of initialisation</b> to update the internal layout settings.
     */
    public void commitLayout()
    {
        this.rootLayer.setTranslation(this.x, this.y);
    }

    /**
     * <b>Required at the end of initialisation</b> to update the internal layout settings.
     * Also overwrites and updates the x and y coordinates from the relative {@link ControlHolder}'s position.
     * @param x The x-coordinate in pixels from the left of the parent {@link ControlHolder}
     * @param y The y-coordinate in pixels from the top of the parent {@link ControlHolder}
     */
    public void commitLayout(int x, int y)
    {
        this.x = x;
        this.y = y;
        this.commitLayout();
    }

    /**
     * Checks if the control will auto centre on screen.
     * @return The auto-centring status
     */
    public boolean isAutoCentring()
    {
        return this.autoCentring;
    }

    /**
     * Update the auto-centring status of the control.
     */
    public void setAutoCentring(boolean autoCentring)
    {
        this.autoCentring = autoCentring;
        if (autoCentring)
        {
            this.x = (AbstractExtendedGame.screenHeight-this.width)/2;
            this.y = (AbstractExtendedGame.screenHeight-this.height)/2;
        }
    }

    /**
     * Checks if the given coordinates are within the range of the control.
     * This method assumes the coordinates are already normalised by the parent
     * {@link ControlHolder}. If absolute coordinates are to be check, use
     * {@link AbstractControl#isInAbsRange(int, int)}
     * @param x The x-coordinate from the left of the parent {@link ControlHolder}
     * @param y The y-coordinate from the top of the parent {@link ControlHolder}
     * @return Whether the coordinates are in range.
     */
    protected boolean isInNormRange(int x, int y)
    {
        return (x >= 0 && x <= this.width) && (y >= 0 && y <= this.height);
    }

    /**
     * Checks if the given coordinates are within the range of the control.
     * This method assumes the coordinates are absolute screen coordinates.
     * If normalised coordinates are to be check, use
     * {@link AbstractControl#isInNormRange(int, int)}
     * @param x The x-coordinate from the left of the screen
     * @param y The y-coordinate from the top of the screen
     * @return Whether the coordinates are in range.
     */
    protected boolean isInAbsRange(int x, int y)
    {
        x -= this.x;
        y -= this.y;
        return (x >= 0 && x <= this.width) && (y >= 0 && y <= this.height);
    }

    /**
     * Gets the x-coordinate from the left of the parent {@link ControlHolder}
     * @return The x coordinates in pixels
     */
    public int getX()
    {
        return this.x;
    }

    /**
     * Gets the y-coordinate from the top of the parent {@link ControlHolder}
     * @return The y coordinates in pixels
     */
    public int getY()
    {
        return this.y;
    }



}