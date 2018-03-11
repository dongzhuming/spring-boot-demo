package org.molecule.demo.metricsinflux;

import org.springframework.http.ResponseEntity;
import org.springframework.jmx.export.annotation.ManagedMetric;
import org.springframework.jmx.support.MetricType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Dong ZhuMing
 */
@RestController
public class MyController {

    @GetMapping("/hi")
    @ManagedMetric(metricType = MetricType.GAUGE)
    public ResponseEntity<?> sayHi() {
        return ResponseEntity.ok("hi");
    }
}

