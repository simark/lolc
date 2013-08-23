package ca.polymtl.lol;

import java.util.HashMap;
import java.util.Map;

import ca.polymtl.lol.exceptions.LOLRuntimeEx;
import ca.polymtl.lol.types.NullValue;
import ca.polymtl.lol.types.Value;

public class VariablesScope {
	final VariablesScope parentScope;
	final Map<String, Value> variables = new HashMap<String, Value>();

	public VariablesScope() {
		this(null);
	}

	public VariablesScope(VariablesScope parent) {
		this.parentScope = parent;
		defineVariable("IT", new NullValue());
	}

	/**
	 * Lookups a variable in the scope and the parent scopes.
	 * 
	 * @param name
	 *            The variable name
	 * @return The value of the variable if it exists, null otherwise.
	 */
	public Value lookupVariable(String name) {
		if (variables.containsKey(name)) {
			return variables.get(name);
		} else if (parentScope != null) {
			return parentScope.lookupVariable(name);
		} else {
			return null;
		}
	}
	
	public void defineVariable(String name, Value value) {
		this.variables.put(name, value);
	}
	
	public void assignExistingVariable(String name, Value val) throws LOLRuntimeEx {
		if (variables.containsKey(name)) {
			variables.put(name, val);
		} else if (parentScope != null) {
			parentScope.assignExistingVariable(name, val);
		} else {
			throw new LOLRuntimeEx("Assignation failed, variable " + name + " does not exist.");
		}
	}
}
