package org.algorithmx.rules.spring.test.rules.seta;

import org.algorithmx.rules.annotation.Given;
import org.algorithmx.rules.annotation.Rule;
import org.algorithmx.rules.annotation.Then;

@Rule(name = "TestRule1")
public class TestRule1 {

    public TestRule1() {
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
