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

import java.util.*;

import playn.core.*;

/**
 * An overlay object for displaying messages on {@link AbstractGameScreen}s
 * @author Prageeth Silva
 * @see AbstractControl
 * @see ControlHolder
 */
public class Message extends AbstractControl implements ControlHolder
{

    private List<AbstractControl> activeControls = new ArrayList<AbstractControl>();

    private boolean active = false;

    private static Message staticMessage = new Message(0,0);

    public Message(int width, int height, Image backgroundImage)
    {
        super(width, height, backgroundImage);
        //this.setAutoCentring(true);
    }

    public Message(int width, int height)
    {
        this(width, height, null);
        //this.setAutoCentring(true);
    }

    @Override
    public void init(ActionCallback<Void> callback, Object obj) { /* NOOP */ }

    @Override
    public void update(float delta) { /* NOOP */ }

    @Override
    public void paint(float alpha) { /* NOOP */ }

    @Override
    public void onPointerDown(int x, int y)
    {
        for (AbstractControl c : this.activeControls)
        {
            c.onPointerDown(x - c.getX(), y - c.getY());
        }
    }

    @Override
    public void onPointerUp(int x, int y)
    {
        for (AbstractControl c : this.activeControls)
        {
            c.onPointerUp(x - c.getX(), y - c.getY());
        }
    }

    @Override
    public void onPointerMove(int x, int y)
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

    @Override
    public void onPointerLeave(int x, int y) { /* NOOP */ }

    @Override
    public void onPointerScroll(int velocity)
    {
        for (AbstractControl c : this.activeControls)
        {
            c.onPointerScroll(velocity);
        }
    }

    @Override
    public void onKeyDown(int keyCode)
    {
        for (AbstractControl c : this.activeControls)
        {
            c.onKeyDown(keyCode);
        }
    }

    @Override
    public void onKeyUp(int keyCode)
    {
        for (AbstractControl c : this.activeControls)
        {
            c.onKeyUp(keyCode);
        }
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
        return true;
    }

    @Override
    public boolean removeChildControl(AbstractControl control)
    {
        if (control == null) { return false; }
        return this.activeControls.remove(control);
    }

    @Override
    public void commitLayout()
    {
        this.rootLayer.setAlpha(0.75f);
        this.rootLayer.setTranslation(this.getX(), this.getY());
    }

    public final void show()
    {
        this.active = true;
    }

    public final void hide()
    {
        this.active = false;
    }

    public boolean isActive()
    {
        return this.active;
    }

    /* Static methods */

    /**
     * Create a message object with only text.
     * Shows white text on a black translucent rectangle
     * @param width The width of the message body
     * @param height The height of the message body
     * @param text The text to display
     * @return New instance of a message.
     */
    public static Message createTextMessage(int width, int height, String text)
    {
        return staticMessage.innerCreateTextMessage(width, height, text, Color.argb(150, 0, 0, 0), Color.argb(255, 255, 255, 255));
    }

    /**
     * Create a message object with only text.
     * @param width The width of the message body
     * @param height The height of the message body
     * @param text The text to display
     * @param backColor The back color using {@link playn.core.Color}
     * @param textColor The text color using {@link playn.core.Color}
     * @return New instance of a message.
     */
    public static Message createTextMessage(int width, int height, String text, int backColor, int textColor)
    {
        return staticMessage.innerCreateTextMessage(width, height, text, backColor, textColor);
    }


    private Message innerCreateTextMessage(int width, int height, String text, int backColor, int textColor)
    {
        return new Message.TextMessage(width, height, text, backColor, textColor);
    }

    /**
     * A private wrapper class to show text only.
     * @author Prageeth Silva
     * @see Message
     */
    private class TextMessage extends Message
    {

        public TextMessage(int width, int height, String text, int backColor, int textColor)
        {

            super(width, height);

            CanvasLayer canvasLayer = graphics().createCanvasLayer(this.getWidth(), this.getHeight());
            Canvas canvas = canvasLayer.canvas();
            this.rootLayer.add(canvasLayer);

            canvas.setFillColor(backColor);
            canvas.fillRect(0, 0, this.getWidth(), this.getHeight());
            canvas.setFillColor(textColor);
            canvas.drawText(text, 20, this.getHeight()/2);

            canvasLayer.setTranslation(this.getX(), this.getY());

        }

    }

}
