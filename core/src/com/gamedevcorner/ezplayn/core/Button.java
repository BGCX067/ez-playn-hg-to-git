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

public abstract class Button extends Control
{

    protected ImageLayer disabledLayer = null;

    private boolean allowingDisable = false;

    private boolean enabled = true;

    private boolean expanding = false;
    private int expandOffsetX = 0;
    private int expandOffsetY = 0;
    private float expandScale = 1.1f;

    public Button(int width, int height, Image activeImage, Image disabledImaged)
    {

        super(width, height, activeImage);
        this.setAutoCentring(false);
        this.setExpanding(true);

        if (disabledImaged != null)
        {
            this.disabledLayer = graphics().createImageLayer(disabledImaged);
            this.allowingDisable = true;
        }

    }

    public Button(int width, int height)
    {
        super(width, height, null);
        this.setAutoCentring(false);
        this.setExpanding(true);
    }

    @Override
    public void init(ActionCallback<Void> callback, Object obj) { /* NOOP */ }

    @Override
    public void update(float delta) { /* NOOP */ }

    @Override
    public void paint(float alpha) { /* NOOP */ }

    @Override
    public void onPointerUp(int x, int y)
    {
        if (this.expanding && this.enabled)
        {
            this.rootLayer.setScale(1f, 1f);
            this.rootLayer.setTranslation(this.x, this.y);
        }
    }

    @Override
    public void onPointerDown(int x, int y)
    {
        if (this.expanding && this.enabled)
        {
            this.rootLayer.setScale(this.expandScale, this.expandScale);
            this.rootLayer.setTranslation(this.x - this.expandOffsetX, this.y - this.expandOffsetY);
        }
    }

    @Override
    public void onPointerMove(int x, int y) { /* NOOP */ }

    @Override
    public void onPointerScroll(int velocity) { /* NOOP */ }

    @Override
    public void onKeyDown(int keyCode) { /* NOOP */ }

    @Override
    public void onKeyUp(int keyCode) { /* NOOP */ }


    public boolean isExpanding()
    {
        return this.expanding;
    }

    public void setExpanding(boolean expanding)
    {
        this.expanding = expanding;
        if (expanding)
        {
            this.expandOffsetX = (int)(this.width*(this.expandScale-1))/2;
            this.expandOffsetY = (int)(this.height*(this.expandScale-1))/2;
        }
        else
        {
            this.expandOffsetX = 0;
            this.expandOffsetY = 0;
        }
    }

    public float getExpandScale()
    {
        return this.expandScale;
    }

    public void setExpandScale(float expandScale)
    {
        if (expandScale == 0)
        {
            this.expanding = false;
            this.expandOffsetX = 0;
            this.expandOffsetY = 0;
        }
        else
        {
            this.expandScale = expandScale;
            this.expanding = true;
            this.expandOffsetX = (int)(this.width*(this.expandScale-1))/2;
            this.expandOffsetY = (int)(this.height*(this.expandScale-1))/2;
        }
    }

    public void enable()
    {
        if (this.allowingDisable && this.backgroundImage != null)
        {
            this.rootLayer.clear();
            this.rootLayer.add(this.backgroundLayer);
            this.enabled = true;
        }
    }

    public void disable()
    {
        if (this.allowingDisable && this.disabledLayer != null)
        {
            this.rootLayer.clear();
            this.rootLayer.add(this.disabledLayer);
            this.enabled = false;
        }
    }

    public boolean isAllowingDisable()
    {
        return this.allowingDisable;
    }

    public boolean isEnabled()
    {
        return this.enabled;
    }

}
