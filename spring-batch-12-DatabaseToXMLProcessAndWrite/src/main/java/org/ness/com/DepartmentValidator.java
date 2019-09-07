/**
 * 
 */
package org.ness.com;

import org.springframework.batch.item.validator.ValidationException;
import org.springframework.batch.item.validator.Validator;

/**
 * @author localadmin
 *
 */
public class DepartmentValidator implements Validator<Department> {

	@Override
	public void validate(Department value) throws ValidationException {
		
		if(value.getName().startsWith("Cgr")) {
			throw new ValidationException("department name being with Agr, invalid data");
		}
	}
}
