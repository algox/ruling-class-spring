package org.algorithmx.rules.spring;

import org.algorithmx.rules.core.ObjectFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.util.Assert;

public class SpringObjectFactory implements ObjectFactory {

    private final AutowireCapableBeanFactory ctx;

    public SpringObjectFactory(AutowireCapableBeanFactory ctx) {
        super();
        Assert.notNull(ctx, "ctx cannot be null");
        this.ctx = ctx;
    }

    @Override
    public <T> T create(Class<T> beanClass) {
        return ctx.createBean(beanClass);
    }
}
