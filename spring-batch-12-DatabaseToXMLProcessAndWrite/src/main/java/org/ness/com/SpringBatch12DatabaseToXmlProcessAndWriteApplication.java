package org.ness.com;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class SpringBatch12DatabaseToXmlProcessAndWriteApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBatch12DatabaseToXmlProcessAndWriteApplication.class, args);
	}

}
