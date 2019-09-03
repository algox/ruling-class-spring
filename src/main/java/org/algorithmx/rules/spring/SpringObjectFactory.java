/**
 * This software is licensed under the Apache 2 license, quoted below.
 *
 * Copyright (c) 2019, algorithmx.org (dev@algorithmx.org)
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
package org.algorithmx.rules.spring;

import org.algorithmx.rules.core.ObjectFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.util.Assert;

/**
 * ObjectFactory implemented using Spring.
 *
 * @author Max Arulananthan
 * @since 1.0
 */
public class SpringObjectFactory implements ObjectFactory {

    // Underlying Spring Factory that does the real work.
    private final AutowireCapableBeanFactory ctx;

    public SpringObjectFactory(AutowireCapableBeanFactory ctx) {
        super();
        Assert.notNull(ctx, "ctx cannot be null");
        this.ctx = ctx;
    }

    @Override
    public <T> T create(Class<T> beanClass) {
        return ctx.createBean(beanClass);
    }
}
