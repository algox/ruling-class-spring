package org.algorithmx.rules.spring;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;

public class IndependentCandidateClassPathScanner extends ClassPathScanningCandidateComponentProvider {

    public IndependentCandidateClassPathScanner() {
        super();
    }

    public IndependentCandidateClassPathScanner(boolean useDefaultFilters) {
        super(useDefaultFilters);
    }

    @Override
    protected final boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isIndependent();
    }
}
