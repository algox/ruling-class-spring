package org.algorithmx.rules.spring.test.rules.setb;

import org.algorithmx.rules.annotation.Rule;

@Rule(name = "TestRule11")
public class TestRule11 {

    public TestRule11() {
        super();
    }

    public boolean when(String arg1, String arg2) {
        return true;
    }

    public void then() {}

    public void then(Integer x) {}
}
