package org.algorithmx.rules.spring.test;

import org.algorithmx.rules.core.RuleSet;
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

    public RuleRegistrationTest() {
        super();
    }

    @Test
    public void test1() {
        Arrays.stream(ruleSetA.getRules()).forEach(rule -> System.err.println(rule));
    }
}
