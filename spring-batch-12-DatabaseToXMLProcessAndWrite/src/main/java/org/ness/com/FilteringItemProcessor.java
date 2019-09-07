/**
 * 
 */
package org.ness.com;

import org.springframework.batch.item.ItemProcessor;

/**
 * @author localadmin
 *
 */
public class FilteringItemProcessor implements ItemProcessor<Department, Department> {

	@Override
	public Department process(Department item) throws Exception {
		
		if(item.getDeptno() == 121 || item.getDeptno() == 129203) {
			return null;
		}
		return new Department(item.getDeptno(), item.getName(), item.getLocation());
	}

}
