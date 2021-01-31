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
import org.algorithmx.rulii.bind.match.BindingMatchingStrategy;
import org.algorithmx.rulii.bind.match.ParameterResolver;
import org.algorithmx.rulii.event.EventProcessor;
import org.algorithmx.rulii.script.ScriptProcessor;
import org.algorithmx.rulii.spring.script.SpringElScriptProcessor;
import org.algorithmx.rulii.text.MessageFormatter;
import org.algorithmx.rulii.text.MessageResolver;
import org.algorithmx.rulii.util.SystemDefaults;
import org.algorithmx.rulii.util.reflect.MethodResolver;
import org.algorithmx.rulii.util.reflect.ObjectFactory;

import java.time.Clock;
import java.util.Locale;

public class SpringEnvironmentSystemDefaults implements SystemDefaults {

    private final BindingMatchingStrategy matchingStrategy = BindingMatchingStrategy.create();
    private final ParameterResolver parameterResolver = ParameterResolver.create();
    private final MethodResolver methodResolver = MethodResolver.create();
    private final MessageResolver messageResolver = MessageResolver.create(new String[0]);
    private final MessageFormatter messageFormatter = MessageFormatter.create();
    private final ConverterRegistry converterRegistry;
    private final ObjectFactory objectFactory;
    private final ScriptProcessor scriptProcessor;

    public SpringEnvironmentSystemDefaults(ObjectFactory objectFactory,
                                           SpringElScriptProcessor scriptProcessor,
                                           ConverterRegistry registry) {
        super();
        this.objectFactory = objectFactory;
        this.scriptProcessor = scriptProcessor;
        this.converterRegistry = registry;
    }

    @Override
    public ObjectFactory createObjectFactory() {
        return this.objectFactory;
    }

    @Override
    public ScriptProcessor createScriptProcessor() {
        return this.scriptProcessor;
    }

    @Override
    public ConverterRegistry createConverterRegistry() {
        return this.converterRegistry;
    }

    @Override
    public MessageResolver createMessageResolver(String... baseNames) {
        // TODO : Add rulii-validators
        return baseNames != null && baseNames.length != 0 ? MessageResolver.create(baseNames) : this.messageResolver;
    }

    @Override
    public BindingMatchingStrategy createBindingMatchingStrategy() {
        return this.matchingStrategy;
    }

    @Override
    public ParameterResolver createParameterResolver() {
        return this.parameterResolver;
    }

    @Override
    public MethodResolver createMethodResolver() {
        return this.methodResolver;
    }

    @Override
    public MessageFormatter createMessageFormatter() {
        return this.messageFormatter;
    }

    @Override
    public EventProcessor createEventProcessor() {
        return EventProcessor.create();
    }

    @Override
    public Clock createClock() {
        return Clock.systemDefaultZone();
    }

    @Override
    public Locale createDefaultLocale() {
        return Locale.getDefault();
    }
}
