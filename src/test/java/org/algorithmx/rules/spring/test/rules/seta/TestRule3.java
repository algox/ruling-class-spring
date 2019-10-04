package org.algorithmx.rules.spring.test.rules.seta;

import org.algorithmx.rules.annotation.Given;
import org.algorithmx.rules.annotation.Rule;

@Rule(name = "TestRule3")
public class TestRule3 {

    public TestRule3() {
        super();
    }

    @Given
    public boolean when() {
        return true;
    }
}
