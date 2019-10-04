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

import org.algorithmx.rules.bind.ParameterResolver;
import org.algorithmx.rules.core.*;
import org.algorithmx.rules.core.impl.DefaultRuleFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Main Rule configuration. Defines the ObjectFactory, RuleFactory, RuleEngine, ParameterResolver and MethodExecutor beans.
 *
 * @author Max Arulananthan
 * @since 1.0
 */
@Configuration
public class RuleConfiguration {

    public RuleConfiguration() {
        super();
    }

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

    /**
     * Creates a RuleFactory bean.
     *
     * @param objectFactory objectFactory instance.
     * @return RuleFactory bean.
     */
    @Bean(name = BeanNames.RULE_FACTORY_NAME)
    public RuleFactory ruleFactory(@Qualifier("defaultRuleObjectFactory") ObjectFactory objectFactory) {
        return new DefaultRuleFactory(objectFactory);
    }

    /**
     * Creates a RuleEngine bean.
     *
     * @return RuleEngine bean.
     */
    @Bean(name = BeanNames.RULE_ENGINE_NAME)
    public RuleEngine ruleEngine() {
        return RuleEngine.defaultRuleEngine();
    }

    /**
     * Creates a ParameterResolver bean.
     *
     * @return ParameterResolver bean.
     */
    @Bean(name = BeanNames.PARAMETER_RESOLVER_NAME)
    public ParameterResolver parameterResolver() {
        return ParameterResolver.defaultParameterResolver();
    }

    /**
     * Creates a BindableMethodExecutor bean.
     * @return BindableMethodExecutor beam.
     */
    @Bean(name = BeanNames.BINDABLE_METHOD_EEXECUTOR)
    public BindableMethodExecutor bindableMethodExecutor() {
        return BindableMethodExecutor.defaultBindableMethodExecutor();
    }
}
