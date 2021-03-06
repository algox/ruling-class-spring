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

package org.algorithmx.rulii.spring;

import org.algorithmx.rulii.annotation.Rule;
import org.algorithmx.rulii.annotation.RuleSet;
import org.algorithmx.rulii.core.rule.RuleBuilder;
import org.algorithmx.rulii.core.ruleset.RuleSetBuilder;
import org.algorithmx.rulii.spring.annotation.EnableRulii;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;

/**
 * Spring Registrar responsible for scanning and registering all the Rule(@Rule)/RuleSets(@RuleSet) in the given base packages.
 *
 * @author Max Arulananthan
 * @since 1.0
 */
public class RuleRegistrar implements ImportBeanDefinitionRegistrar, BeanFactoryAware {

    private static final BeanNameGenerator BEAN_NAME_GENERATOR = new AnnotationBeanNameGenerator();

    private final IndependentCandidateClassPathScanner scanner = new IndependentCandidateClassPathScanner(false);
    private BeanFactory beanFactory;

    public RuleRegistrar() {
        super();
        scanner.addIncludeFilter(new AnnotationTypeFilter(Rule.class));
        scanner.addIncludeFilter(new AnnotationTypeFilter(RuleSet.class));
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        MultiValueMap<String, Object> attributes = importingClassMetadata.getAllAnnotationAttributes(EnableRulii.class.getName());
        if (attributes == null) return;

        String scriptLanguage = getAttribute(attributes, "scriptLanguage");
        String[] messageSources = getAttributes(attributes, "messageSources");

        // Register MetaInfo Bean
        registerMetaInfoBean(scriptLanguage, messageSources, registry);

        String[] basePackages = getAttributes(attributes, "basePackages");
        if (basePackages == null || basePackages.length == 0) return;;
        Arrays.stream(basePackages).forEach((basePackage) -> scanPackage(basePackage, registry));
    }

    /**
     T
     *
     * @param basePackage base package to scan.
     * @param registry bean registry.
     */
    private void scanPackage(String basePackage, BeanDefinitionRegistry registry) {

        for (BeanDefinition beanDefinition : scanner.findCandidateComponents(basePackage)) {
            if (!(beanDefinition instanceof ScannedGenericBeanDefinition)) continue;
            register((ScannedGenericBeanDefinition) beanDefinition, registry);
        }
    }

    private void register(ScannedGenericBeanDefinition beanDefinition, BeanDefinitionRegistry registry) {
        MultiValueMap<String, Object> ruleAttributes = beanDefinition.getMetadata().getAllAnnotationAttributes(Rule.class.getName());
        MultiValueMap<String, Object> ruleSetAttributes = beanDefinition.getMetadata().getAllAnnotationAttributes(RuleSet.class.getName());

        if (ruleAttributes != null) {
            Object name = ruleAttributes.getFirst("name");
            registerBean(name != null ? name.toString() : null, beanDefinition, registry, Rule.class);
        }

        if (ruleSetAttributes != null) {
            Object name = ruleSetAttributes.getFirst("name");
            registerBean(name != null ? name.toString() : null, beanDefinition, registry, RuleSet.class);
        }
    }

    private void registerBean(String beanName, BeanDefinition beanDefinition,
                              BeanDefinitionRegistry registry, Class<?> type) {

        String originalBeanName = (beanName == null || "".equals(beanName.trim()))
                ? BEAN_NAME_GENERATOR.generateBeanName(beanDefinition, registry)
                : beanName;

        BeanDefinitionBuilder builder = null;

        if (type.equals(Rule.class)) {
            builder = BeanDefinitionBuilder.genericBeanDefinition(RuleBuilder.class);
        } else if (type.equals(RuleSet.class)) {
            builder = BeanDefinitionBuilder.genericBeanDefinition(RuleSetBuilder.class);
        }

        builder.addConstructorArgValue(beanDefinition.getBeanClassName());
        builder.addConstructorArgReference(BeanNames.OBJECT_FACTORY_NAME);
        builder.setFactoryMethod("build");
        registry.registerBeanDefinition(originalBeanName, builder.getBeanDefinition());
    }

    private void registerMetaInfoBean(String scriptLanguage, String[] messageSources, BeanDefinitionRegistry registry) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(RuliiMetaInfo.class);
        builder.addConstructorArgValue(scriptLanguage);
        builder.addConstructorArgValue(messageSources);
        registry.registerBeanDefinition(BeanNames.META_INFO_NAME, builder.getBeanDefinition());
    }

    private String[] getAttributes(MultiValueMap<String, Object> attributes, String name) {
        return attributes.containsKey(name) ? (String[]) attributes.getFirst(name) : null;
    }

    private String getAttribute(MultiValueMap<String, Object> attributes, String name) {
        return attributes.containsKey(name) ? (String) attributes.getFirst(name) : null;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
