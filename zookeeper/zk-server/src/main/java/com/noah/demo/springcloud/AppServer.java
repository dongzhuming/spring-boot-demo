package com.noah.demo.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableDiscoveryClient
@SpringBootApplication
@RestController
public class AppServer {
	public static void main(String[] args) {
		SpringApplication.run(AppServer.class, args);
	}

  @RequestMapping("/")
  public String home() {
    return "Hello World";
  }
}
