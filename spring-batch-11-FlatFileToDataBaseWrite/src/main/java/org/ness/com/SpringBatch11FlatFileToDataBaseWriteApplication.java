package org.ness.com;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class SpringBatch11FlatFileToDataBaseWriteApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBatch11FlatFileToDataBaseWriteApplication.class, args);
	}

}
