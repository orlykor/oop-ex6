package oop.ex6.variables;

import oop.ex6.main.IllegalLineException;
import oop.ex6.main.Scope;

/**
 * This class creates the variables object which holds the name of the variable, the type, the value, if its
 * final and its scope.
 * 
 * @author orlykor12
 * 
 */
public class VariablesObject {

    private String name;

    private String value;

    private boolean isFinal;

    private Scope scope;

    private VariableType type;

    CheckForLegalVariables variable;

    /**
     * constructor
     * 
     * @param name - the name of the object
     * @param type - one of int, String, boolean, char, double
     * @param value - the value that the object keeps
     * @param isFinal - true iff the value can't be changed.
     * @throws IllegalLineException
     * @throws FinalExceptionError
     */
    public VariablesObject(String name, VariableType type, String value, boolean isFinal, Scope scope)
	    throws TypeException, IllegalLineException, FinalExceptionError {
	if (CheckForLegalVariables.isVarLegal(type, value, scope)) {
	    this.name = name;
	    this.type = type;
	    this.value = value;
	    this.isFinal = isFinal;
	    this.scope = scope;
	} else {
	    throw new TypeException();
	}
    }

    /**
     * set a new value to the variable name.
     * if the new value doesn't match the type, then throw typeException
     * 
     * @param value the new value to set
     * @param type the type of the variable
     * @throws TypeException
     * @throws IllegalLineException
     */
    public void setValue(String value, VariableType type, Scope scope, String name)
	    throws FinalExceptionError, TypeException, IllegalLineException {
	if (!scope.getValue(name).isFinal) {
	    if (CheckForLegalVariables.isVarLegal(type, value, scope)) {
		this.value = value;
	    } else {
		throw new FinalExceptionError();
	    }
	}
	throw new TypeException();
    }

    /**
     * getter for the object's name
     * 
     * @return the object's name
     */
    public String getName() {
	return name;
    }

    /**
     * getter for the object's type
     * 
     * @param the object's type
     */

    public VariableType getType() {
	return this.type;
    }

    /**
     * getter for the object's value
     * 
     * @param the object's value
     */
    public String getValue() {
	return value;
    }

    /**
     * getter for the object's "final" status
     * 
     * @param true iff the object value is final and can't be changed
     */
    public boolean isFinal() {
	return isFinal;
    }

    /**
     * 
     * @return the current scope
     */
    public Scope getScope() {
	return this.scope;
    }

}
