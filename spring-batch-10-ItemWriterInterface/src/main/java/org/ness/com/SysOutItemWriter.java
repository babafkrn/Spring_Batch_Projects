/**
 * 
 */
package org.ness.com;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

/**
 * @author localadmin
 *
 */
public class SysOutItemWriter implements ItemWriter<String>{

	@Override
	public void write(List<? extends String> items) throws Exception {

		System.out.println("The chunk size was: " + items.size());
		
		for(String item : items) {
			System.out.println(">>: " + item);
		}
	}

	
}
