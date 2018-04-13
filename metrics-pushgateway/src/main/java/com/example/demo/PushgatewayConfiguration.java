package com.example.demo;

import org.springframework.boot.autoconfigure.condition.AllNestedConditions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Dong Zhuming
 */
@Configuration
@EnableConfigurationProperties(PushgatewayConfiguration.PushgatewayProperties.class)
public class PushgatewayConfiguration {

    @ConfigurationProperties(prefix = "management.metrics.export.prometheus.pushgateway")
    public static class PushgatewayProperties {
        /**
         * Enable publishing via a Prometheus Pushgateway.
         */
        private Boolean enabled = false;

        /**
         * Required host:port or ip:port of the Pushgateway.
         */
        private String baseUrl = "localhost:9091";

        /**
         * Required identifier for this application instance.
         */
        private String job;

        /**
         * Frequency with which to push metrics to Pushgateway.
         */
        private Duration pushRate = Duration.ofMinutes(1);

        /**
         * Push metrics right before shut-down. Mostly useful for batch jobs.
         */
        private boolean pushOnShutdown = true;

        /**
         * Delete metrics from Pushgateway when application is shut-down
         */
        private boolean deleteOnShutdown = true;

        /**
         * Used to group metrics in pushgateway. A common example is setting
         */
        private Map<String, String> groupingKeys = new HashMap<>();

        public Boolean getEnabled() {
            return enabled;
        }

        public void setEnabled(Boolean enabled) {
            this.enabled = enabled;
        }

        public String getBaseUrl() {
            return baseUrl;
        }

        public void setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
        }

        public String getJob() {
            return job;
        }

        public void setJob(String job) {
            this.job = job;
        }

        public Duration getPushRate() {
            return pushRate;
        }

        public void setPushRate(Duration pushRate) {
            this.pushRate = pushRate;
        }

        public boolean isPushOnShutdown() {
            return pushOnShutdown;
        }

        public void setPushOnShutdown(boolean pushOnShutdown) {
            this.pushOnShutdown = pushOnShutdown;
        }

        public boolean isDeleteOnShutdown() {
            return deleteOnShutdown;
        }

        public void setDeleteOnShutdown(boolean deleteOnShutdown) {
            this.deleteOnShutdown = deleteOnShutdown;
        }

        public Map<String, String> getGroupingKeys() {
            return groupingKeys;
        }

        public void setGroupingKeys(Map<String, String> groupingKeys) {
            this.groupingKeys = groupingKeys;
        }
    }

    static class PrometheusPushGatewayEnabledCondition extends AllNestedConditions {
        public PrometheusPushGatewayEnabledCondition() {
            super(ConfigurationPhase.PARSE_CONFIGURATION);
        }

        @ConditionalOnProperty(value = "management.metrics.export.prometheus.enabled", matchIfMissing = true)
        static class PrometheusMeterRegistryEnabled {
            //
        }

        @ConditionalOnProperty("management.metrics.export.prometheus.pushgateway.enabled")
        static class PushGatewayEnabled {
            //
        }
    }
}