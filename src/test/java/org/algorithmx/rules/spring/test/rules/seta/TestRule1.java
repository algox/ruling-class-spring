package org.algorithmx.rules.spring.test.rules.seta;

import org.algorithmx.rules.annotation.Rule;

@Rule(name = "TestRule1")
public class TestRule1 {

    public TestRule1() {
        super();
    }

    public boolean when(String arg1, String arg2) {
        return true;
    }

    public void then() {}

    public void then(Integer x) {}
}
