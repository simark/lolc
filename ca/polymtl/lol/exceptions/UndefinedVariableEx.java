package ca.polymtl.lol.exceptions;

public class UndefinedVariableEx extends LOLRuntimeEx {
	private static final long serialVersionUID = -2296907979292060287L;
	
	String variableName;

	public UndefinedVariableEx(String variableName) {
		this.variableName = variableName;
	}

	@Override
	public String toString() {
		return "Undefined variable " + variableName;
	}
}
