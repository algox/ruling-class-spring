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

package org.algorithmx.rulii.spring.convert;

import org.algorithmx.rulii.bind.convert.ConversionException;
import org.algorithmx.rulii.bind.convert.Converter;
import org.algorithmx.rulii.spring.util.ResolvableTypeDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.support.DefaultConversionService;

import javax.annotation.PostConstruct;
import java.lang.reflect.Type;

public class ConverterAdapter implements Converter<Object, Object> {

    @Autowired(required = false)
    private ConversionService conversionService = DefaultConversionService.getSharedInstance();

    public ConverterAdapter() {
        super();
    }

    @PostConstruct
    private void init() {
        if (conversionService == null) this.conversionService = DefaultConversionService.getSharedInstance();
    }

    @Override
    public Type getSourceType() {
        return Object.class;
    }

    @Override
    public Type getTargetType() {
        return Object.class;
    }

    @Override
    public boolean canConvert(Type type1, Type type2) {
        TypeDescriptor typeDescriptor1 = new ResolvableTypeDescriptor(ResolvableType.forType(type1));
        TypeDescriptor typeDescriptor2 = new ResolvableTypeDescriptor(ResolvableType.forType(type2));
        return conversionService.canConvert(typeDescriptor1, typeDescriptor2);
    }

    @Override
    public Object convert(Object source, Type type) throws ConversionException {
        if (source == null) return null;
        TypeDescriptor sourceType = TypeDescriptor.forObject(source);
        TypeDescriptor targetType = new ResolvableTypeDescriptor(ResolvableType.forType(type));
        return conversionService.convert(source, sourceType, targetType);
    }

    @Override
    public String toString() {
        return "Generic Spring Based Converter";
    }
}
