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

import org.algorithmx.rules.core.Rule;
import org.algorithmx.rules.core.RuleFactory;
import org.algorithmx.rules.core.RuleSet;
import org.algorithmx.rules.core.impl.DefaultRuleSet;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.NamedBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * FactoryBean to create RuleSet easily. Specify the base package and let Spring group all the Rules under that
 * base package into a RuleSet.
 *
 * @author Max Arulananthan
 * @since 1.0
 */
public class RuleSetFactoryBean implements FactoryBean<RuleSet>, NamedBean {

    private final String name;
    private final String basePackageName;
    private String description;
    private Comparator<Rule> comparator;

    @Autowired
    private RuleFactory ruleFactory;
    @Autowired(required = false)
    private List<Rule> rules;

    private RuleSet result;
    private boolean initialized = false;

    public RuleSetFactoryBean(String name, String basePackageName) {
        this(name, basePackageName, null);
    }

    public RuleSetFactoryBean(String name, String basePackageName, String description) {
        super();
        Assert.isTrue(StringUtils.hasText(name), "Invalid RuleSet name [" + name + "]");
        Assert.isTrue(StringUtils.hasText(basePackageName), "basePackageName must be defined [" + basePackageName + "]");
        this.name = name;
        this.basePackageName = basePackageName;
        this.description = description;
    }

    @PostConstruct
    private void init() {
        this.result = build();
        this.initialized = true;
    }

    /**
     * Specify the RuleSet description.
     *
     * @param description RuleSet description.
     * @return this (for fluency)
     */
    public RuleSetFactoryBean description(String description) {
        Assert.isTrue(!initialized, "Bean already initialized.");
        this.description = description;
        return this;
    }

    /**
     * Override the autowired RuleFactory with your own.
     *
     * @param ruleFactory desired RuleFactory to use.
     * @return this (for fluency)
     */
    public RuleSetFactoryBean ruleFactory(RuleFactory ruleFactory) {
        Assert.isTrue(!initialized, "Bean already initialized.");
        this.ruleFactory = ruleFactory;
        return this;
    }

    /**
     * Specify a comparator to order the Rules within the RuleSet.
     *
     * @param comparator desired order.
     * @return this (for fluency)
     */
    public RuleSetFactoryBean order(Comparator<Rule> comparator) {
        Assert.isTrue(!initialized, "Bean already initialized.");
        this.comparator = comparator;
        return this;
    }

    @Override
    public RuleSet getObject() throws Exception {
        return result;
    }

    @Override
    public Class<?> getObjectType() {
        return RuleSet.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public String getBeanName() {
        return name;
    }

    /**
     * Builds the RuleSet with the given properties.
     *
     * @return a new RuleSet.
     */
    protected RuleSet build() {
        RuleSet result = new DefaultRuleSet(name, description, ruleFactory);
        List<Rule> candidates = new ArrayList<>();

        if (rules != null) {
            rules.stream()
                    // Filter out all the Rules based on package
                    .filter(rule -> rule.getRuleDefinition().getRulingClass().getPackage().getName().startsWith(basePackageName))
                    .forEach(rule -> candidates.add(rule));

            // Sort them if needed
            if (comparator != null) {
                Collections.sort(candidates, comparator);
            }

            // Add to RuleSet
            candidates.stream().forEach(rule -> result.add(rule));
        }

        return result;
    }
}
