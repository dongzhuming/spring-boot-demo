package org.molecule.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class ConditionalOnXxxApplication implements CommandLineRunner {

	@Autowired
	private BaseTestService myTestService;

	public static void main(String[] args) {
		SpringApplication.run(ConditionalOnXxxApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		myTestService.doSomething();
	}
}
