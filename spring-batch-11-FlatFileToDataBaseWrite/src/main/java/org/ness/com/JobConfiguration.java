/**
 * 
 */
package org.ness.com;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
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
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private DataSource dataSource;
	
	@Bean
	public FlatFileItemReader<Department> itemReader() {
		
		FlatFileItemReader<Department> reader = new FlatFileItemReader<>();
		reader.setLinesToSkip(1);
		
		// location from where we want to read the data
		reader.setResource(new ClassPathResource("department.csv"));
		
		// to read the data from a file
		DefaultLineMapper<Department> defaultLineMapper = new DefaultLineMapper<Department>();
		
		DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
		delimitedLineTokenizer.setNames("deptno","name","location");
		
		defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);
		
		defaultLineMapper.setFieldSetMapper(new DepartmentFiledSetMapper());
		
		defaultLineMapper.afterPropertiesSet();
		
		reader.setLineMapper(defaultLineMapper);
		
		return reader;
	}
	
	@Bean
	public JdbcBatchItemWriter<Department> itemWriter() {
		
		JdbcBatchItemWriter<Department> jdbcBatchItemWriter = new JdbcBatchItemWriter<>();
		
		jdbcBatchItemWriter.setDataSource(dataSource);
		jdbcBatchItemWriter.setSql("INSERT INTO DEPARTMENT VALUES(:deptno, :name, :location)");
		jdbcBatchItemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
		jdbcBatchItemWriter.afterPropertiesSet();
		return jdbcBatchItemWriter;
	}
	
	@Bean
	public Step step1() {
		
		return stepBuilderFactory.get("step1").<Department, Department>chunk(10).reader(itemReader()).writer(itemWriter()).build();
	}
	
	@Bean
	public Job job1() {
		
		return jobBuilderFactory.get("myJob1211").start(step1()).build();
	}
}
