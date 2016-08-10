package oop.ex6.variables;



/**
 * This class is responsible for the deceleration errors.
 * if a variable is created with a certain deceleration and then its declared again with the same or 
 * a different deceleration. 
 * 
 * @author orlykor12
 *
 */
public class DecelerationErrorException extends Exception{ 





	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs a new exception with null as its detail message.
	 */
	public DecelerationErrorException() {
		
	}
	
	/**
	 * Constructs a new exception with the specified detail message
	 * 
	 * @param message
	 */
	public  DecelerationErrorException(String message) {
		super(message);
	}
}