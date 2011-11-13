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

import playn.core.ResourceCallback;

/**
 * A generic callback wrapper interface for any actions.
 * This can be useful if/when threads are allowed.
 * @author Prageeth Silva
 * @param <T> A generic type
 */
public interface ActionCallback<T> extends ResourceCallback<T>
{

    /**
     * Called when the action is successfully done.
     * @param result The result returned by the callback,
     *        use {@link java.lang.Void} if no return value is required.
     */
    @Override
    void done(T result);

    /**
     * Called when the action fails.
     * @param error The exception thrown that caused the action to fail.
     */
    @Override
    void error(Throwable error);

}
