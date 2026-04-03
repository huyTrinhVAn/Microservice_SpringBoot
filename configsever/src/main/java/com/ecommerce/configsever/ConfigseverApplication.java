package com.ecommerce.configsever;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfigseverApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigseverApplication.class, args);
	}

}
