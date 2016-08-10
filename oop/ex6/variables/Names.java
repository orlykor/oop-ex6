package oop.ex6.variables;

import java.util.ArrayList;
import java.util.regex.Pattern;

import oop.ex6.main.IllegalLineException;
import oop.ex6.main.Parameter;
import oop.ex6.main.Parser;
import oop.ex6.main.Scope;

/**
 * This class receives a line that starts with anything but, a primitive type, a final
 * Statement and a void statement.
 * 
 * @author orlykor12
 * 
 */
public class Names extends Variables {

    
    /** The name regex*/    
    private static final String NAME_REGEX= "\\s*" + VAR_NAME + VAR_VALUE + "?(,\\s*" + VAR_NAME + VAR_VALUE + "?)*;$";
    
    
    
    /**
     * 
     * The constructor
     * 
     * creates the right pattern for this type of class.
     * 
     * @param line the line to check the regex with.
     * @param scope the scope we are in
     */
    public Names(String line, Scope scope) {
	super(line, scope);
	this.pattern = Pattern
		.compile(NAME_REGEX);
	this.matcher = pattern.matcher(line);
    }

    @Override
    public void takeVariablesOut() throws TypeException, IllegalLineException, FinalExceptionError {
	String name = matcher.group(NAME);
	String value = Parser.getValue(pattern, matcher);
	if (scope.isContainsKey(name)) {
	    if (scope.getValue(name) != null) {
		scope.getValue(name).setValue(value, scope.getValue(name).getType(), scope, name);
	    }
	    else if (scope.upperKeyValue(name).isFinal()==true){
		throw new FinalExceptionError();
	    }
	    scope.addVariables((new VariablesObject(name, scope.upperKeyValue(name).getType(), value,
		    false, scope)));
	} else if (scope.getMethod() != null) {
	    ArrayList<Parameter> methodPar = scope.getMethod().getParameters();
	    for (Parameter param : methodPar) {
		if (param.getName().equals(name)) {
		    scope.addVariables((new VariablesObject(name, CheckForLegalVariables.getEnumType(param.getType()), value, NOT_FINAL, scope)));
		}
	    }
	} else {
	    throw new TypeException();
	}
    }

}
