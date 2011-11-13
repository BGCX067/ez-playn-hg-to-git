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

import java.util.*;

/**
 * An interface that allows to hold child controls.
 * @author Prageeth Silva
 * @see Control
 */
public interface ControlHolder
{

    /**
     * Gets a collection of child controls associated with this object.
     * @return The collection of child controls.
     */
    public Collection<Control> getChildControls();

    /**
     * Add a child control to the associated control collection.
     * @param control The child control to add.
     * @return <code>true</code> represents succession and <code>false</code>
     *         represents failure.
     */
    public boolean addChildControl(Control control);

    /**
     * Removes an already added child control from the associated control collection.
     * @param control The child control to be removed.
     * @return <code>true</code> represents succession and <code>false</code>
     *         represents failure.
     */
    public boolean removeChildControl(Control control);

}