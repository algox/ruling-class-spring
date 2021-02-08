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

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelCompilerMode;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.util.Assert;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import javax.script.SimpleScriptContext;
import java.io.IOException;
import java.io.Reader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SpringElScriptEngine implements ScriptEngine {

    private static final Map<String, Expression> EXPRESSION_CACHE = new ConcurrentHashMap<>();

    private final ExpressionParser parser;

    private ScriptContext context = new SimpleScriptContext();

    public SpringElScriptEngine() {
        this(new SpelParserConfiguration(SpelCompilerMode.MIXED, null));
    }

    public SpringElScriptEngine(SpelParserConfiguration configuration) {
        super();
        Assert.notNull(configuration, "configuration cannot be null");
        this.parser = new SpelExpressionParser(new SpelParserConfiguration(SpelCompilerMode.MIXED, null));
    }

    @Override
    public Object eval(String script, ScriptContext context) throws ScriptException {
        return evalInternal(script, createEvaluationContext(context));
    }

    public Object evalInternal(String script, EvaluationContext context) throws ScriptException {
        Assert.notNull(script, "script cannot be null");
        Assert.notNull(context, "context cannot be null");

        Expression expression = EXPRESSION_CACHE.get(script);

        // Make sure to cache the expressions
        if (expression == null) {
            expression = parser.parseExpression(script);
            EXPRESSION_CACHE.put(script, expression);
        }

        try {
            return expression.getValue(context);
        } catch (EvaluationException e) {
            throw new ScriptException(e);
        }
    }

    protected EvaluationContext createEvaluationContext(ScriptContext context) {
        if (context instanceof EvaluationContext) return (EvaluationContext) context;
        return new DelegatingEvaluationContext(context.getBindings(ScriptContext.GLOBAL_SCOPE));
    }

    @Override
    public Object eval(String script, Bindings bindings) throws ScriptException {
        return evalInternal(script, new DelegatingEvaluationContext(bindings));
    }

    @Override
    public Object eval(String script) throws ScriptException {
        return eval(script, new SimpleBindings());
    }

    @Override
    public Object eval(Reader reader) throws ScriptException {
        String script = readScript(reader);
        return eval(script);
    }

    @Override
    public Object eval(Reader reader, Bindings bindings) throws ScriptException {
        String script = readScript(reader);
        return eval(script, bindings);
    }

    @Override
    public Object eval(Reader reader, ScriptContext context) throws ScriptException {
        String script = readScript(reader);
        return eval(script, context);
    }

    @Override
    public void put(String key, Object value) {
        getBindings(ScriptContext.ENGINE_SCOPE).put(key, value);
    }

    @Override
    public Object get(String key) {
        return getBindings(ScriptContext.ENGINE_SCOPE).get(key);
    }

    @Override
    public Bindings getBindings(int scope) {
        return this.context.getBindings(scope);
    }

    @Override
    public void setBindings(Bindings bindings, int scope) {
        this.context.setBindings(bindings, scope);
    }

    @Override
    public Bindings createBindings() {
        return new SimpleBindings();
    }

    @Override
    public ScriptContext getContext() {
        return context;
    }

    @Override
    public void setContext(ScriptContext context) {
        Assert.notNull(context, "context cannot be null.");
        this.context = context;
    }

    @Override
    public ScriptEngineFactory getFactory() {
        return null;
    }

    private String readScript(Reader reader) throws ScriptException {
        StringBuilder result = new StringBuilder();
        char[] data = new char[4 * 1024];
        int read;

        try {
            while ((read = reader.read(data, 0, data.length)) != -1) {
                result.append(data, 0, read);
            }

            reader.close();
        } catch (IOException e) {
            throw new ScriptException(e);
        }

        return result.toString();
    }

    @Override
    public String toString() {
        return "SpringElScriptEngine{}";
    }
}
