/**
 * 
 */
package org.ness.com;

/**
 * @author localadmin
 *
 */
public class Department {

	private int deptno;
	
	private String name;
	
	private String location;

	public Department(int deptno, String name, String location) {
		this.deptno = deptno;
		this.name = name;
		this.location = location;
	}
	
	/**
	 * @return the deptno
	 */
	public int getDeptno() {
		return deptno;
	}


	/**
	 * @param deptno the deptno to set
	 */
	public void setDeptno(int deptno) {
		this.deptno = deptno;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	
	@Override
	public String toString() {
		
		return "[" + "id=" + deptno + ", name=" + name + ", location=" + location + "]";
	}
}
