/**
 * 
 */
package org.ness.com;

import java.util.Iterator;
import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;


/**
 * @author localadmin
 *
 */
public class StateLessItemReader implements ItemReader<String> {

	private final Iterator<String> data;
	
	public StateLessItemReader(List<String> data) {
		this.data = data.iterator();
	}
	
	@Override
	public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		System.out.println("read method called");
		
		if(this.data.hasNext()) {
			
			return this.data.next();
		} else {
			return null;	
		}
	}
}
