package org.algorithmx.rules.spring;

import org.algorithmx.rules.core.*;
import org.algorithmx.rules.core.impl.DefaultRuleFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RuleConfiguration {

    public RuleConfiguration() {
        super();
    }

    @Bean(name = BeanNames.OBJECT_FACTORY_NAME)
    public ObjectFactory objectFactory(BeanFactory factory) {
        if (factory instanceof AutowireCapableBeanFactory) {
            return new SpringObjectFactory((AutowireCapableBeanFactory) factory);
        }

        return ObjectFactory.create();
    }

    @Bean(name = BeanNames.RULE_FACTORY_NAME)
    public RuleFactory ruleFactory(@Qualifier("defaultRuleObjectFactory") ObjectFactory objectFactory) {
        return new DefaultRuleFactory(objectFactory);
    }

    @Bean(name = BeanNames.RULE_ENGINE_NAME)
    public RuleEngine ruleEngine() {
        return RuleEngine.defaultRuleEngine();
    }

    @Bean(name = BeanNames.PARAMETER_RESOLVER_NAME)
    public ParameterResolver parameterResolver() {
        return ParameterResolver.defaultParameterResolver();
    }

    @Bean(name = BeanNames.BINDABLE_METHOD_EEXECUTOR)
    public BindableMethodExecutor bindableMethodExecutor() {
        return BindableMethodExecutor.defaultBindableMethodExecutor();
    }
}
