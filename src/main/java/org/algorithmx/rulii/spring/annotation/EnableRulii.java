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

package org.algorithmx.rulii.spring.annotation;

import org.algorithmx.rulii.spring.RuleRegistrar;
import org.algorithmx.rulii.spring.RuliiConfigurer;
import org.algorithmx.rulii.spring.script.SpringElScriptProcessor;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Enables annotation driven Rule management.
 *
 * @author Max Arulananthan
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import({RuliiConfigurer.class, RuleRegistrar.class})
public @interface EnableRulii {

    /**
     * Base packages to scan for Rules.
     *
     * @return base package names.
     */
    @AliasFor("basePackages")
    String[] value() default {};

    /**
     * Base packages to scan for Rules.
     *
     * @return base package names.
     */
    @AliasFor("value")
    String[] basePackages() default {};

    String scriptLanguage() default SpringElScriptProcessor.LANGUAGE_NAME;

    String[] messageSources() default {};
}
