/**
 * Copyright

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

public abstract class Control implements InteractiveDrawable
{

    protected int width = 0;
    protected int height = 0;

    protected int x = 0;
    protected int y = 0;

    protected GroupLayer rootLayer = null;

    protected ImageLayer backgroundLayer = null;
    protected Image backgroundImage = null;

    protected boolean autoCentring = true;

    public Control(int width, int height, Image backgroundImage)
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

    public Control(int width, int height)
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

    public void setWidth(int width)
    {

        this.width = width;

        if (autoCentring)
        {
            this.x = (ExtendedGame.screenWidth-width)/2;
        }

    }

    public void setHeight(int height)
    {

        this.height = height;

        if (autoCentring)
        {
            this.y = (ExtendedGame.screenHeight-height)/2;
        }

    }

    public void commitLayout()
    {
        this.rootLayer.setTranslation(this.x, this.y);
    }

    public void commitLayout(int x, int y)
    {
        this.x = x;
        this.y = y;
        this.commitLayout();
    }

    public boolean isAutoCentring()
    {
        return this.autoCentring;
    }

    public void setAutoCentring(boolean autoCentring)
    {
        this.autoCentring = autoCentring;
        if (autoCentring)
        {
            this.x = (ExtendedGame.screenHeight-this.width)/2;
            this.y = (ExtendedGame.screenHeight-this.height)/2;
        }
    }

    protected boolean isInNormRange(int x, int y)
    {
        return (x >= 0 && x <= this.width) && (y >= 0 && y <= this.height);
    }

    protected boolean isInAbsRange(int x, int y)
    {
        x -= this.x;
        y -= this.y;
        return (x >= 0 && x <= this.width) && (y >= 0 && y <= this.height);
    }

}