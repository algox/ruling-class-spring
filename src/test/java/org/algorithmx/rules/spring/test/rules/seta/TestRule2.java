package org.algorithmx.rules.spring.test.rules.seta;

import org.algorithmx.rules.annotation.Rule;

@Rule(name = "TestRule2")
public class TestRule2 {

    public TestRule2() {
        super();
    }

    public boolean when(String arg1) {
        return true;
    }

    public void then() {}

    public void then(Integer x) {}
}
