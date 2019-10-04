package org.algorithmx.rules.spring.test.rules.setc;

import org.algorithmx.rules.annotation.Given;
import org.algorithmx.rules.annotation.Rule;
import org.algorithmx.rules.annotation.Then;

@Rule(name = "TestRule21")
public class TestRule21 {

    public TestRule21() {
        super();
    }

    @Given
    public boolean when(String arg1) {
        return true;
    }

    @Then
    public void then() {}

    @Then
    public void then(Integer x) {}
}
