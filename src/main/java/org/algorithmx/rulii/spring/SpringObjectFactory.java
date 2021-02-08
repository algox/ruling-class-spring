/*
 * This software is licensed under the Apache 2 license, quoted below.
 *
 * Copyright (c) 1999-2021, Algorithmx Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.algorithmx.rulii.spring;

import org.algorithmx.rulii.core.UnrulyException;
import org.algorithmx.rulii.util.reflect.DefaultObjectFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.util.Assert;

import java.lang.ref.WeakReference;

/**
 * ObjectFactory implemented using Spring.
 *
 * @author Max Arulananthan
 * @since 1.0
 */
public class SpringObjectFactory extends DefaultObjectFactory {

    // Underlying Spring Factory that does the real work.
    private WeakReference<AutowireCapableBeanFactory> ref;

    public SpringObjectFactory(AutowireCapableBeanFactory beanFactory) {
        super();
        Assert.notNull(beanFactory, "beanFactory cannot be null.");
        this.ref = new WeakReference(beanFactory);
    }

    @Override
    protected <T> T createInternal(Class<T> type) throws UnrulyException {
        if (ref.get() == null) throw new UnrulyException("Looking like the application context was closed");
        return ref.get().createBean(type);
    }
}
