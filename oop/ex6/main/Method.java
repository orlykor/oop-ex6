package oop.ex6.main;
import java.util.ArrayList;



/**
 * The class that represent a method.
 * 
 * @author jonathan2023
 *
 */

public class Method {

	private String name;
	
	private ArrayList<Parameter> parameters;
	
	/**
	 * constructor
	 * @param name - name of the method
	 * @param parameters - parameters of the method
	 */
	public Method(String name, ArrayList<Parameter> parameters) {
		this.name = name;
		this.parameters = parameters;
	}


	/**
	 * 
	 * @return Gives the method's name
	 */
	public String getName() {
		return name;
	}


	/**
	 * 
	 * @return returns the methods parameters
	 */
	public ArrayList<Parameter> getParameters() {
		return parameters;
	}
	

	
	
	
	
	
	
	
}
