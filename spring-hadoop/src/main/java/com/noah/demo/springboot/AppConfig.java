package com.noah.demo.springboot;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.hive.jdbc.HiveDriver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.hadoop.hive.HiveClientFactory;
import org.springframework.data.hadoop.hive.HiveClientFactoryBean;
import org.springframework.data.hadoop.hive.HiveTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

@Configuration
@ConditionalOnMissingBean(HiveTemplate.class)
@ConfigurationProperties(value = "${hive.jdbc}")
public class AppConfig {

	private String url;
	
	private String driver;
	
	private String username;
	
	private String password;
	
	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl(url);
		dataSource.setDriverClassName(driver);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		return dataSource;
	}
	
	@Bean
	public HiveClientFactory hiveClientFactory() throws Exception {
		HiveClientFactoryBean bean = new HiveClientFactoryBean();
		HiveDriver driver = new HiveDriver();
		DataSource dataSource = new SimpleDriverDataSource(driver,"jdbc:hive2://10.4.168.180:10000", "root", "root");
		bean.setHiveDataSource(dataSource);
		bean.afterPropertiesSet();
		return bean.getObject();
	}
	
	@Bean
	public HiveTemplate hiveTemplate() throws Exception {
	  return new HiveTemplate(hiveClientFactory());
	}
}
