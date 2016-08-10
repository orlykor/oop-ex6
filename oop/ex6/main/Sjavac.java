package oop.ex6.main;

import java.io.File;
import java.io.IOException;

import oop.ex6.variables.BracketsBlockException;
import oop.ex6.variables.DecelerationErrorException;
import oop.ex6.variables.FinalExceptionError;
import oop.ex6.variables.TypeException;

/**
 * 
 * The main class which from the program is running
 * 
 * 
 * @author orlykor12
 * 
 */
public class Sjavac {

    /**The illegal code error representing number */
    private final static String ILLEGAL_CODE_ERROR = "1";
    
    /**The file error representing number */
    private final static String IO_FILE_ERROR = "2";

    /**The legal code representing number */
    private final static String LEGAL_CODE = "0";
    
    /**
     * The manager method that runs the Sjava code check
     * 
     * @param args
     */
    public static void main(String[] args) {
	try {
	    File file = new File(args[0]);
	    Parser parser = new Parser();
	    parser.firstReading(file);
	    if (!parser.getMethodArray().isEmpty()) {
		parser.secondReading(file);
	    }
	    System.out.println(LEGAL_CODE);

	} catch (TypeException e) {
	    System.out.println(ILLEGAL_CODE_ERROR);
	    System.err.println("Not matching variable's type");
	} catch (IllegalLineException e) {
	    System.out.println(ILLEGAL_CODE_ERROR);
	    System.err.println("An illegal Sjava line has been entered");
	} catch (FinalExceptionError e) {
	    System.out.println(ILLEGAL_CODE_ERROR);
	    System.err.println("Tried to change a final variable value");
	} catch (IllegalMethodParamException e) {
	    System.out.println(ILLEGAL_CODE_ERROR);
	    System.err.println("The parameters not matching the method's parameters that had been defined");
	} catch (ReturnMissingException e) {
	    System.out.println(ILLEGAL_CODE_ERROR);
	    System.err.println("Missing a return statement");
	} catch (BracketsBlockException e) {
	    System.out.println(ILLEGAL_CODE_ERROR);
	    System.err.println("Syntex Error: Unmatching brackets have been found");
	} catch (DecelerationErrorException e) {
	    System.out.println(ILLEGAL_CODE_ERROR);
	    System.err.println("This variable has already been declared");
	} catch (IOException e) {
	    System.out.println(IO_FILE_ERROR);
	    System.err.println("Problem occurred with the file");
	}
    }
}