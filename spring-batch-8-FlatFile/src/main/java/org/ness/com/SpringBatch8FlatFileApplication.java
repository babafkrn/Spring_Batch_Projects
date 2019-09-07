package org.ness.com;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class SpringBatch8FlatFileApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBatch8FlatFileApplication.class, args);
	}

}
