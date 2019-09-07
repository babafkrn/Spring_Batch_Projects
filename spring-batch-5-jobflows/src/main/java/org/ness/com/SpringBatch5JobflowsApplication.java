package org.ness.com;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class SpringBatch5JobflowsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBatch5JobflowsApplication.class, args);
	}

}
