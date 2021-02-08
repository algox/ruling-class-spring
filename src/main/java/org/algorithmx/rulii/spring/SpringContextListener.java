package org.algorithmx.rulii.spring;

import org.algorithmx.rulii.config.RuliiConfiguration;
import org.algorithmx.rulii.config.RuliiConfigurationBuilder;
import org.algorithmx.rulii.config.RuliiSystem;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

public class SpringContextListener implements ApplicationListener<ContextClosedEvent> {

    public SpringContextListener() {
        super();
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        RuliiConfiguration configuration = RuliiConfigurationBuilder.create().build();
        RuliiSystem.getInstance().setConfiguration(configuration);
    }
}
