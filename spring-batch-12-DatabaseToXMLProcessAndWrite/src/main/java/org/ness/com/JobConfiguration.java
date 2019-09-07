/**
 * 
 */
package org.ness.com;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.OraclePagingQueryProvider;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.batch.item.validator.ValidatingItemProcessor;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.xstream.XStreamMarshaller;

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
	
	@Autowired
	private DataSource dataSource;
	
	// read the data from the database in a page wise manner
	@Bean
	public JdbcPagingItemReader<Department> pagingItemReader() {
		
		JdbcPagingItemReader<Department> jdbcPagingItemReader = new JdbcPagingItemReader<>();
		jdbcPagingItemReader.setDataSource(dataSource);
		jdbcPagingItemReader.setFetchSize(10);
		
		jdbcPagingItemReader.setRowMapper(new DepartmentRowMapper());
		
		OraclePagingQueryProvider oraclePagingQueryProvider = new OraclePagingQueryProvider();
		oraclePagingQueryProvider.setFromClause("from department");
		oraclePagingQueryProvider.setSelectClause("deptno, name, location");
		
		Map<String, Order> sortKeys = new HashMap<>();
		sortKeys.put("name", Order.ASCENDING);
		
		oraclePagingQueryProvider.setSortKeys(sortKeys);
		
		jdbcPagingItemReader.setQueryProvider(oraclePagingQueryProvider);
		
		return jdbcPagingItemReader;
	}
	
	@Bean
	public StaxEventItemWriter<Department> departmentItemWriter() throws Exception {
		
		XStreamMarshaller marshaller = new XStreamMarshaller();
		
		Map<String, Class> aliases = new HashMap<>();
		aliases.put("department", Department.class);
		marshaller.setAliases(aliases);
		
		StaxEventItemWriter<Department> writer = new StaxEventItemWriter<>();
		writer.setRootTagName("departments");
		writer.setMarshaller(marshaller);
		
		// writing the xml to a temporary file
		String accountOutputPath = File.createTempFile("accountoutput", ".xml").getAbsolutePath();
		System.out.println("Absolute Path: " + accountOutputPath);
		
		writer.setResource(new FileSystemResource(accountOutputPath));
		writer.afterPropertiesSet();
		
		return writer;
	}
	
	@Bean
	public UpperCaseItemProcessor upperCaseItemProcessor() {
		
		return new UpperCaseItemProcessor();
	}
	
	@Bean
	public FilteringItemProcessor filteringItemProcessor() {
		
		return new FilteringItemProcessor();
	}
	
	@Bean
	public CompositeItemProcessor<Department, Department> itemProcessor() throws Exception {
		
		List<ItemProcessor<Department, Department>> delegates = new ArrayList<>();
		delegates.add(new UpperCaseItemProcessor());
		delegates.add(new FilteringItemProcessor());
		
		CompositeItemProcessor<Department, Department> compositeItemProcessor = new CompositeItemProcessor<>();
		compositeItemProcessor.setDelegates(delegates);
		compositeItemProcessor.afterPropertiesSet();
		
		return compositeItemProcessor;
	}
	
	@Bean
	public ValidatingItemProcessor<Department> validatingItemProcessor() {
	
		ValidatingItemProcessor<Department> itemProcessor = new ValidatingItemProcessor<>(new DepartmentValidator());
		// this is will throw a ValidationException
		return itemProcessor;
	}
	
	@Bean
	public Step step1() throws Exception {
		
		return stepBuilderFactory.get("step1")
				.<Department, Department>chunk(10)
				.reader(pagingItemReader())
				//.processor(validatingItemProcessor())
				.processor(itemProcessor())
				//.processor(upperCaseItemProcessor())
				.processor(validatingItemProcessor())
				.writer(departmentItemWriter())
				.build();
	}
	
	@Bean
	public Job job1() throws Exception {
		
		return jobBuilderFactory.get("myJob1817").start(step1()).build();
	}
}
