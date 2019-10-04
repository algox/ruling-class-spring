package org.algorithmx.rules.spring.test.rules.setb;

import org.algorithmx.rules.annotation.Given;
import org.algorithmx.rules.annotation.Rule;
import org.algorithmx.rules.annotation.Then;

@Rule(name = "TestRule11")
public class TestRule11 {

    public TestRule11() {
        super();
    }

    @Given
    public boolean when(String arg1, String arg2) {
        return true;
    }

    @Then
    public void then() {}

    @Then
    public void then(Integer x) {}
}
