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

package org.algorithmx.rulii.spring.test.script;

import org.algorithmx.rulii.bind.Bindings;
import org.algorithmx.rulii.script.ScriptProcessor;
import org.algorithmx.rulii.spring.script.SpringElScriptProcessor;
import org.junit.Assert;
import org.junit.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class SpringElScriptEngineTest {

    public SpringElScriptEngineTest() {
        super();
    }

    @Test
    public void test1() {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine engine = scriptEngineManager.getEngineByName("spring-el");
        Assert.assertNotNull(engine);
    }

    @Test
    public void test2() {
        ScriptProcessor processor = ScriptProcessor.create("spring-el");
        Assert.assertNotNull(processor);
    }

    @Test
    public void test3() {
        ScriptProcessor processor = ScriptProcessor.create("spring-el");
        Object result = processor.evaluate("new String('hello world').toUpperCase()", Bindings.create());
        Assert.assertTrue("HELLO WORLD".equals(result));
    }

    @Test
    public void test4() {
        ScriptProcessor processor = new SpringElScriptProcessor();
        Object result = processor.evaluate("new String('hello world').toUpperCase()", Bindings.create());
        Assert.assertTrue("HELLO WORLD".equals(result));
    }

    @Test
    public void test5() {
        Bindings bindings = Bindings.create()
                                .bind("x", "Hello World");

        ScriptProcessor processor = new SpringElScriptProcessor();
        Object result = processor.evaluate("#x.toUpperCase()", bindings);
        Assert.assertTrue("HELLO WORLD".equals(result));
    }
}
