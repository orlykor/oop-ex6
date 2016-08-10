package oop.ex6.variables;
import java.util.regex.*;

import oop.ex6.main.IllegalLineException;
import oop.ex6.main.Parser;
import oop.ex6.main.Scope;

/**
 * This class receives a line that starts with the "final" string
 * 
 * @author orlykor12 
 *
 */
public class Final extends Variables{
	
    
	/**
	 * The constructor
	 * creates the right pattern for this type of class.
	 * 
	 * @param line the line to check the regex with.
	 * @param scope the scope we are in
	 */
	public Final(String line, Scope scope){
		super(line,scope);
		this.pattern = Pattern
				.compile("\\s*(final\\s+)\\s*"+PRIMITIVE_TYPES+"\\s+"+VAR_NAME+"(\\s*=\\s*\\w+)(,\\s*"+VAR_NAME+"(\\s*=\\s*\\w+))*;$");
		this.matcher = pattern.matcher(line);
	}
	
	@Override
	public void takeVariablesOut() throws TypeException, IllegalLineException, FinalExceptionError{
//		if(isMatch()){
		String type = matcher.group(TYPE);
		line = line.substring(line.indexOf(SINGLE_SPACE) + 1); // removes the words final
		line = Parser.lineTrimmer(line);
		line = line.substring(line.indexOf(SINGLE_SPACE) + 1);// removes the type
		line = Parser.lineTrimmer(line);
		String[] listOfVariables = line.split(COMMA);
		createVariablesObject(listOfVariables, type, scope, FINAL);
//		}
		
	}
	

	
	
	
}
