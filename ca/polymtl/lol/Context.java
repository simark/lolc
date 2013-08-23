package ca.polymtl.lol;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import ca.polymtl.lol.types.Value;

public class Context {
	VariablesScope currentScope;
	BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));

	boolean returning;
	boolean breaking;
	
	Value returnValue;
	
	public void setReturnValue(Value returnValue) {
		this.returnValue = returnValue;
	}
	
	public Value getReturnValue() {
		return returnValue;
	}
	
	public boolean isBreaking() {
		return breaking;
	}
	
	public boolean isReturning() {
		return returning;
	}
	
	public void setBreaking(boolean breaking) {
		this.breaking = breaking;
	}
	
	public void setReturning(boolean returning) {
		this.returning = returning;
	}
	
	public Context() {
		currentScope = new VariablesScope();
	}

	public BufferedReader getInputReader() {
		return inputReader;
	}

	public VariablesScope getCurrentScope() {
		return currentScope;
	}

	public void pushScope() {
		VariablesScope newScope = new VariablesScope(currentScope);
		currentScope = newScope;
	}

	public void popScope() {
		assert (currentScope.parentScope != null);
		currentScope = currentScope.parentScope;
	}
}
