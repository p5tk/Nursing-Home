package com.blissstock.nursinghomesupport.main;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import com.blissstock.nursinghomesupport.storage.StorageProperties;
import com.blissstock.nursinghomesupport.storage.StorageService;


@SpringBootApplication
@ComponentScan("com.blissstock.nursinghomesupport")
@EntityScan("com.blissstock.nursinghomesupport.entity")
@EnableJpaRepositories("com.blissstock.nursinghomesupport.repository")
@EnableConfigurationProperties(StorageProperties.class)
public class NursingHomeSystemApp {

	public static void main(String[] args) {
		SpringApplication.run(NursingHomeSystemApp.class, args);
	}
	

	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.init();
		};
	}
}
