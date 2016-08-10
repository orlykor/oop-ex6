package oop.ex6.variables;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oop.ex6.main.IllegalLineException;
import oop.ex6.main.Parameter;
import oop.ex6.main.Parser;
import oop.ex6.main.Scope;

/**
 * This class checks if the variables matches the types they initialized with
 * 
 * 
 * @author orlykor12
 * 
 */
public class CheckForLegalVariables {

    /**Represent the int enum*/
    private final static String INT = "int";

    /**Represent the double enum*/
    private final static String DOUBLE = "double";

    /**Represent the char enum*/
    private final static String CHAR = "char";

    /**Represent the String enum*/
    private final static String STRING = "String";

    /**Represent the boolean enum*/
    private final static String BOOLEAN = "boolean";

    /**Represent the int Regex*/
    private final static String INT_REGEX = "(-)?\\d+";

    /**Represent the doble Regex*/
    private final static String DOUBLE_REGEX = INT_REGEX + "(\\.\\d+)?";

    /**Represent the string Regex*/
    private final static String STRING_REGEX = "\".*\"";

    /**Represent the char Regex*/
    private final static String CHAR_REGEX = "\'.\'";

    /**Represent the boolean Regex*/
    private final static String BOOLEAN_REGEX = "true|false|(-)?\\d+(\\.\\d+)?";
    
    /**The sign of a semicolon */
    private final static String SEMICOLON = ";";
    

    /**The sign of an empty space */
    private final static String EMPTY = "";

    /**
     * 
     * check if the value of the variable matches the type
     * 
     * 
     * @param type the type the variable has
     * @param value the value of the variable
     * @param scope the scope the variable is in
     * @return true if it's a legal variable, false otherwise.
     * @throws TypeException
     * @throws IllegalLineException 
     */
    public static boolean isVarLegal(VariableType type, String value, Scope scope) throws TypeException, IllegalLineException {
	// if the name already exist then check if its value matches the type.
	if (value != null) {
	    if (value.endsWith(SEMICOLON)) {
		value = value.replace(SEMICOLON, EMPTY);
	    }
	    if (scope.isContainsKey(value)) {
		if (scope.upperKeyValue(value).getValue()==null){
		    throw new IllegalLineException();
		}
		return scope.upperKeyValue(value).getType() == type;
	    } else if (scope.getMethod() != null){
		ArrayList<Parameter> methodPar = scope.getMethod().getParameters();
		for (Parameter param: methodPar){
		    if (param.getName().equals(value)){
			return true;
		    }
		}
	    }

	    switch (type) {
	    case INT:
		if (createPattern(value, type, INT_REGEX))
		    return true;
		return false;
	    case DOUBLE:
		if (createPattern(value, type, DOUBLE_REGEX))
		    return true;
		return false;
	    case STRING:
		if (createPattern(value, type, STRING_REGEX))
		    return true;
		return false;
	    case CHAR:
		if (createPattern(value, type, CHAR_REGEX))
		    return true;
		return false;
	    case BOOLEAN:
		if (createPattern(value, type, BOOLEAN_REGEX))
		    return true;
		return false;
	    default:
		throw new TypeException();
	    }
	}
	return true;
    }

    
    /**
     * Get the matched enum of the type string.
     * 
     * @param type the type of the variable
     * @return an VariableType object with the right type
     * @throws TypeException
     */
    public static VariableType getEnumType(String type) throws TypeException {
	if (type.equals(INT)) {
	    return VariableType.INT;
	} else if (type.equals(DOUBLE)) {
	    return VariableType.DOUBLE;
	} else if (type.equals(CHAR)) {
	    return VariableType.CHAR;
	} else if (type.equals(STRING)) {
	    return VariableType.STRING;
	} else if (type.equals(BOOLEAN)) {
	    return VariableType.BOOLEAN;
	}

	throw new TypeException();

    }

    /*
     * Creates the pattern according to the given regex.
     * @param value the value of the variable
     * @param type the type of the variable
     * @param regex the regex matching the value we want to check
     * @return true if there's a match between the value and the regex.
     */
    private static boolean createPattern(String value, VariableType type, String regex) {
	value = Parser.lineTrimmer(value);
	Pattern p = Pattern.compile(regex);
	Matcher matcher = p.matcher(value);
	if (matcher.matches())
	    return true;
	return false;
    }

}
