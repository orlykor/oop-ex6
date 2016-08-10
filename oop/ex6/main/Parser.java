package oop.ex6.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.*;
import oop.ex6.blocks.*;
import oop.ex6.variables.*;

/**
 * This class is responsible for reading the given files
 * 
 * 
 * @author orlykor12
 * 
 */
public class Parser {

    Final isVarFinal;

    Matcher matcher;

    int bracketsCounter = 0;

    /** if there are even brackets then its 0 */
    private static final int EVEN_BRACKETS = 0;
    
    /** if there is a while statement */
    private static final String WHILE_CONDITION = "while";
    
    /** if there is a if statement */
    private static final String IF_CONDITION = "if";

    /** The sign of a comment line */
    private static final String COMMENT = "//";

    /** The method sign */
    private static final String METHOD_DECELER = "void";

    /** The final sign */
    private static final String FINAL_DECLARE = "final";
    
    /**The sign of a semicolon */
    private final static String SEMICOLON = ";";
    
    /**The sign of a semicolon */
    private final static String COMMA = ",";
    
    /** The open bracket sign */
    private static final String ROUND_CLOSING_BRACKET = ");";
    
    /** The number of the value group */
    private final static int VALUE = 2;
    
    
    /** The open bracket sign */
    private static final String OPEN_BRACKET = "{";

    /** The close bracket sign */
    private static final String CLOSE_BRACKET = "}";

    /** The int or double regex */
    private final static String INT_OR_DOUBLE = "\\d+(\\.\\d+)?";

    /** The return statement which state the end of the method */
    private static final String END_OF_METHOD = "return;";

    /** The method's parameters regex */
    private static final String METHOD_PARAMS = "[A-Za-z]+\\w*";

    /** The variable regex */
    private static final String PRIMITIVE_TYPES = "(int|double|char|String|boolean)";

    /** The boolean regex */
    private final static String BOOLEAN_REGEX = "true|false|" + INT_OR_DOUBLE + "|(_\\w+|[A-Za-z]+)\\w*";

    /** The method regex */
    private static final String METHOD_REGEX = "\\s*void\\s+(" + METHOD_PARAMS + ")\\s*\\(((\\s*"
	    + PRIMITIVE_TYPES + "\\s*" + METHOD_PARAMS + "\\s*)?(,\\s*" + PRIMITIVE_TYPES + "\\s*"
	    + METHOD_PARAMS + "\\s*)*)\\)\\s*\\{$";

    /** The block regex */
    private final static String BLOCK_REGEX = "(if|while)\\s*\\((\\s*" + BOOLEAN_REGEX
	    + "(\\s*((\\|\\|)|(\\&\\&)\\s*)(" + BOOLEAN_REGEX + "\\s*))*)+\\s*\\)\\s*\\{";

    /** The inner method regex */
    private final static String INNER_METHOD_REGEX = "(" + METHOD_PARAMS
	    + ")\\s*\\(((\\s*(\'.\'|\".*\"|_\\w+|" + METHOD_PARAMS + "|" + INT_OR_DOUBLE
	    + ")\\s*((,\\s*((\'.\'|\".*\"|_\\w+|" + METHOD_PARAMS + "|" + INT_OR_DOUBLE
	    + "|)\\s*)?)?))*)\\)\\s*\\;$";

    private static Pattern primitiveRegex;

    HashMap<String, Method> methodMap;

    static Scope generalScope = new Scope(); 

    ArrayList<Method> methodArray = new ArrayList<>();

    /**
     * The constructor
     */
    public Parser() {

	primitiveRegex = Pattern.compile(PRIMITIVE_TYPES);

	generalScope = new Scope();

	methodArray = new ArrayList<>();

    }

    /**
     * Reads the general part of a method, and saves its variables in the scope
     * and the methods Objects of the methods.
     * 
     * @param file the file to read
     * @throws IOException
     * @throws TypeException
     * @throws IllegalLineException
     * @throws FinalExceptionError
     * @throws BracketsBlockException
     * @throws ReturnMissingException
     * @throws DecelerationErrorException
     * @throws IllegalMethodParamException
     */
    public void firstReading(File file) throws IOException, TypeException, IllegalLineException,
	    FinalExceptionError, BracketsBlockException, ReturnMissingException,
	    DecelerationErrorException, IllegalMethodParamException {
	BufferedReader reader = new BufferedReader(new FileReader(file));
	String line = reader.readLine();
	line = lineTrimmer(line);
	while (line != null) {
	    if (line.startsWith(COMMENT) || line.isEmpty()) {
		line = reader.readLine();
		if (line == null) {
		    break;
		}
		lineTrimmer(line);
	    } else if (line.startsWith(METHOD_DECELER)) {
		MethodBlock methodVariables = new MethodBlock(line, METHOD_REGEX);
		if (methodVariables.isMatch()) {
		    Method method = methodVariables.createNewMethod();
		    methodArray.add(method);
		    bracketsCounter++;
		    while (bracketsCounter != EVEN_BRACKETS) {
			String parentLine = line;
			line = reader.readLine();
			line = lineTrimmer(line);
			if (line == null) {
			    reader.close();
			    throw new BracketsBlockException();
			}
			if (line.contains(CLOSE_BRACKET)) {
			    bracketsCounter--;
			} else if (line.contains(OPEN_BRACKET)) {
			    bracketsCounter++;
			}
			if (bracketsCounter == EVEN_BRACKETS) {
			    parentLine = lineTrimmer(parentLine);
			    if (!parentLine.equals(END_OF_METHOD)) {
				reader.close();
				throw new ReturnMissingException();
			    }
			    line = reader.readLine();
			}
		    }
		} else {
		    reader.close();
		    throw new IllegalLineException();
		}
	    } else {
		checkGeneralLine(generalScope, line, true);
		line = reader.readLine();
	    }
	}

	reader.close();
    }

