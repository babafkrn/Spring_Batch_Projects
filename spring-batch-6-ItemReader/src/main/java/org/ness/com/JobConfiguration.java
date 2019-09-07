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
import org.springframework.batch.item.ItemWriter;
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
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public StateLessItemReader stateLessItemReader() {
		
		List<String> data = new ArrayList<>();
		data.add("foo");
		data.add("bar");
		data.add("baz");
		
		return new StateLessItemReader(data);
	}
	
	@Bean
	public Step step1() {
		
		return stepBuilderFactory.get("step1")
				.<String, String>chunk(2)
				.reader(stateLessItemReader())
				.writer(new ItemWriter<String>() {
					public void write(List<? extends String> items) throws Exception {
						
						System.out.println("Chunk: " + items);
						
						for(String item: items) {
							System.out.println("Current item: " + item);
						}
					}
				}).build();
	}
	
	@Bean
	public Job myJob1() {
		
		return jobBuilderFactory.get("myJob3").start(step1()).build();
	}
}
