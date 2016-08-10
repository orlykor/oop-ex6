package oop.ex6.variables;


/**
 * This class is responsible for the final type errors.
 * if a there was an attempt to change the value of a final variable 
 * 
 * @author orlykor12
 *
 */
public class FinalExceptionError extends Exception{

	/*
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs a new exception with null as its detail message.
	 */
	public FinalExceptionError() {
		
	}
	
	/**
	 * Constructs a new exception with the specified detail message
	 * 
	 * @param message
	 */
	public  FinalExceptionError(String message) {
		super(message);
	}
}