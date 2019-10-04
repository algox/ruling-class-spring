package org.algorithmx.rules.spring.test;

import org.algorithmx.rules.core.RuleSet;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestRuleConfig.class})
public class RuleRegistrationTest {

    @Autowired
    private RuleSet ruleSetA;
    @Autowired
    private RuleSet ruleSetB;

    public RuleRegistrationTest() {
        super();
    }

    @Test
    public void test1() {
        Arrays.stream(ruleSetA.getRules()).forEach(rule -> Assert.assertTrue(rule.getRuleDefinition().getRulingClass()
                .getPackage().getName().startsWith("org.algorithmx.rules.spring.test.rules.seta")));
        Arrays.stream(ruleSetB.getRules()).forEach(rule -> Assert.assertTrue(rule.getRuleDefinition().getRulingClass()
                .getPackage().getName().startsWith("org.algorithmx.rules.spring.test.rules.setb")));
    }
}
