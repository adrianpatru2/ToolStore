package com.toolstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Profile;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Traverson;
import org.springframework.web.client.RestTemplate;

import com.toolstore.config.ToolStoreSecurity;
import com.toolstore.config.ToolStoreUser;

@SpringBootApplication
public class ToolStoreApplication {
	@Configuration
	@Profile({ "pg", "h2" })
	@ComponentScan(basePackages = { "com.toolstore", "com.toolstore.io" }, excludeFilters = {
			@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = ToolStoreSecurity.class),
			@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = ToolStoreUser.class) })
	public class ConfigDb {
	}

	public static void main(final String[] args) {
		SpringApplication.run(ToolStoreApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().addAll(Traverson.getDefaultMessageConverters(MediaTypes.HAL_JSON));
		return restTemplate;
	}
}
