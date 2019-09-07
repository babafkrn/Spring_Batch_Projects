/**
 * 
 */
package org.ness.com;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.xstream.XStreamMarshaller;

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
	
	@Autowired
	public StaxEventItemReader<Department> departmentItemReader() {
		
		XStreamMarshaller unmaMarshaller = new XStreamMarshaller(); // xstream / cursor
		Map<String, Class<?>> aliases = new HashMap<>();
		aliases.put("department", Department.class); // what tag to be mapped what class object
		unmaMarshaller.setAliases(aliases);
		
		StaxEventItemReader<Department> reader = new StaxEventItemReader<>();
		reader.setResource(new ClassPathResource("departments.xml"));
		reader.setFragmentRootElementName("department");
		reader.setUnmarshaller(unmaMarshaller); // what each chunk is delimeted with
		
		return reader;
	}
	
	@Bean
	public ItemWriter<Department> departmentItemWriter() {
		
		return new ItemWriter<Department>() {
			
			public void write(List<? extends Department> items) throws Exception {
				
				for(Department department : items) {
					System.out.println(department);
				}
			}
		};
	}
	
	@Bean
	public Step step1() {
		
		return stepBuilderFactory.get("step1")
				.<Department, Department>chunk(10)
				.reader(departmentItemReader())
				//.reader(pagingItemReader())
				.writer(departmentItemWriter()).build();
	}
	
	@Bean
	public Job myJob1() {
		
		return jobBuilderFactory.get("myJob101").start(step1()).build();
	}
	
}
