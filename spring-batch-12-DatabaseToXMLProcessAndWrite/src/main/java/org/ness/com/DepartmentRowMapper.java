/**
 * 
 */
package org.ness.com;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author localadmin
 *
 */
public class DepartmentRowMapper implements RowMapper<Department> {

	@Override
	public Department mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		return new Department(rs.getInt("deptno"), rs.getString("name"), rs.getString("location"));
	}

}
