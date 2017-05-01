package com.noah.demo.springboot.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ConfigurationProperties(prefix = "queues")
public class QueueController {
	private List<String> toListen = new ArrayList<>();

	public List<String> getToListen() {
		return toListen;
	}


	@RequestMapping("/queues")
	public List<String> getQueues() {
		return toListen;
	}
}
