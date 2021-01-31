/*
 * This software is licensed under the Apache 2 license, quoted below.
 *
 * Copyright (c) 1999-2021, Algorithmx Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.algorithmx.rulii.spring.test.rules.setb;

import org.algorithmx.rulii.annotation.Given;
import org.algorithmx.rulii.annotation.Rule;
import org.algorithmx.rulii.annotation.Then;

@Rule(name = "testRule12")
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
