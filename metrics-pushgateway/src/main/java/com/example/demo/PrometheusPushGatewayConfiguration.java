package com.example.demo;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.exporter.PushGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.PreDestroy;
import java.net.UnknownHostException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author dongzhuming
 */
@Configuration
@ConditionalOnClass(PushGateway.class)
@Conditional(PushgatewayConfiguration.PrometheusPushGatewayEnabledCondition.class)
@Slf4j
public class PrometheusPushGatewayConfiguration {
    private final CollectorRegistry collectorRegistry;
    private final PushgatewayConfiguration.PushgatewayProperties pushgatewayProperties;
    private final PushGateway pushGateway;
    private final Environment environment;
    private final ScheduledExecutorService executorService;

    PrometheusPushGatewayConfiguration(CollectorRegistry collectorRegistry,
                                       PushgatewayConfiguration.PushgatewayProperties pushgatewayProperties, Environment environment) {
        this.collectorRegistry = collectorRegistry;
        this.pushgatewayProperties = pushgatewayProperties;
        this.pushGateway = new PushGateway(pushgatewayProperties.getBaseUrl());
        this.environment = environment;
        this.executorService = Executors.newSingleThreadScheduledExecutor((r) -> {
            final Thread thread = new Thread(r);
            thread.setDaemon(true);
            thread.setName("micrometer-pushgateway");
            return thread;
        });
        executorService.scheduleAtFixedRate(this::push, 0, pushgatewayProperties.getPushRate().toMillis(),
                TimeUnit.MILLISECONDS);
    }

    void push() {
        try {
            pushGateway.pushAdd(collectorRegistry, job(), pushgatewayProperties.getGroupingKeys());
        } catch (UnknownHostException e) {
            log.error("Unable to locate host '" + pushgatewayProperties.getBaseUrl()
                    + "'. No longer attempting metrics publication to this host");
            executorService.shutdown();
        } catch (Throwable t) {
            log.error("Unable to push metrics to Prometheus Pushgateway", t);
        }
    }

    @PreDestroy
    void shutdown() {
        executorService.shutdown();
        if (pushgatewayProperties.isPushOnShutdown()) {
            push();
        }
        if (pushgatewayProperties.isDeleteOnShutdown()) {
            try {
                pushGateway.delete(job(), pushgatewayProperties.getGroupingKeys());
            } catch (Throwable t) {
                log.error("Unable to delete metrics from Prometheus Pushgateway", t);
            }
        }
    }

    private String job() {
        String job = pushgatewayProperties.getJob();
        if (job == null) {
            job = environment.getProperty("spring.application.name");
        }
        if (job == null) {
            // There's a history of Prometheus spring integration defaulting the job name to
            // "spring" from when
            // Prometheus integration didn't exist in Spring itself.
            job = "spring";
        }
        return job;
    }
}