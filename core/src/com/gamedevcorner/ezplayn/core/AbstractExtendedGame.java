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
import playn.core.Keyboard.Event;

/**
 * The main {@link Game} class with extra added features
 * @author Prageeth Silva
 */
public abstract class AbstractExtendedGame implements Game
{

    public static int screenWidth = 640;
    public static int screenHeight = 480;

    private AbstractGameScreen<?> currentScreen = null;
    private AbstractGameScreen<?> defaultLoadingScreen = null;

    private int screensLoadCount = -1;

    protected AbstractExtendedGame(int screenWidth, int screenHieght)
    {
        AbstractExtendedGame.screenWidth = screenWidth;
        AbstractExtendedGame.screenHeight = screenHieght;
    }

    @Override
    public void init()
    {

        // set up the screen dimensions

        graphics().setSize(AbstractExtendedGame.screenWidth, AbstractExtendedGame.screenHeight);

        // hook the input devices

        final AbstractExtendedGame self = this;

        // pointer
        pointer().setListener(new Pointer.Adapter() {

            @Override
            public void onPointerStart(Pointer.Event event)
            {
                self.onPointerDown((int)event.x(), (int)event.y());
            }

            @Override
            public void onPointerEnd(Pointer.Event event)
            {
                self.onPointerUp((int)event.x(), (int)event.y());
            }

            @Override
            public void onPointerDrag(Pointer.Event event)
            {
                self.onPointerMove((int)event.x(), (int)event.y());
            }

        });

        // keyboard
        keyboard().setListener(new Keyboard.Listener() {

            @Override
            public void onKeyUp(Event event)
            {
                self.onKeyUp(event.keyCode());
            }

            @Override
            public void onKeyDown(Event event)
            {
                self.onKeyDown(event.keyCode());
            }

        });

        // setup default loading screen
        // check for user defined loading screens
        if (this.defaultLoadingScreen == null)
        {
            this.defaultLoadingScreen = new DefaultLoadingScreen(this, AbstractExtendedGame.screenWidth, AbstractExtendedGame.screenHeight);
            this.defaultLoadingScreen.init(null, null);
        }
        this.setCurrentScreen(this.defaultLoadingScreen, null);

    }

    @Override
    public void update(float delta)
    {

        // do usual updates
        if (this.currentScreen != null)
        {
            this.currentScreen.update(delta);
        }

        // load custom screens
        // only happens once
        if (this.screensLoadCount < 1)
        {
            // skip one update frame to show loading screen
            if (this.screensLoadCount == 0)
            {
                this.setCurrentScreen(this.loadScreens(), null);
            }
            this.screensLoadCount++;
        }

    }

    @Override
    public void paint(float alpha)
    {
        // usual paint
        if (this.currentScreen != null)
        {
            this.currentScreen.paint(alpha);
        }
    }

    @Override
    public int updateRate()
    {
        //return 25;
        return 0;
    }

    public abstract AbstractGameScreen<?> loadScreens();

    public void onPointerDown(int x, int y)
    {
        if (this.currentScreen != null)
        {
            this.currentScreen.onPointerDown(x, y);
        }
    }

    public void onPointerUp(int x, int y)
    {
        if (this.currentScreen != null)
        {
            this.currentScreen.onPointerUp(x, y);
        }
    }

    public void onPointerMove(int x, int y)
    {
        if (this.currentScreen != null)
        {
            this.currentScreen.onPointerMove(x, y);
        }
    }

    public void onPointerScroll(int velocity)
    {
        if (this.currentScreen != null)
        {
            this.currentScreen.onPointerScroll(velocity);
        }
    }

    public void onKeyDown(int keyCode)
    {
        if (this.currentScreen != null)
        {
            this.currentScreen.onKeyDown(keyCode);
        }
    }

    public void onKeyUp(int keyCode)
    {
        if (this.currentScreen != null)
        {
            this.currentScreen.onKeyUp(keyCode);
        }
    }

    /* Getters and Setters */

    public void setCurrentScreen(AbstractGameScreen<?> screen, Object obj)
    {
        if (screen != null)
        {
            screen.previousScreen = this.currentScreen;
            this.currentScreen = screen;
            graphics().rootLayer().clear();
            graphics().rootLayer().add(screen.getRootLayer());
            if (screen.getMessageLayer() != null)
            {
                graphics().rootLayer().add(screen.getMessageLayer());
            }
            screen.onShown(obj);
        }
    }

    protected void overrideLoadingScreen(AbstractGameScreen<?> customScreen)
    {
        if (customScreen != null)
        {
            this.defaultLoadingScreen = customScreen;
        }
    }

    public void showLoadingScreen()
    {
        this.setCurrentScreen(this.defaultLoadingScreen, null);
    }

    public int getScreenWidth()
    {
        return AbstractExtendedGame.screenWidth;
    }

    public void setScreenWidth(int screenWidth)
    {
        AbstractExtendedGame.screenWidth = screenWidth;
    }

    public int getScreenHeight()
    {
        return AbstractExtendedGame.screenHeight;
    }

    public void setScreenHeight(int screenHeight)
    {
        AbstractExtendedGame.screenHeight = screenHeight;
    }

    public AbstractGameScreen<?> getDefaultLoadingScreen()
    {
        return this.defaultLoadingScreen;
    }

    public void setDefaultLoadingScreen(AbstractGameScreen<?> defaultLoadingScreen)
    {
        this.defaultLoadingScreen = defaultLoadingScreen;
    }

    /* Auxiliary Classes */

    private class DefaultLoadingScreen extends AbstractGameScreen<AbstractExtendedGame>
    {

        private CanvasLayer rootLayer = null;

        protected DefaultLoadingScreen(AbstractExtendedGame game, int width, int height)
        {
            super(game, width, height);
        }

        @Override
        public void init(ActionCallback<Void> callback, Object obj)
        {

            int width = this.width;
            int height = this.height;

            this.rootLayer = graphics().createCanvasLayer(width, height);
            Canvas canvas = this.rootLayer.canvas();

            // set the contents in one go
            canvas.setFillColor(Color.rgb(0, 0, 0)); // black
            canvas.fillRect(0, 0, width, height);
            canvas.setFillColor(Color.rgb(255, 255, 255)); // white
            canvas.drawText("Loading...", width/2, height/2);

        }

        @Override
        public void update(float delta)
        {
            // do nothing
        }

        @Override
        public void paint(float alpha)
        {
            // do nothing
        }

        @Override
        public Layer getRootLayer()
        {
            return this.rootLayer;
        }

        @Override
        public GroupLayer getMessageLayer()
        {
            return null;
        }

    }

}
