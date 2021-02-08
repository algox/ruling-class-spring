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

package org.algorithmx.rulii.spring.test;

import org.algorithmx.rulii.bind.Bindings;
import org.algorithmx.rulii.bind.convert.Converter;
import org.algorithmx.rulii.bind.convert.ConverterRegistry;
import org.algorithmx.rulii.core.context.RuleContextBuilder;
import org.algorithmx.rulii.core.rule.RuleBuilder;
import org.algorithmx.rulii.core.ruleset.RuleSet;
import org.algorithmx.rulii.core.ruleset.RuleSetBuilder;
import org.algorithmx.rulii.spring.test.rules.setc.TestRule21;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestRuleConfig.class})
public class RuleRegistrationTest {

    @Autowired
    private ConverterRegistry converterRegistry;
    @Autowired
    private RuleSet testRuleSetA;
    @Autowired
    private RuleSet testRuleSetB;

    public RuleRegistrationTest() {
        super();
    }

    @Test
    public void test1() {
        Assert.assertNotNull(converterRegistry);
        Assert.assertNotNull(testRuleSetA);
        Assert.assertNotNull(testRuleSetB);
    }

    @Test
    public void test2() {
        testRuleSetA.run(RuleContextBuilder.build(Bindings.create()));
        testRuleSetB.run(RuleContextBuilder.build(Bindings.create()));
    }

    @Test
    public void test3() {
        RuleSet testRuleSetC = RuleSetBuilder.with("testRuleSetC")
                            .rule(RuleBuilder.build(TestRule21.class))
                            .build();
        testRuleSetC.run(RuleContextBuilder.empty());
    }

    @Test
    public void test4() {
        Converter converter = converterRegistry.find(String.class, Long.class);
        System.err.println(converter.convert("12345678", Long.class));
    }
}
