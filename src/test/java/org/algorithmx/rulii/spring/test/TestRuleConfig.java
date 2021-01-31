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

import org.algorithmx.rulii.core.rule.RuleBuilder;
import org.algorithmx.rulii.core.ruleset.RuleSet;
import org.algorithmx.rulii.core.ruleset.RuleSetBuilder;
import org.algorithmx.rulii.spring.annotation.EnableRulii;
import org.algorithmx.rulii.spring.test.rules.setb.TestRule11;
import org.algorithmx.rulii.spring.test.rules.setb.TestRule12;
import org.algorithmx.rulii.util.SystemDefaultsHolder;
import org.algorithmx.rulii.util.reflect.ObjectFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableRulii("org.algorithmx.rulii.spring.test")
public class TestRuleConfig {

    public TestRuleConfig() {
        super();
    }

    @Bean(name = "testRuleSetB")
    public RuleSet createRuleSetB() {
        ObjectFactory factory = SystemDefaultsHolder.getInstance().getDefaults().createObjectFactory();
        return RuleSetBuilder.with("RuleSetB")
                .rule(RuleBuilder.build(TestRule11.class))
                .rule(RuleBuilder.build(TestRule12.class))
                .build();
    }

    @Bean
    public Person person() {
        return new Person("Michael", "Jordan");
    }
}
