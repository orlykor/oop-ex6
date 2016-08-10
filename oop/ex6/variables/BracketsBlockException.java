package oop.ex6.variables;



/**
 * This class is responsible for the  brackets errors.
 * if there's missing a bracket or there's too many this exception is called
 * 
 * @author orlykor12
 *
 */
public class BracketsBlockException extends Exception{


	/*
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs a new exception with null as its detail message.
	 */
	public BracketsBlockException() {
		
	}
	
	/**
	 * Constructs a new exception with the specified detail message
	 * 
	 * @param message
	 */
	public  BracketsBlockException(String message) {
		super(message);
	}
}