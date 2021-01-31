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

import org.springframework.util.Assert;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpringElScriptEngineFactory implements ScriptEngineFactory {

    private static Map<String, String> properties = new HashMap<>();

    static {
        properties.put(ScriptEngine.ENGINE, "spring-el");
        properties.put(ScriptEngine.ENGINE_VERSION, "1.0");
        properties.put(ScriptEngine.LANGUAGE, "Spring Expression Language");
        properties.put(ScriptEngine.LANGUAGE_VERSION, "4.3.18");
        properties.put(ScriptEngine.NAME, "spring-el");
    }

    public SpringElScriptEngineFactory() {
        super();
    }

    @Override
    public String getEngineName() {
        return properties.get(ScriptEngine.ENGINE);
    }

    @Override
    public String getEngineVersion() {
        return properties.get(ScriptEngine.ENGINE_VERSION);
    }

    @Override
    public List<String> getExtensions() {
        return Arrays.asList("spel");
    }

    @Override
    public List<String> getMimeTypes() {
        return Arrays.asList("text/spel");
    }

    @Override
    public List<String> getNames() {
        return Arrays.asList(properties.get(ScriptEngine.NAME), "spel", "spring-el");
    }

    @Override
    public String getLanguageName() {
        return properties.get(ScriptEngine.LANGUAGE);
    }

    @Override
    public String getLanguageVersion() {
        return properties.get(ScriptEngine.LANGUAGE_VERSION);
    }

    @Override
    public Object getParameter(String key) {
        return properties.get(key);
    }

    @Override
    public String getMethodCallSyntax(String obj, String method, String... args) {
        Assert.notNull(obj, "obj cannot be null.");
        Assert.notNull(method, "method cannot be null.");
        StringBuilder result = new StringBuilder();
        result.append(obj + "." + method + "(");
        if (args != null) result.append(String.join(",", args));
        result.append(")");
        return result.toString();
    }

    @Override
    public String getOutputStatement(String toDisplay) {
        return "System.out.println(" + toDisplay + ");";
    }

    @Override
    public String getProgram(String... statements) {
        if (statements == null) return null;
        String separator = ";" + System.lineSeparator();
        return String.join(separator, statements) + separator;
    }

    @Override
    public ScriptEngine getScriptEngine() {
        return new SpringElScriptEngine();
    }
}
