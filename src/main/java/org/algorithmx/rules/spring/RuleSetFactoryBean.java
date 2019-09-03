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

public class RuleSetFactoryBean implements FactoryBean<RuleSet>, NamedBean {

    private final String name;
    private final String basePackageName;
    private String description;
    private Comparator<Rule> comparator;

    @Autowired
    private RuleFactory ruleFactory;
    @Autowired
    private List<Rule> rules;

    private RuleSet result;

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
    }

    public RuleSetFactoryBean description(String description) {
        this.description = description;
        return this;
    }

    public RuleSetFactoryBean ruleFactory(RuleFactory ruleFactory) {
        this.ruleFactory = ruleFactory;
        return this;
    }

    public RuleSetFactoryBean order(Comparator<Rule> comparator) {
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

    protected RuleSet build() {
        RuleSet result = new DefaultRuleSet(name, description, ruleFactory);
        List<Rule> candidates = new ArrayList<>();

        if (rules != null) {
            rules.stream()
                    .filter(rule -> rule.getRuleDefinition().getRulingClass().getPackage().getName().startsWith(basePackageName))
                    .forEach(rule -> candidates.add(rule));

            if (comparator != null) {
                Collections.sort(candidates, comparator);
            }

            candidates.stream().forEach(rule -> result.add(rule));
        }

        return result;
    }
}
