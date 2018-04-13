package com.example.demo;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author dongzhuming
 */
@SpringBootApplication
public class PushGatewayApplication {

    @Bean
    public MeterRegistryCustomizer<MeterRegistry> commonTags() {
//        return MeterRegistry::config;
        return  (registry) -> registry.config().commonTags("job", "test1");
    }
	public static void main(String[] args) {
		SpringApplication.run(PushGatewayApplication.class, args);
	}
}
