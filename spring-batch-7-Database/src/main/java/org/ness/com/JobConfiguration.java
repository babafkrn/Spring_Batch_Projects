/**
 * 
 */
package org.ness.com;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.OraclePagingQueryProvider;
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
	
	@Autowired
	private DataSource dataSource;
	
	@Bean
	public JdbcCursorItemReader<Department> cursorItemReader() {
		
		JdbcCursorItemReader<Department> itemReader = new JdbcCursorItemReader<Department>();
		itemReader.setDataSource(dataSource);
		itemReader.setSql("SELECT DEPTNO, NAME, LOCATION FROM DEPARTMENT ORDER BY NAME DESC");
		itemReader.setRowMapper(new DepartmentRowMapper());
		return itemReader;
	}
	
	@Bean
	public JdbcPagingItemReader<Department> pagingItemReader() {
	 
		JdbcPagingItemReader<Department> jdbcPagingItemReader = new JdbcPagingItemReader<>();
		jdbcPagingItemReader.setDataSource(dataSource);
		jdbcPagingItemReader.setFetchSize(10);
		jdbcPagingItemReader.setRowMapper(new DepartmentRowMapper());
		
		OraclePagingQueryProvider oraclePagingQueryProvider = new OraclePagingQueryProvider();
		oraclePagingQueryProvider.setSelectClause("deptno, name, location");
		oraclePagingQueryProvider.setFromClause("from Department");
		
		Map<String, Order> sortKeys = new HashMap<>();
		sortKeys.put("name", Order.ASCENDING);
		
		oraclePagingQueryProvider.setSortKeys(sortKeys);
		
		jdbcPagingItemReader.setQueryProvider(oraclePagingQueryProvider);
		return jdbcPagingItemReader;
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
				// .reader(cursorItemReader())
				.reader(pagingItemReader())
				.writer(departmentItemWriter()).build();
	}
	
	@Bean
	public Job myJob1() {
		
		return jobBuilderFactory.get("myJob6").start(step1()).build();
	}
}
