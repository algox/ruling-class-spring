/**
 * This software is licensed under the Apache 2 license, quoted below.
 *
 * Copyright (c) 2019, algorithmx.org (dev@algorithmx.org)
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

/**
 * Spring Registrar responsible for scanning and registering all the Rules(@Rule) in the given base packages.
 *
 * @author Max Arulananthan
 * @since 1.0
 */
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

    /**
     * Retrieves all the base packages from the EnableRules annotation.
     *
     * @param metadata scanned data.
     * @return all the applicable base packages.
     */
    private String[] getBasePackages(AnnotationMetadata metadata) {
        MultiValueMap<String, Object> attributes = metadata.getAllAnnotationAttributes(EnableRules.class.getName());

        if (attributes == null) return null;

        String[] result = null;

        if (attributes.containsKey("basePackages")) {
            result = (String[]) attributes.getFirst("basePackages");
        }

        return result;
    }

    /**
     * Scans for classes annotated with @Rule and registers them as a Bean.
     *
     * @param basePackage base package to scan.
     * @param registry bean registry.
     */
    private void scanPackage(String basePackage, BeanDefinitionRegistry registry) {
        for (BeanDefinition beanDefinition : scanner.findCandidateComponents(basePackage)) {
            Class<?> ruleClass = loadClass(beanDefinition.getBeanClassName(),  beanClassLoader);
            RuleDefinition definition = RuleDefinition.load(ruleClass);
            GenericBeanDefinition ruleBeanDefinition = new GenericBeanDefinition();
            ConstructorArgumentValues args = new ConstructorArgumentValues();

            ruleBeanDefinition.setBeanClass(Rule.class);
            args.addGenericArgumentValue(definition);
            ruleBeanDefinition.setConstructorArgumentValues(args);
            ruleBeanDefinition.setFactoryBeanName(ruleFactoryBeanName);
            ruleBeanDefinition.setFactoryMethodName("rule");
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
