package org.molecule.demo;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Dong Zhuming
 */
@Configuration
@ConditionalOnMissingBean(BaseTestService.class)
public class MyConfig {
    @Bean
    public DefaultTestService defaultTestService() {
        return new DefaultTestService();
    }
}
