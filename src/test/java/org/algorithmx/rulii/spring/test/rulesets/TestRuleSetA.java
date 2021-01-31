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

package org.algorithmx.rulii.spring.test.rulesets;

import org.algorithmx.rulii.annotation.PreCondition;
import org.algorithmx.rulii.annotation.RuleSet;
import org.algorithmx.rulii.annotation.Rules;
import org.algorithmx.rulii.core.action.ActionBuilder;
import org.algorithmx.rulii.core.rule.Rule;
import org.algorithmx.rulii.core.ruleset.RuleSetBuilder;
import org.algorithmx.rulii.util.RuleUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

@RuleSet
public class TestRuleSetA {

    @Autowired
    private Set<Rule<?>> rules;

    public TestRuleSetA() {
        super();
    }

    @PreCondition
    public boolean preCondition() {
        return true;
    }

    @Rules
    public void load(RuleSetBuilder builder) {
        builder.action(ActionBuilder.build(() -> System.err.println("XXX Start")));
        builder.rules(rules.stream().filter(RuleUtils
                .createPackageRuleFilter("org.algorithmx.rulii.spring.test.rules.seta"))
                .toArray(size -> new Rule[size]));
        builder.action(ActionBuilder.build(() -> System.err.println("XXX End")));
    }
}
