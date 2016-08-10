package oop.ex6.blocks;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oop.ex6.main.*;
import oop.ex6.variables.*;

/**
 * This class is responsible to check if the lines which starts with the strings "while" or "if" are in
 * the correct form.
 * if it is, then check if the variables in them are legal.
 * 
 * @author orlykor12
 * 
 */
public class Blocks {

    private Pattern pattern;
    
    private Matcher matcher;
    
    public String line;

    /** The number of the group that contains the parameters*/
    private final static int GROUP_OF_PARAMS = 2;
    
    /** The sign OR*/
    private final static String OR_SIGN = "||";
    
    /** The sign AND*/
    private final static String AND_SIGN = "&&";
    
    /**The sign comma */
    private final static String COMMA = ",";

    /**The sign closing bracket*/
    private final static String BRACKETS = ")";
    
    /**The sign of an empty space */
    private final static String EMPTY = "";
    
    /**The boolean sign */
    private final static String BOOLEAN = "boolean";
    
    /**
     * The constructor
     * 
     * @param line
     * @param regex
     */
    public Blocks(String line, String regex) {

	this.pattern = Pattern.compile(regex);
	this.matcher = pattern.matcher(line);
	this.line = line;
    }

    /**
     * checks if the inserted parameters are legal: if they were already declared in the file, then check
     * if the type matches to the variable. if not matched then return false.
     * 
     * @param scope the scope the blocks (if or while) are in
     * @return true if the parameters are legal false otherwise.
     * @throws TypeException
     * @throws IllegalLineException
     */
    public boolean checkParams(Scope scope) throws TypeException, IllegalLineException {
	if (matcher.find()) {
	    String parameters = matcher.group(GROUP_OF_PARAMS);
	    parameters = parameters.replace(AND_SIGN, COMMA);
	    parameters = parameters.replace(OR_SIGN, COMMA);
	    if (parameters.endsWith(BRACKETS)){
		parameters = parameters.replace(BRACKETS, EMPTY);
	    }
	    String[] names = parameters.split(COMMA);
	    for (String var : names) {
		var = Parser.lineTrimmer(var);
		if (!CheckForLegalVariables.isVarLegal(CheckForLegalVariables.getEnumType(BOOLEAN), var,
			scope)) {
		    return false;
		}

	    }return true;
	}
	return false;

    }


}
