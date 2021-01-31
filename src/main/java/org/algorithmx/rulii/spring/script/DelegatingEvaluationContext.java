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

import org.springframework.expression.BeanResolver;
import org.springframework.expression.ConstructorResolver;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.MethodResolver;
import org.springframework.expression.OperatorOverloader;
import org.springframework.expression.PropertyAccessor;
import org.springframework.expression.TypeComparator;
import org.springframework.expression.TypeConverter;
import org.springframework.expression.TypeLocator;
import org.springframework.expression.TypedValue;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.Assert;

import javax.script.Bindings;
import javax.script.ScriptContext;
import java.io.Reader;
import java.io.Writer;
import java.util.List;

class DelegatingEvaluationContext implements EvaluationContext, ScriptContext {

    private final TypedValue root;
    private final EvaluationContext context;
    private final Bindings bindings;

    DelegatingEvaluationContext(Bindings bindings) {
        this(null, new StandardEvaluationContext(), bindings);
    }

    DelegatingEvaluationContext(TypedValue root, EvaluationContext context, Bindings bindings) {
        super();
        Assert.notNull(context, "context cannot be null.");
        Assert.notNull(bindings, "bindings cannot be null.");
        this.root = root;
        this.context = context;
        this.bindings = bindings;
    }

    @Override
    public TypedValue getRootObject() {
        return root;
    }

    @Override
    public List<PropertyAccessor> getPropertyAccessors() {
        return context.getPropertyAccessors();
    }

    @Override
    public List<ConstructorResolver> getConstructorResolvers() {
        return context.getConstructorResolvers();
    }

    @Override
    public List<MethodResolver> getMethodResolvers() {
        return context.getMethodResolvers();
    }

    @Override
    public BeanResolver getBeanResolver() {
        return context.getBeanResolver();
    }

    @Override
    public TypeLocator getTypeLocator() {
        return context.getTypeLocator();
    }

    @Override
    public TypeConverter getTypeConverter() {
        return context.getTypeConverter();
    }

    @Override
    public TypeComparator getTypeComparator() {
        return context.getTypeComparator();
    }

    @Override
    public OperatorOverloader getOperatorOverloader() {
        return context.getOperatorOverloader();
    }

    @Override
    public Object lookupVariable(String name) {
        return this.bindings.get(name);
    }

    @Override
    public void setVariable(String name, Object value) {
        this.bindings.put(name, value);
    }

    public EvaluationContext getTargetContext() {
        return context;
    }

    public Bindings getBindings() {
        return bindings;
    }

    @Override
    public void setBindings(Bindings bindings, int scope) {
        throw new UnsupportedOperationException("SpringElScriptEngine does not support this operation.");
    }

    @Override
    public Bindings getBindings(int scope) {
        throw new UnsupportedOperationException("SpringElScriptEngine does not support this operation.");
    }

    @Override
    public void setAttribute(String name, Object value, int scope) {
        throw new UnsupportedOperationException("SpringElScriptEngine does not support this operation.");
    }

    @Override
    public Object getAttribute(String name, int scope) {
        throw new UnsupportedOperationException("SpringElScriptEngine does not support this operation.");
    }

    @Override
    public Object removeAttribute(String name, int scope) {
        throw new UnsupportedOperationException("SpringElScriptEngine does not support this operation.");
    }

    @Override
    public Object getAttribute(String name) {
        throw new UnsupportedOperationException("SpringElScriptEngine does not support this operation.");
    }

    @Override
    public int getAttributesScope(String name) {
        throw new UnsupportedOperationException("SpringElScriptEngine does not support this operation.");
    }

    @Override
    public Writer getWriter() {
        throw new UnsupportedOperationException("SpringElScriptEngine does not support this operation.");
    }

    @Override
    public Writer getErrorWriter() {
        throw new UnsupportedOperationException("SpringElScriptEngine does not support this operation.");
    }

    @Override
    public void setWriter(Writer writer) {
        throw new UnsupportedOperationException("SpringElScriptEngine does not support this operation.");
    }

    @Override
    public void setErrorWriter(Writer writer) {
        throw new UnsupportedOperationException("SpringElScriptEngine does not support this operation.");
    }

    @Override
    public Reader getReader() {
        throw new UnsupportedOperationException("SpringElScriptEngine does not support this operation.");
    }

    @Override
    public void setReader(Reader reader) {
        throw new UnsupportedOperationException("SpringElScriptEngine does not support this operation.");
    }

    @Override
    public List<Integer> getScopes() {
        throw new UnsupportedOperationException("SpringElScriptEngine does not support this operation.");
    }
}
