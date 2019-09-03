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

/**
 * Constant class containing all the Spring bean names used by the Rule Framework.
 *
 * @author Max Arulananthan.
 * @since 1.0
 */
public final class BeanNames {

    public static final String OBJECT_FACTORY_NAME          = "defaultRuleObjectFactory";

    public static final String RULE_FACTORY_NAME            = "defaultRuleFactory";

    public static final String RULE_ENGINE_NAME             = "defaultRuleEngine";

    public static final String PARAMETER_RESOLVER_NAME      = "defaultParameterResolver";

    public static final String BINDABLE_METHOD_EEXECUTOR    = "defaultBindableMethodExecutor";
}
