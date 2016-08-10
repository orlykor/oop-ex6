package oop.ex6.main;




/**
 *  This exception is called when a method is closed without the "return;" statement.
 * 
 * 
 * @author orlykor12
 *
 */
public class ReturnMissingException extends Exception{


	/*
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs a new exception with null as its detail message.
	 */
	public ReturnMissingException() {
		
	}
	
	/**
	 * Constructs a new exception with the specified detail message
	 * 
	 * @param message
	 */
	public  ReturnMissingException(String message) {
		super(message);
	}
}