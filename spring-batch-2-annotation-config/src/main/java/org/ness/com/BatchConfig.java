/**
 * 
 */
package org.ness.com;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.SimpleJob;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author localadmin
 *
 */
@Configuration
public class BatchConfig {

	// configure job repository This would reside in your BatchConfigurer implementation
	@Bean
	protected JobRepository jobRepository() throws Exception {
	    JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
	    factory.setDataSource(dataSource());
	    factory.setTransactionManager(transactionManager());
	    factory.setIsolationLevelForCreate("ISOLATION_SERIALIZABLE");
	    factory.setTablePrefix("BATCH_");
	    factory.setMaxVarCharLength(1000);
	    return factory.getObject();
	}
	
	// configure data source
	@Bean
	public DriverManagerDataSource dataSource() {
		
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:DEMO12");
		dataSource.setUsername("system");
		dataSource.setPassword("password1");
		return dataSource;
	}
	
	// configure transaction manager
	@Bean
	public PlatformTransactionManager transactionManager() {
		
		return new DataSourceTransactionManager(dataSource());
	}
	
	
	@Bean
	public HelloTasklet helloTasklet() {
		
		return new HelloTasklet();
	}
	
	// configure step
	@Bean
	public Step step1() throws Exception {
		
		TaskletStep step = new TaskletStep();
		step.setJobRepository(jobRepository());
		step.setTasklet(helloTasklet());
		step.setTransactionManager(transactionManager());
		
		return step;
	}
	
	// configure job
	@Bean
	public Job job1() throws Exception {
		
		SimpleJob simpleJob = new SimpleJob();
		simpleJob.addStep(step1());
		simpleJob.setJobRepository(jobRepository());
		
		return simpleJob;
	}
	
	// configure job launcher
	@Bean
	public JobLauncher launcher() throws Exception {
		
		SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
		jobLauncher.setJobRepository(jobRepository());
		jobLauncher.afterPropertiesSet();
		return jobLauncher;
	}
}
