package org.molecule.demo.metricsinflux;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.influx.InfluxConfig;
import io.micrometer.influx.InfluxMeterRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jmx.export.annotation.ManagedMetric;

import java.time.Duration;

@SpringBootApplication
public class MetricsInfluxApplication {

	public static void main(String[] args) {
		SpringApplication.run(MetricsInfluxApplication.class, args);
	}
}
