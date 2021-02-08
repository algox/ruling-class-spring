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

package org.algorithmx.rulii.spring.script;

import org.algorithmx.rulii.bind.Binding;
import org.algorithmx.rulii.bind.Bindings;
import org.algorithmx.rulii.script.EvaluationException;
import org.algorithmx.rulii.script.ScriptEngineBindings;
import org.algorithmx.rulii.script.ScriptProcessor;
import org.algorithmx.rulii.spring.util.ResolvableTypeDescriptor;
import org.springframework.core.ResolvableType;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.expression.ConstructorResolver;
import org.springframework.expression.MethodFilter;
import org.springframework.expression.MethodResolver;
import org.springframework.expression.OperatorOverloader;
import org.springframework.expression.PropertyAccessor;
import org.springframework.expression.TypeComparator;
import org.springframework.expression.TypeConverter;
import org.springframework.expression.TypeLocator;
import org.springframework.expression.TypedValue;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.Assert;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.lang.reflect.Method;

public class SpringElScriptProcessor implements ScriptProcessor {

    public static final String LANGUAGE_NAME = "spring-el";

    private final SpringElScriptEngine engine;
    private StandardEvaluationContext context = new StandardEvaluationContext();

    public SpringElScriptProcessor() {
        this(new SpringElScriptEngine());
    }

    public SpringElScriptProcessor(SpringElScriptEngine engine) {
        super();
        Assert.notNull(engine, "engine cannot be null.");
        this.engine = engine;
    }

    public final static String getLanguageName() {
        return LANGUAGE_NAME;
    }

    @Override
    public Object evaluate(String script, Bindings bindings) throws EvaluationException {
        return evaluate(script, createContext(bindings));
    }

    @Override
    public Object evaluate(String script, ScriptContext context) throws EvaluationException {
        Assert.notNull(script, "script cannot be null.");
        Assert.notNull(context, "context cannot be null.");

        try {
            return this.engine.eval(script, context);
        } catch (ScriptException e) {
            throw new EvaluationException(script, "Script Error trying to evaluate [" + script + "]", e);
        }
    }

    @Override
    public ScriptContext createContext(Bindings bindings) {
        return new DelegatingEvaluationContext(extractRoot(bindings), context, new ScriptEngineBindings(bindings));
    }

    protected TypedValue extractRoot(Bindings bindings) {
        Binding binding = bindings.getBinding(SpringElConstants.ROOT_OBJECT_NAME);
        TypedValue result = null;

        if (binding != null) {
            TypeDescriptor descriptor = new ResolvableTypeDescriptor(ResolvableType.forType(binding.getType()));
            result = new TypedValue(binding.getValue(), descriptor);
        }

        return result;
    }

    public void clear() {
        this.context = new StandardEvaluationContext();
    }

    public void addPropertyAccessor(PropertyAccessor accessor) {
        getContext().addPropertyAccessor(accessor);
    }

    public boolean removePropertyAccessor(PropertyAccessor accessor) {
        return getContext().removePropertyAccessor(accessor);
    }

    public void addConstructorResolver(ConstructorResolver resolver) {
        getContext().addConstructorResolver(resolver);
    }

    public boolean removeConstructorResolver(ConstructorResolver resolver) {
        return getContext().removeConstructorResolver(resolver);
    }

    public void addMethodResolver(MethodResolver resolver) {
        getContext().addMethodResolver(resolver);
    }

    public boolean removeMethodResolver(MethodResolver methodResolver) {
        return getContext().removeMethodResolver(methodResolver);
    }

    public void setTypeLocator(TypeLocator typeLocator) {
        getContext().setTypeLocator(typeLocator);
    }

    public void setTypeConverter(TypeConverter typeConverter) {
        getContext().setTypeConverter(typeConverter);
    }

    public void setTypeComparator(TypeComparator typeComparator) {
        getContext().setTypeComparator(typeComparator);
    }

    public void setOperatorOverloader(OperatorOverloader operatorOverloader) {
        getContext().setOperatorOverloader(operatorOverloader);
    }

    public void registerFunction(String name, Method method) {
        getContext().registerFunction(name, method);
    }

    public void registerMethodFilter(Class<?> type, MethodFilter filter) {
        getContext().registerMethodFilter(type, filter);
    }

    protected StandardEvaluationContext getContext() {
        return context;
    }

    @Override
    public ScriptEngine getEngine() {
        return engine;
    }
}
