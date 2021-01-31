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

import org.algorithmx.rulii.bind.convert.ConverterRegistry;
import org.algorithmx.rulii.spring.script.SpringElScriptProcessor;
import org.algorithmx.rulii.util.SystemDefaultsHolder;
import org.algorithmx.rulii.util.reflect.ObjectFactory;

public class SystemDefaultsInitializer {

    public SystemDefaultsInitializer(ObjectFactory objectFactory, SpringElScriptProcessor scriptProcessor,
                                     ConverterRegistry registry) {
        super();
        init(objectFactory, scriptProcessor, registry);
    }

    private void init(ObjectFactory objectFactory, SpringElScriptProcessor scriptProcessor, ConverterRegistry registry) {
        SpringEnvironmentSystemDefaults result = new SpringEnvironmentSystemDefaults(objectFactory, scriptProcessor, registry);
        // Set Spring as the default
        SystemDefaultsHolder.getInstance().setDefaults(result);
    }
}
