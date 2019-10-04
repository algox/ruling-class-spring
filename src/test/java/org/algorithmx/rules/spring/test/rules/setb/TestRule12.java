package org.algorithmx.rules.spring.test.rules.setb;

import org.algorithmx.rules.annotation.Given;
import org.algorithmx.rules.annotation.Rule;
import org.algorithmx.rules.annotation.Then;

@Rule(name = "TestRule12")
public class TestRule12 {

    public TestRule12() {
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
