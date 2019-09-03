package org.algorithmx.rules.spring.annotation;

import org.algorithmx.rules.spring.RuleConfiguration;
import org.algorithmx.rules.spring.RuleRegistrar;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import({RuleRegistrar.class, RuleConfiguration.class})
public @interface EnableRules {

    @AliasFor("basePackages")
    String[] value() default {};

    @AliasFor("value")
    String[] basePackages() default {};
}