    /**
     * 
     * Reads the file for the second time, this time in order to handle the methods and what's inside them
     * 
     * 
     * @param file the file to read
     * @throws IOException
     * @throws IllegalLineException
     * @throws TypeException
     * @throws FinalExceptionError
     * @throws BracketsBlockException
     * @throws ReturnMissingException
     * @throws DecelerationErrorException
     * @throws IllegalMethodParamException
     */
    public void secondReading(File file) throws IOException, IllegalLineException, TypeException,
	    FinalExceptionError, BracketsBlockException, ReturnMissingException,
	    DecelerationErrorException, IllegalMethodParamException {

	BufferedReader reader = new BufferedReader(new FileReader(file));
	String innerLine = reader.readLine();
	innerLine = lineTrimmer(innerLine);
	Scope currScope;
	String parentLine = innerLine;
	int methodCounter = 0;

	outerloop: while (innerLine != null) {
	    while (!innerLine.startsWith(METHOD_DECELER)) {
		innerLine = reader.readLine();
		if (innerLine == null) {
		    break outerloop;
		}
		innerLine = lineTrimmer(innerLine);
	    }
	    int counter = 1;

	    Scope methodScope = new Scope(generalScope);

	    methodScope.addMethod(methodArray.get(methodCounter));
	    methodCounter++;
	    currScope = methodScope;
	    innerLine = reader.readLine();
	    innerLine = lineTrimmer(innerLine);

	    while (counter != EVEN_BRACKETS && innerLine != null) {
		innerLine = lineTrimmer(innerLine);
		if (innerLine.isEmpty()) {
		    innerLine = reader.readLine();
		}
		if (innerLine.endsWith(OPEN_BRACKET)) {
		    counter++;
		    if (innerLine.startsWith(WHILE_CONDITION) || innerLine.startsWith(IF_CONDITION)) {

			Blocks block = new Blocks(innerLine, BLOCK_REGEX);
			if (block.checkParams(currScope)) {
			    Scope blockScope = new Scope(currScope);
			    blockScope.addMethod(currScope.getMethod());
			    currScope = blockScope;
			    parentLine = innerLine;
			    innerLine = reader.readLine();
			    innerLine = lineTrimmer(innerLine);
			} else {
			    reader.close();
			    throw new IllegalLineException();
			}
		    } else {
			reader.close();
			throw new IllegalLineException();
		    }

		} else if (innerLine.endsWith(CLOSE_BRACKET)) {
		    counter--;
		    if (counter < 0) {
			reader.close();
			throw new BracketsBlockException();
		    } else if (counter == EVEN_BRACKETS) {
			if (!parentLine.equals(END_OF_METHOD)) {
			    reader.close();
			    throw new ReturnMissingException();
			}
		    }
		    currScope = currScope.getParent();
		    parentLine = innerLine;
		    innerLine = reader.readLine();

		} else if (innerLine.endsWith(SEMICOLON) && !innerLine.equals(END_OF_METHOD)) {
		    checkGeneralLine(currScope, innerLine, false);
		    parentLine = innerLine;
		    innerLine = reader.readLine();
		    innerLine = lineTrimmer(innerLine);

		} else if (innerLine.equals(END_OF_METHOD)) {
		    parentLine = innerLine;
		    innerLine = reader.readLine();
		    innerLine = lineTrimmer(innerLine);
		} else {
		    reader.close();
		    throw new IllegalLineException();
		}

	    }

	}
	reader.close();
    }

    /**
     * 
     * Check the lines in the global(general) scope.
     * 
     * @param currScope the current scope the variables are in
     * @param line the line we read from
     * @param isFirstRead true if its the first time reading the file, false otherwise
     * @throws IllegalLineException
     * @throws TypeException
     * @throws FinalExceptionError
     * @throws DecelerationErrorException
     * @throws IllegalMethodParamException
     */
    private void checkGeneralLine(Scope currScope, String line, boolean isFirstRead)
	    throws IllegalLineException, TypeException, FinalExceptionError, DecelerationErrorException,
	    IllegalMethodParamException {
	// check if matches the final pattern
	if (line.startsWith(FINAL_DECLARE)) {
	    Final typeFinal = new Final(line, currScope);
	    if (typeFinal.isMatch()) {
		typeFinal.takeVariablesOut();
		// Doesn't match, throw exception
	    } else {
		throw new IllegalLineException();
	    }
	} else if (primitiveRegex.matcher(line).find()) { 
	    PrimitiveVariables primitive = new PrimitiveVariables(line, currScope);
	    if (primitive.isMatch()) {
		primitive.takeVariablesOut();
	    } else {
		throw new IllegalLineException();
	    }
	} else if (line.endsWith(ROUND_CLOSING_BRACKET)) {
	    if (!isFirstRead) {
		innerMethodCall(line, currScope);
	    } else {
		throw new IllegalLineException();
	    }
	}
	else {
	    Names names = new Names(line, currScope);
	    if (!names.isMatch()) {
		throw new IllegalLineException();
	    } else {

		names.takeVariablesOut();
	    }
	}
    }

