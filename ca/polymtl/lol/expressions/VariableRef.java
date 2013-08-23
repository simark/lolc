package ca.polymtl.lol.expressions;

import ca.polymtl.lol.Context;
import ca.polymtl.lol.exceptions.LOLRuntimeEx;
import ca.polymtl.lol.exceptions.UndefinedVariableEx;
import ca.polymtl.lol.types.Value;
import static ca.polymtl.lol.Utils.tabs;
public class VariableRef implements Expression {

	
	final String variableName;
	
	public VariableRef(String name) {
		this.variableName = name;
	}
	
	@Override
	public Value evaluate(Context context) throws LOLRuntimeEx {
		System.err.println("Evaluating variable call of " + variableName);
		Value value = context.getCurrentScope().lookupVariable(variableName);
		
		if (value == null) {
			throw new UndefinedVariableEx(variableName);
		}
		
		return value;
	}
	
	@Override
	public void debugPrint(int indent) {
		tabs(indent);
		System.err.println("Reference to var " + variableName);
	}

}
