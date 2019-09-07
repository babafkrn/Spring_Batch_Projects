/**
 * 
 */
package org.ness.com;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author localadmin
 *
 */
@Configuration
public class BatchConfig {

	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Bean
	public Step step1() {
		
		return stepBuilderFactory.get("step1").tasklet(new HelloTasklet()).build();
	}
	
	@Bean
	public Job job1() {
		
		return jobBuilderFactory.get("myJob1").start(step1()).build();
	}
}
