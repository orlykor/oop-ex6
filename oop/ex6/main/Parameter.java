package oop.ex6.main;

/**
 * This class represent the Parameter object which holds its type and its name
 * 
 * 
 * @author orlykor12
 *
 */
public class Parameter {
	

	private String type;
	
	private String name;
	
	
	/**
	 * 
	 * @param type - int/double/char/String/boolean
	 * @param name - name of parameter
	 */
	public Parameter(String type, String name){
		
		this.type = type;
		this.name = name;
	}

	
	/**
	 * 
	 * @return the type of the parameter 
	 */
	public String getType() {
		return type;
	}

	/**
	 * 
	 * @return the name of the parameter
	 */
	public String getName() {
		return name;
	}
	
	
	
	
	

}
