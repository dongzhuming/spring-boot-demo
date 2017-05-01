package com.noah.demo.springcloud;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@EnableDiscoveryClient
public class SayHelloApplication {
	private static Logger log = LoggerFactory.getLogger(SayHelloApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SayHelloApplication.class, args);
	}

	@Value("${server.port}")
	String serverPort;

	@RequestMapping("/greeting")
	public String greet() {
		log.info("access /greeting");
		List<String> greetings = Arrays.asList("Hi there,Greetings,Solutions".split(","));
		Random rand = new Random();
		int randomValue = rand.nextInt(greetings.size());
		return serverPort + " : " + greetings.get(randomValue);
	}
	
	@RequestMapping("/")
	public String home() {
		log.info("access /");
		return "Hi";
	}
}
