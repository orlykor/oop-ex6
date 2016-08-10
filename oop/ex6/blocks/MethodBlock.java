package oop.ex6.blocks;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//import java.util.regex.*;

import oop.ex6.main.*;
import oop.ex6.variables.TypeException;

/**
 * This class receives a line that represents a method line.
 * 
 * @author orlykor12
 * 
 */
public class MethodBlock {

    private Pattern pattern;
    
    private Matcher matcher;
    
    protected String line;

    /** The number of the group that contains the parameters*/
    private final static int GROUP_OF_PARAMS = 2;
    
    private final static int METHOD_NAME = 1;

    
    
    /**
     * The constructor
     * 
     * @param line the line the string "void" starts with
     * @param regex the regex matching for the method line
     */
    public MethodBlock(String line, String regex) {
	this.pattern = Pattern.compile(regex);
	this.matcher = pattern.matcher(line);
	this.line = line;
    }

    /**
     * if the regex matches the method line then it creates a method object containing the method's and 
     * the parameters it holds.
     * 
     * @return Method a new method object
     * @throws TypeException
     * @throws IllegalLineException
     */
    public Method createNewMethod() throws TypeException, IllegalLineException {

	    String methodName = matcher.group(METHOD_NAME);
	    String methodParamString = matcher.group(GROUP_OF_PARAMS);
	    ArrayList<Parameter> methodParameters = Parser.getMethodParam(methodParamString);
	    return new Method(methodName, methodParameters);
    }

    /**
     * Check if the regex match the given method line
     * 
     * @return true if the line matches the given regex, false otherwise
     * @throws IllegalLineException
     */
    public boolean isMatch() throws IllegalLineException {
	if (matcher.find())
	    return true;
	return false;
    }

    /**
     * get the name of the method
     * 
     * @return methodName the name of the method
     */
    public String getName() {
	String methodName = matcher.group(METHOD_NAME);
	return methodName;
    }

    /**
     * get the method's parameters
     * 
     * @return methodParamString the method's parameters
     */
    public String getParamteres() {
	String methodParamString = matcher.group(2);
	return methodParamString;
    }

}
