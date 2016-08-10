package oop.ex6.main;



/**
 *Illegal parameters have been entered to an existing method.
 * 
 * @author orlykor12
 *
 */

public class IllegalMethodParamException extends Exception{


	/*
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs a new exception with null as its detail message.
	 */
	public IllegalMethodParamException() {
		
	}
	
	/**
	 * Constructs a new exception with the specified detail message
	 * 
	 * @param message
	 */
	public  IllegalMethodParamException(String message) {
		super(message);
	}
}