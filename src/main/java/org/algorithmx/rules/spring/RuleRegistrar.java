package org.algorithmx.rules.spring;

import org.algorithmx.rules.core.Rule;
import org.algorithmx.rules.core.UnrulyException;
import org.algorithmx.rules.model.RuleDefinition;
import org.algorithmx.rules.spring.annotation.EnableRules;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;

public class RuleRegistrar implements ImportBeanDefinitionRegistrar, BeanClassLoaderAware {

    private final IndependentCandidateClassPathScanner scanner = new IndependentCandidateClassPathScanner(false);
    private final String ruleFactoryBeanName;
    private ClassLoader beanClassLoader;

    public RuleRegistrar() {
        this(BeanNames.RULE_FACTORY_NAME);
    }

    public RuleRegistrar(String ruleFactoryBeanName) {
        super();
        Assert.notNull(ruleFactoryBeanName, "ruleFactoryBeanName cannot be null.");
        this.ruleFactoryBeanName = ruleFactoryBeanName;
        scanner.addIncludeFilter(new AnnotationTypeFilter(org.algorithmx.rules.annotation.Rule.class));
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        String[] basePackages = getBasePackages(importingClassMetadata);

        if (basePackages == null || basePackages.length == 0) return;;

        Arrays.stream(basePackages).forEach((basePackage) -> scanPackage(basePackage, registry));
    }

    private String[] getBasePackages(AnnotationMetadata metadata) {
        MultiValueMap<String, Object> attributes = metadata.getAllAnnotationAttributes(EnableRules.class.getName());

        if (attributes == null) return null;

        String[] result = null;

        if (attributes.containsKey("basePackages")) {
            result = (String[]) attributes.getFirst("basePackages");
        }

        return result;
    }

    private void scanPackage(String basePackage, BeanDefinitionRegistry registry) {
        System.err.println("XXX Scanning package [" + basePackage + "]");
        for (BeanDefinition beanDefinition : scanner.findCandidateComponents(basePackage)) {
            System.err.println("XXX Rule Candidate [" + beanDefinition.getBeanClassName() + "]");
            Class<?> ruleClass = loadClass(beanDefinition.getBeanClassName(),  beanClassLoader);
            RuleDefinition definition = RuleDefinition.load(ruleClass);
            GenericBeanDefinition ruleBeanDefinition = new GenericBeanDefinition();
            ConstructorArgumentValues args = new ConstructorArgumentValues();

            ruleBeanDefinition.setBeanClass(Rule.class);
            args.addGenericArgumentValue(definition);
            ruleBeanDefinition.setConstructorArgumentValues(args);
            ruleBeanDefinition.setFactoryBeanName(ruleFactoryBeanName);
            ruleBeanDefinition.setFactoryMethodName("rule");
            System.err.println("XXX Registering Rule [" + ruleClass.getName() + "] as [" + definition.getName() + "]");
            registry.registerBeanDefinition(definition.getName(), ruleBeanDefinition);
        }
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.beanClassLoader = classLoader;
    }

    protected Class<?> loadClass(String ruleClassName, ClassLoader classLoader) {
        try {
            return Class.forName(ruleClassName, true, classLoader);
        } catch (ClassNotFoundException e) {
            throw new UnrulyException("Unable to load Rule Class [" + ruleClassName + "] using Class Loader [" + classLoader + "]");
        }
    }
}
