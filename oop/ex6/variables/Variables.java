package oop.ex6.variables;

import java.util.ArrayList;
import java.util.regex.*;

import oop.ex6.main.*;

/**
 * This class is the super class of all the global variables that can be.
 * 
 * 
 * @author orlykor12
 * 
 */
public abstract class Variables {

    /** The primitive types regex */
    protected static final String PRIMITIVE_TYPES = "(int|double|char|String|boolean)";
    
    /**The final check regex*/
    protected final String FINAL_STATEMENT = "\\s*(final\\s+)\\s*";

    /** The name of the variable regex */
    protected static final String VAR_NAME = "(_\\w+|[A-Za-z]+)\\w*";
    
    /** The value of the variable regex */
    protected static final String VAR_VALUE = "(\\s*=\\s*\\w+)";

    /** The sign comma */
    public final String COMMA = ",";

    /** Representing a single space */
    public final String SINGLE_SPACE = " ";

    /** The number of the type group */
    public final int TYPE = 2;

    /** The number of the name group */
    public final int NAME = 1;

    /** The sign of a semicolon */
    public final String SEMICOLON = ";";

    /** The sign of an empty space */
    public final String EMPTY = "";

    /** The variable is not declared as final */
    public final boolean NOT_FINAL = false;

    /** The variable is declared as final */
    public final boolean FINAL = true;
    
    
    protected Matcher matcher;
    
    protected Pattern pattern;
    
    protected String line;
    
    protected Scope scope;


    /**
     * The constructor
     * 
     * @param line the line to check the regex with
     */
    public Variables(String line, Scope scope) {
	this.line = line;
	this.scope = scope;
    }

    /**
     * check if the line is legal, if it is then it split the line in order to create a
     * variable object.
     * 
     * @param line the line to check the regex with
     * @throws TypeException
     * @throws IllegalLineException
     * @throws FinalExceptionError
     * @throws DecelerationErrorException 
     */
    abstract public void takeVariablesOut() throws TypeException, IllegalLineException, FinalExceptionError, DecelerationErrorException;

    /**
     * creates an object variables which holds inside him the name of the variable, its type
     * if it has one his scope and if its final or not.
     * 
     * @param type the type of the variable
     * @throws TypeException
     * @throws IllegalLineException
     * @throws FinalExceptionError 
     */
    public void createVariablesObject(String[] listOfFinalLine, String type, Scope scope, boolean isFinal)
	    throws TypeException, IllegalLineException, FinalExceptionError{
	for (String subLine : listOfFinalLine) {

	    Pattern subPattern = Pattern.compile("\\s*"+VAR_NAME+"(\\s*=.*)?(;)?");
	    Matcher subMatcher = subPattern.matcher(subLine);
	    String subName = Parser.getName(subPattern, subMatcher, subLine);//the name of the variable
	    String subValue = Parser.getValue(subPattern, subMatcher); // take the value out-null
	    subName = Parser.lineTrimmer(subName); 

	    if (subValue != null) { //cut the ;
		if (subValue.endsWith(SEMICOLON)) {
		    subValue = subValue.replace(SEMICOLON, EMPTY);
		}
		subValue = Parser.lineTrimmer(subValue);
	    }
	    if (scope.getMethod() != null) { //if we read a method
		ArrayList<Parameter> methodPar = scope.getMethod().getParameters(); //the method's params
		for (Parameter param : methodPar) {
		    if (param.getType().equals(type)) { //check if any of  the types of the params matches this one
			if (param.getName().equals(subName)) { //also the name
			    throw new IllegalLineException();
			}
		    } else if (param.getName().equals(subName)) { //now check the names
			if (!param.getType().equals(type)) { //different type- wrong!
			    throw new IllegalLineException();
			}
		    }

		}
		scope.addVariables(new VariablesObject(subName, CheckForLegalVariables.getEnumType(type),
			subValue, isFinal, scope));
	    }

	    else{
	    scope.addVariables(new VariablesObject(subName, CheckForLegalVariables.getEnumType(type),
		    subValue, isFinal, scope));
	}
	}
    }

    /**
     * 
     * @return true if the line matches the given regex, false otherwise
     * @throws IllegalLineException
     */
    public boolean isMatch() throws IllegalLineException {
	if (matcher.matches())
	    return true;
	return false;
    }

}
