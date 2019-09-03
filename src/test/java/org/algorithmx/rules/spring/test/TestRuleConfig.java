package org.algorithmx.rules.spring.test;

import org.algorithmx.rules.spring.RuleSetFactoryBean;
import org.algorithmx.rules.spring.annotation.EnableRules;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRules("org.algorithmx.rules.spring.test")
public class TestRuleConfig {

    public TestRuleConfig() {
        super();
    }

    @Bean(name="ruleSetA")
    public RuleSetFactoryBean ruleSetA() {
        RuleSetFactoryBean result = new RuleSetFactoryBean("ruleSetA", "org.algorithmx.rules.spring.test.rules.seta");
        return result;
    }

    @Bean(name="ruleSetB")
    public RuleSetFactoryBean ruleSetB() {
        RuleSetFactoryBean result = new RuleSetFactoryBean("ruleSetB", "org.algorithmx.rules.spring.test.rules.setb");
        return result;
    }
}
