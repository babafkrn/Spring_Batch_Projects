/**
 * 
 */
package org.ness.com;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author localadmin
 *
 */
@Configuration
public class JobConfiguration {

	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Bean
	public ListItemReader<String> itemReader() {
		
		List<String> list = new ArrayList<>(100);
		
		for(int i = 0; i <= 100; i++) {
			list.add(String.valueOf(i));
		}
		
		return new ListItemReader<>(list);
	}
	
	@Bean
	public SysOutItemWriter itemWriter() {
	
		return new SysOutItemWriter();
	}
	
	@Bean
	public Step step1() {
		
		return stepBuilderFactory.get("step1")
				.<String, String>chunk(50)
				.reader(itemReader())
				.writer(itemWriter())
				.build();
	}
	
	@Bean
	public Job job1() {
		
		return jobBuilderFactory.get("myJob11").start(step1()).build();
	}
}
