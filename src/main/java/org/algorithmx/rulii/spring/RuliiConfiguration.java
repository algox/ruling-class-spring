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
import org.algorithmx.rulii.spring.convert.ConverterAdapter;
import org.algorithmx.rulii.spring.script.SpringElScriptEngine;
import org.algorithmx.rulii.spring.script.SpringElScriptProcessor;
import org.algorithmx.rulii.util.reflect.ObjectFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;

/**
 * Main Rulii configuration. Defines the ObjectFactory.
 *
 * @author Max Arulananthan
 * @since 1.0
 */
public class RuliiConfiguration {

    public RuliiConfiguration() {
        super();
    }

    /**
    /**
     * Creates an ObjectFactory bean.
     *
     * @param factory Spring's BeanFactory.
     * @return SpringObjectFactory instance if possible; default ObjectFactory otherwise.
     */
    @Bean(name = BeanNames.OBJECT_FACTORY_NAME)
    public ObjectFactory objectFactory(BeanFactory factory) {
        // Use Spring to create the Objects
        if (factory instanceof AutowireCapableBeanFactory) {
            return new SpringObjectFactory((AutowireCapableBeanFactory) factory);
        }
        // Use the default implementation
        return ObjectFactory.create();
    }

    @Bean(name = BeanNames.SCRIPT_ENGINE_NAME)
    public SpringElScriptEngine springElScriptEngine() {
        return new SpringElScriptEngine();
    }

    @Bean(name = BeanNames.SCRIPT_PROCESSOR_NAME)
    public SpringElScriptProcessor scriptProcessor(SpringElScriptEngine scriptEngine) {
        return new SpringElScriptProcessor(scriptEngine);
    }

    @Bean(name = BeanNames.GENERIC_CONVERTER)
    public ConverterAdapter genericSpringConverter() {
        return new ConverterAdapter();
    }

    @Bean(name = BeanNames.CONVERTER_REGISTRY)
    public ConverterRegistry converterRegistry(ConverterAdapter converterAdapter) {
        ConverterRegistry result = ConverterRegistry.create();
        result.register(converterAdapter);
        return result;
    }

    @Bean
    public SystemDefaultsInitializer systemDefaultsInitializer(ObjectFactory objectFactory,
                                                               SpringElScriptProcessor scriptProcessor,
                                                               ConverterRegistry registry) {
        return new SystemDefaultsInitializer(objectFactory, scriptProcessor, registry);
    }
}