    /**
     * If we are in the second reading then we need to call to the inner method
     * 
     * @param line the line we are at
     * @throws IllegalLineException
     * @throws TypeException
     * @throws IllegalMethodParamException
     */
    public void innerMethodCall(String line, Scope scope) throws IllegalLineException, TypeException,
	    IllegalMethodParamException {
	if (line.endsWith(ROUND_CLOSING_BRACKET)) {
	    String[] valuesArray;
	    MethodBlock methodVariable = new MethodBlock(line, INNER_METHOD_REGEX);
	    if (methodVariable.isMatch()) {

		String nameOfmethod = methodVariable.getName();
		for (int i = 0; i < methodArray.size(); i++) {
		    if (nameOfmethod.equals(methodArray.get(i).getName())) {
			ArrayList<Parameter> matchedMethParameters = methodArray.get(i).getParameters();
			if (methodVariable.getParamteres().isEmpty()) {
			    if (matchedMethParameters.size() != 0) {
				throw new IllegalMethodParamException();
			    }
			} else {
			    valuesArray = methodVariable.getParamteres().split(COMMA); 
			    if (matchedMethParameters.size() != valuesArray.length) {
				throw new IllegalMethodParamException();
			    }
			    for (int j = 0; j < valuesArray.length; j++) {
				if (!CheckForLegalVariables.isVarLegal(CheckForLegalVariables
					.getEnumType(matchedMethParameters.get(j).getType()),
					valuesArray[j], scope)) {
				    throw new IllegalMethodParamException();
				}
			    }
			}
		    }
		}
	    } else {
		throw new IllegalLineException();
	    }
	}
    }

    /**
     * takes a string in the form of ("type parameter*") and returns an
     * arrayList of parameters.
     * 
     * @param methodParamString
     *            - the string to convert
     * @return arrayList of parameters
     * @throws IllegalLineException
     * @throws TypeException
     */
    public static ArrayList<Parameter> getMethodParam(String methodParamString)
	    throws IllegalLineException, TypeException {
	ArrayList<Parameter> result = new ArrayList<>();
	if (methodParamString == null || methodParamString.equals("")) {
	    return result;
	}
	String[] paramArray = methodParamString.split(COMMA);
	for (String paramValues : paramArray) {
	    paramValues = lineTrimmer(paramValues);
	    String[] singleParam = paramValues.split("\\s+");
	    Parameter parameter = new Parameter(singleParam[0], singleParam[1]);
	    if (!result.isEmpty()) {
		int stoper = result.size();
		for (int i = 0; i < stoper; i++) {
		    String insideParam = result.get(i).getName();
		    if (insideParam.equals(parameter.getName())) {
			throw new TypeException();
		    }
		}
		result.add(parameter);
	    } else {
		result.add(parameter);
	    }
	}
	return result;
    }

    /**
     * 
     * @return the scope of the general variables
     */
    public Scope getGeneralScope() {
	return generalScope;
    }

    /**
     * 
     * @return the the methodArray
     */
    public ArrayList<Method> getMethodArray() {
	return methodArray;
    }

    /**
     * returns a value of variable, given as string
     * 
     * @param subPattern pattern of variable
     * @param subMatcher string we want to extract its value
     * @return the value of the variable
     */
    public static String getValue(Pattern subPattern, Matcher subMatcher) {
	String value = null;
	if (subMatcher.group(VALUE) != null) {
	    value = subMatcher.group(VALUE);
	    if (value.contains("=")) {
		value = value.replace("=", "");
		value = Parser.lineTrimmer(value);

	    }
	}
	return value;
    }

    /**
     * a name of variable, given as string
     * 
     * @param subPattern pattern of variable
     * @param subMatcher string we want to extract its name
     * @return the name of the variable
     * @throws IllegalLineException
     */
    public static String getName(Pattern subPattern, Matcher subMatcher, String line)
	    throws IllegalLineException {
	if (subMatcher.matches()) {
	    String name = subMatcher.group(1);
	    return name;
	} else {
	    throw new IllegalLineException();
	}
    }

    /**
     * deletes redundant white spaces.
     * 
     * @param line - line to fix its spaces
     * @return fixed line
     */
    public static String lineTrimmer(String line) {
	if (line != null) {
	    line = line.replaceAll("\\s+", " ");
	    line = line.trim();
	}
	return line;

    }
}
