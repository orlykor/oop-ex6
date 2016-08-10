package oop.ex6.main;

import java.util.HashMap;

import oop.ex6.variables.VariablesObject;

/**
 * This class represents the scope for each part of the file
 * 
 * @author orlykor12
 * 
 */
public class Scope {

    private Scope parent;

    HashMap<String, VariablesObject> map;

    Method method;

    /**
     * The constructor that receives the parent scope
     * 
     * @param parent the last scope before this one
     */
    public Scope(Scope parent) {
	this.parent = parent;
	map = new HashMap<>();
    }

    /**
     * The default constructor
     * 
     */
    public Scope() {
	this.parent = null;
	map = new HashMap<>();
    }

    /**
     * add the variables that in the SJavaObject to the hash map of the current scope
     * 
     * @param variavbles the SJavaObject that holds the name and type of the variable.
     */
    public void addVariables(VariablesObject variavbles) {
	map.put(variavbles.getName(), variavbles);

    }

    /**
     * return the parent of the current scope
     * 
     * @return parent the parent of the current scope, if the current scope is the global one,
     *         then return null
     */
    public Scope getParent() {
	if (parent != null) {
	    return parent;
	}
	return null;
    }

    /**
     * Check if the hash map already contains the name of the variable.
     * 
     * @param variavbles the SJavaObject that holds the name and type of the variable.
     * @return true if the hash map contains the key false otherwise.
     */
    public boolean isContainsKey(String name) {
	if (map.containsKey(name))
	    return true;
	if (parent == null)
	    return false;
	return parent.isContainsKey(name);
    }

    /**
     * 
     * @param name the name of the variable to get its value
     * @return the value of the variable name
     */
    public VariablesObject getValue(String name) {
	return map.get(name);

    }

    /**
     * looks for the key in its scope and in its parents scope.
     * 
     * 
     * @param name the name to look for in the scopes
     * @return the variable object matches the key
     */
    public VariablesObject upperKeyValue(String name) {
	if (map.containsKey(name)) {
	    return map.get(name);
	}
	if (parent == null) {
	    return null;
	}
	return parent.upperKeyValue(name);
    }

    /**
     * add method to the current scope it belongs
     * 
     * @param methodToAdd the method to add to the scope
     */
    public void addMethod(Method methodToAdd) {
	method = methodToAdd;
    }

    /**
     * 
     * @return the method belongs to the scope
     */
    public Method getMethod() {
	return method;
    }

    /**
     * check if the current scope contains the given name
     * 
     * @param name the name of the object
     * @return true if the scope contains the name, false otherwise
     */
    public boolean isCurrentScopContain(String name) {
	if (map.containsKey(name))
	    return true;
	return false;
    }

}