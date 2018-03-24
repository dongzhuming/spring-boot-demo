package com.molecule.demo.metricsprometheus;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.spring.autoconfigure.MeterRegistryCustomizer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Clock;
import java.util.stream.Stream;

/**
 * @author Dong Zhuming
 */
@SpringBootApplication
@RestController
public class MetricsPrometheusApplication {

    @GetMapping("/hi")
    public String sayHi() {
        return "Hello";
    }

    @Bean
    public MeterRegistryCustomizer<MeterRegistry> commonTags() {
//        return MeterRegistry::config;
      return  (registry) -> registry.config().commonTags("job", "test1");
    }

    @Bean
    CommandLineRunner commandLineRunner(CityRepository cityRepository) {

        return args -> {
            System.out.println(Clock.systemDefaultZone());
            Stream.of("li", "lv", "yu", "lu").forEach(s -> {
                cityRepository.save(new City(s, "country"));
            });
        };

    }

    public static void main(String[] args) {
        SpringApplication.run(MetricsPrometheusApplication.class, args);
    }
}
