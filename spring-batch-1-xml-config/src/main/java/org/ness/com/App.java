/**
 * 
 */
package org.ness.com;

import java.util.Date;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author localadmin
 *
 */
public class App {

	public static void main(String[] args) throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("org/ness/com/beans.xml");
		JobRepository jobRepository = context.getBean(JobRepository.class);
		System.out.println(jobRepository);
		
		JobLauncher jobLauncher  = context.getBean(JobLauncher.class);
		
		Job job1 = context.getBean("myJob1", Job.class);
		System.out.println(job1);
		
		JobParametersBuilder builder = new JobParametersBuilder().addDate("currenttime", new Date());
		jobLauncher.run(job1, builder.toJobParameters());
	}
}
