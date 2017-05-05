package com.noah.demo.springcloud;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.curator.test.TestingServer;
import org.junit.Test;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.SocketUtils;

public class SampleApplicationTests {

	@Test
	public void contextLoads() throws Exception {
		int zkPort = SocketUtils.findAvailableTcpPort();
		TestingServer server = new TestingServer(zkPort);

		int port = SocketUtils.findAvailableTcpPort(zkPort + 1);

		ConfigurableApplicationContext context = new SpringApplicationBuilder(SampleZookeeperApplication.class)
		    .run("--server.port=" + port, "--spring.cloud.zookeeper.connectString=localhost:" + zkPort);

		ResponseEntity<String> response = new TestRestTemplate().getForEntity("http://localhost:" + port + "/health",
		    String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		context.close();
		server.close();
	}
}