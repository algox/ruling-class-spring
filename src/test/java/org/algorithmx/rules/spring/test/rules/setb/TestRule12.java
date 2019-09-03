package org.algorithmx.rules.spring.test.rules.setb;

import org.algorithmx.rules.annotation.Rule;

@Rule(name = "TestRule12")
public class TestRule12 {

    public TestRule12() {
        super();
    }

    public boolean when(String arg1) {
        return true;
    }

    public void then() {}

    public void then(Integer x) {}
}
