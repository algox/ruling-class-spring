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

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;

/**
 * Spring class path scanner provider which looks for all beans that are top level classes.
 *
 * @author Max Arulananthan.
 * @since 1.0
 */
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
