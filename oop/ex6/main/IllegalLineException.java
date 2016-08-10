package oop.ex6.main;
/**
 * A bad line format has been entered
 * 
 * @author orlykor12
 *
 */
public class IllegalLineException extends Exception {

	
	/*
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs a new exception with null as its detail message.
	 */
	public IllegalLineException() {
		
	}
	
	/**
	 * Constructs a new exception with the specified detail message
	 * 
	 * @param message
	 */
	public IllegalLineException(String message) {
		super(message);
	}
}
