package org.molecule.demo.gracefulshutdown;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import javax.annotation.PreDestroy;


/**
 * @author dongzhuming
 */
@SpringBootApplication
@Slf4j
public class GracefulShutdownApplication {

	public static void main(String[] args) {
		SpringApplication.run(GracefulShutdownApplication.class, args);
	}

	@PreDestroy
	private void tearDown() throws InterruptedException {
		log.info("正在销毁实例，请等待5秒");
		Thread.sleep(5000L);
		log.info("程序正常关闭！");
	}
}
