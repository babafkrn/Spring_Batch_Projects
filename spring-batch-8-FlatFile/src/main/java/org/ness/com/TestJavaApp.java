/**
 * 
 */
package org.ness.com;

/**
 * @author localadmin
 *
 */
public class TestJavaApp {

	public static void main(String...args) {

		doStuff();
		System.out.println(10/0);
	}
	
	public static void doStuff() {
		
		System.out.println("Hello...!");
		
		doMoreStuff();
	}
	
	public static void doMoreStuff() {
		
		System.out.println("Hi...!");
	}
}
