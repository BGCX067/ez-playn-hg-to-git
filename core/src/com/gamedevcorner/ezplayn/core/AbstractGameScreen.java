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

import static playn.core.PlayN.graphics;

import java.util.*;

import playn.core.*;

/**
 * A single game can have multiple screens that can be switched between easily.
 * Only a single screen will be active at any given time.
 * @author Prageeth Silva
 * @param <T> The main game object which is a child of {@link AbstractExtendedGame}
 * @see InteractiveDrawable
 * @see ControlHolder
 */
public abstract class AbstractGameScreen<T extends AbstractExtendedGame> implements InteractiveDrawable, ControlHolder
{

    protected GroupLayer rootLayer;
    protected GroupLayer controlLayer;
    protected GroupLayer messageLayer;

    private List<Message> activeMessages = new ArrayList<Message>();
    private List<AbstractControl> activeControls = new ArrayList<AbstractControl>();

    protected T game = null;

    protected AbstractGameScreen<?> previousScreen = null;

    protected int width = 0;
    protected int height = 0;

    protected AbstractGameScreen(T game, int width, int height)
    {

        this.game = game;
        this.width = width;
        this.height = height;

        this.rootLayer = graphics().createGroupLayer();
        this.controlLayer = graphics().createGroupLayer();
        this.messageLayer = graphics().createGroupLayer();

    }

    public void onShown(Object obj) { /* NOOP */ }

    @Override
    public void update(float delta)
    {
        if (this.activeMessages.size() > 0)
        {
            try
            {
                for (Message m : this.activeMessages)
                {
                    m.update(delta);
                }
            }
            catch (Exception e) { /* Ignore */ }
        }
        else
        {
            try
            {
                for (AbstractControl c : this.activeControls)
                {
                    c.update(delta);
                }
            }
            catch (Exception e) { /* Ignore */ }
        }
    }

    @Override
    public void paint(float alpha)
    {
        if (this.activeMessages.size() > 0)
        {
            try
            {
                for (Message m : this.activeMessages)
                {
                    m.paint(alpha);
                }
            }
            catch (Exception e) { /* Ignore */ }
        }
        else
        {
            try
            {
                for (AbstractControl c : this.activeControls)
                {
                    c.paint(alpha);
                }
            }
            catch (Exception e) { /* Ignore */ }
        }
    }

    @Override
    public void onPointerDown(int x, int y)
    {
        if (this.activeMessages.size() > 0)
        {
            try
            {
                for (Message m : this.activeMessages)
                {
                    m.onPointerDown(x - m.getX(), y - m.getY());
                }
            }
            catch (Exception e) { /* Ignore */ }
        }
        else
        {
            try
            {
                int nx, ny;
                for (AbstractControl c : this.activeControls)
                {
                    nx = x - c.getX();
                    ny = y - c.getY();
                    if (c.isInNormRange(nx, ny))
                    {
                        c.onPointerDown(nx, ny);
                    }
                }
            }
            catch (Exception e) { /* Ignore */ }
        }
    }

    @Override
    public void onPointerUp(int x, int y)
    {
        if (this.activeMessages.size() > 0)
        {
            try
            {
                for (Message m : this.activeMessages)
                {
                    m.onPointerUp(x - m.getX(), y - m.getY());
                }
            }
            catch (Exception e) { /* Ignore */ }
        }
        else
        {
            try
            {
                for (AbstractControl c : this.activeControls)
                {
                    c.onPointerUp(x - c.getX(), y - c.getY());
                }
            }
            catch (Exception e) { /* Ignore */ }
        }
    }

    @Override
    public void onPointerMove(int x, int y)
    {
        if (this.activeMessages.size() > 0)
        {
            try
            {
                for (Message m : this.activeMessages)
                {
                    m.onPointerMove(x - m.getX(), y - m.getY());
                }
            }
            catch (Exception e) { /* Ignore */ }
        }
        else
        {
            try
            {
                int nx, ny;
                for (AbstractControl c : this.activeControls)
                {
                    nx = x - c.getX();
                    ny = y - c.getY();
                    if (c.isInNormRange(nx, ny))
                    {
                        c.onPointerMove(nx, ny);
                    }
                    else
                    {
                        c.onPointerLeave(x, y);
                    }
                }
            }
            catch (Exception e) { /* Ignore */ }
        }
    }

    @Override
    public void onPointerScroll(int velocity)
    {
        if (this.activeMessages.size() > 0)
        {
            try
            {
                for (Message m : this.activeMessages)
                {
                    m.onPointerScroll(velocity);
                }
            }
            catch (Exception e) { /* Ignore */ }
        }
        else
        {
            try
            {
                for (AbstractControl c : this.activeControls)
                {
                    c.onPointerScroll(velocity);
                }
            }
            catch (Exception e) { /* Ignore */ }
        }
    }

    @Override
    public void onKeyDown(int keyCode)
    {
        if (this.activeMessages.size() > 0)
        {
            try
            {
                for (Message m : this.activeMessages)
                {
                    m.onKeyDown(keyCode);
                }
            }
            catch (Exception e) { /* Ignore */ }
        }
        else
        {
            try
            {
                for (AbstractControl c : this.activeControls)
                {
                    c.onKeyDown(keyCode);
                }
            }
            catch (Exception e) { /* Ignore */ }
        }
    }

    @Override
    public void onKeyUp(int keyCode)
    {
        if (this.activeMessages.size() > 0)
        {
            try
            {
                for (Message m : this.activeMessages)
                {
                    m.onKeyUp(keyCode);
                }
            }
            catch (Exception e) { /* Ignore */ }
        }
        else
        {
            try
            {
                for (AbstractControl c : this.activeControls)
                {
                    c.onKeyUp(keyCode);
                }
            }
            catch (Exception e) { /* Ignore */ }
        }
    }

    @Override
    public Layer getRootLayer()
    {
        return this.rootLayer;
    }

    public GroupLayer getMessageLayer()
    {
        return this.messageLayer;
    }

    public GroupLayer getControlLayer()
    {
        return this.controlLayer;
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
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public T getGame()
    {
        return game;
    }

    public AbstractGameScreen<?> getPreviousScreen()
    {
        return this.previousScreen;
    }

    protected Collection<Message> getActiveMessages()
    {
        return this.activeMessages;
    }

    @Override
    public Collection<AbstractControl> getChildControls()
    {
        return this.activeControls;
    }

    @Override
    public boolean addChildControl(AbstractControl control)
    {
        if (control == null) { return false; }
        this.activeControls.add(control);
        this.controlLayer.add(control.getRootLayer());
        return true;
    }

    @Override
    public boolean removeChildControl(AbstractControl control)
    {
        if (control == null) { return false; }
        return this.activeControls.remove(control);
    }

    protected void clearMessages()
    {

        for (Message m : this.activeMessages)
        {
            m.hide();
        }
        this.activeMessages.clear();

        if (this.getMessageLayer() != null)
        {
            this.getMessageLayer().clear();
        }

    }

    protected void showMessages(Message msg)
    {
        if (this.getMessageLayer() != null && msg.getRootLayer() != null)
        {
            msg.show();
            this.getMessageLayer().add(msg.getRootLayer());
            this.activeMessages.add(msg);
        }
    }

}