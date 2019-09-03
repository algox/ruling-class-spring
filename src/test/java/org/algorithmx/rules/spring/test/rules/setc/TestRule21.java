package org.algorithmx.rules.spring.test.rules.setc;

import org.algorithmx.rules.annotation.Rule;

@Rule(name = "TestRule21")
public class TestRule21 {

    public TestRule21() {
        super();
    }

    public boolean when(String arg1) {
        return true;
    }

    public void then() {}

    public void then(Integer x) {}
}
