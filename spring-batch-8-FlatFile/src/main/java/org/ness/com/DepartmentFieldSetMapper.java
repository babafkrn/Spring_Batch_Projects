/**
 * 
 */
package org.ness.com;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

/**
 * @author localadmin
 *
 */
public class DepartmentFieldSetMapper implements FieldSetMapper<Department> {

	@Override
	public Department mapFieldSet(FieldSet fieldSet) throws BindException {
		
		return new Department(fieldSet.readInt("deptno"), fieldSet.readString("name"), fieldSet.readString("location"));
	}
}
