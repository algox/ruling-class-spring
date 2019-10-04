package org.algorithmx.rules.spring.test.rules.seta;

import org.algorithmx.rules.annotation.Given;
import org.algorithmx.rules.annotation.Rule;
import org.algorithmx.rules.annotation.Then;

@Rule(name = "TestRule2")
public class TestRule2 {

    public TestRule2() {
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
