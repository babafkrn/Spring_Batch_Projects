package org.ness.com;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class SpringBatch3BootConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBatch3BootConfigApplication.class, args);
	}

}
