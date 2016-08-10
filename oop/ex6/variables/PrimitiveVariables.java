package oop.ex6.variables;


import java.util.regex.*;
import oop.ex6.main.IllegalLineException;
import oop.ex6.main.Parser;
import oop.ex6.main.Scope;

/**
 * This class receives a line that starts with a primitive type
 * 
 * @author orlykor12
 * 
 */
public class PrimitiveVariables extends Variables {

 

    /** The number of the type group */
    private final int NAME_GROUP = 2;

    /** The number of the name group */
    private final int TYPE_GROUP = 1;

    
    
    /**
     * The constructor
     * creates the right pattern for this type of class.
     * 
     * @param line the line to check the regex with.
     * @param scope the scope we are in
     */
    public PrimitiveVariables(String line, Scope scope) {
	super(line, scope);
	this.pattern = Pattern
		.compile("\\s*"+PRIMITIVE_TYPES+"\\s+"+VAR_NAME+"(\\s*=.*)?(,\\s*"+VAR_NAME+"(\\s*=.*)?)*;$");
	this.matcher = pattern.matcher(line);

    }

    @Override
    public void takeVariablesOut() throws TypeException, IllegalLineException, FinalExceptionError, DecelerationErrorException {
	    String type = matcher.group(TYPE_GROUP);
	    String name = matcher.group(NAME_GROUP);
	    line = line.substring(line.indexOf(SINGLE_SPACE) + 1);// removes the type
	    line = Parser.lineTrimmer(line);
	    String[] listOfFinalLine = line.split(COMMA);
	    if (scope.isCurrentScopContain(name)){
		throw new DecelerationErrorException();
	    }
	    createVariablesObject(listOfFinalLine, type, scope, NOT_FINAL);
	}
}
