/**
 * 
 */
package org.ness.com;

import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

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
	public FlatFileItemReader<Department> departmentItemReader() {
		
		FlatFileItemReader<Department> fileItemReader = new FlatFileItemReader<Department>();
		fileItemReader.setLinesToSkip(1);
		
		//from where you want to read the data
		fileItemReader.setResource(new ClassPathResource("department.csv"));
		DefaultLineMapper<Department> defaultLineMapper = new DefaultLineMapper<>();
		DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
		delimitedLineTokenizer.setNames("deptno", "name", "location");
		
		defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);
		
		defaultLineMapper.setFieldSetMapper(new DepartmentFieldSetMapper());
		
		defaultLineMapper.afterPropertiesSet();
		
		fileItemReader.setLineMapper(defaultLineMapper);

		return fileItemReader;
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
		
		return jobBuilderFactory.get("myJob8").start(step1()).build();
	}
}
