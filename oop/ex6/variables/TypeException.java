package oop.ex6.variables;
/**
 * This class is responsible for the errors of the variables type error.
 * if the variable doesn't match its declared type.
 * 
 * @author orlykor12
 *
 */
public class TypeException extends Exception{
	
	/*
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs a new exception with null as its detail message.
	 */
	public TypeException() {
		
	}
	
	/**
	 * Constructs a new exception with the specified detail message
	 * 
	 * @param message
	 */
	public  TypeException(String message) {
		super(message);
	}
}