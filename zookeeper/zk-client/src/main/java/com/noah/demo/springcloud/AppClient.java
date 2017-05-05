package com.noah.demo.springcloud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class AppClient {
	private static Logger log = LoggerFactory.getLogger(AppClient.class);

	@Autowired
	private LoadBalancerClient loadBalancer;

	@Autowired
	private DiscoveryClient discovery;

	@RequestMapping("/discovery")
	public Object discovery() {
		log.info(loadBalancer.choose("test").toString());
		return "discovery";
	}

	@RequestMapping("/all")
	public Object all() {
		log.info(discovery.getServices().toString());
		return "all";
	}

	public static void main(String[] args) {
		SpringApplication.run(AppClient.class, args);
	}
}