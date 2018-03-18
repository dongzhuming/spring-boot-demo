package org.molecule.demo.metricsstatsd;

import io.micrometer.core.annotation.Timed;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class MetricsStatsdApplication {

	@Timed
	@GetMapping("/hi")
	public String sayHello() {
		return "Hello";
	}

//	@Bean
//	public MetricsEndpointMetricReader metricsEndpointMetricReader(final MetricsEndpoint metricsEndpoint) {
//		return new MetricsEndpointMetricReader(metricsEndpoint);
//	}
	public static void main(String[] args) {
		SpringApplication.run(MetricsStatsdApplication.class, args);
	}
}
