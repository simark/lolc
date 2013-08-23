package ca.polymtl.lol;

import java.util.HashMap;
import java.util.Map;

import ca.polymtl.lol.expressions.Function;

public abstract class FunctionsScope {
	final FunctionsScope parentScope;
	final Map<String, Function> functions = new HashMap<String, Function>();

	public FunctionsScope(FunctionsScope parent) {
		this.parentScope = parent;
	}

	public Function lookupFunction(String name) {
		if (functions.containsKey(name)) {
			return functions.get(name);
		} else if (parentScope != null) {
			return parentScope.lookupFunction(name);
		} else {
			return null;
		}
	}

	public void defineFunction(String name, Function function) {
		System.err.println("Function " + function.getFullName() + " defined in " + this.getFullName());
		this.functions.put(name, function);
	}
	
	public abstract String getName();
	public String getFullName() {
		if (parentScope != null) {
			return parentScope.getFullName() + "." + getName();
		} else {
			return getName();
		}
	}
	
	public void clearFunctions() {
		functions.clear();
	}
}
