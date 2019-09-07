package org.ness.com;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class SpringBatch6ItemReaderApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBatch6ItemReaderApplication.class, args);
	}

}
