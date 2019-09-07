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
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author localadmin
 *
 */
public class App {

	public static void main(String[] args) throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		
		AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(BatchConfig.class);
		Job job = annotationConfigApplicationContext.getBean(Job.class);
		
		JobLauncher jobLauncher = annotationConfigApplicationContext.getBean(JobLauncher.class);
		JobParametersBuilder builder = new JobParametersBuilder().addDate("currenttime", new Date());
		jobLauncher.run(job, builder.toJobParameters());
	}
}
