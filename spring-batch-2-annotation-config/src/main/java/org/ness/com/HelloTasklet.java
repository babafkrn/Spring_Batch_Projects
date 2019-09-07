/**
 * 
 */
package org.ness.com;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

/**
 * @author localadmin
 *
 */
public class HelloTasklet implements Tasklet {

	int x = 0;
	
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		
		System.out.println("Hello !");
		
		x++;
		
		if(x < 10) {
			
			return RepeatStatus.CONTINUABLE;
		} else {
			return RepeatStatus.FINISHED;
		}
	}
}
