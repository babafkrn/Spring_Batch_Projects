/**
 * 
 */
package org.ness.com;

import org.springframework.batch.item.ItemProcessor;

/**
 * @author localadmin
 *
 */
public class UpperCaseItemProcessor implements ItemProcessor<Department, Department> {

	@Override
	public Department process(Department item) throws Exception {
		// TODO Auto-generated method stub
		return new Department(item.getDeptno(), item.getName().toUpperCase(), item.getLocation());
	}

}
