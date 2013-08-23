package ca.polymtl.lol.expressions;

import ca.polymtl.lol.Context;
import ca.polymtl.lol.exceptions.LOLRuntimeEx;
import ca.polymtl.lol.types.Value;
import static ca.polymtl.lol.Utils.tabs;

public class Assign implements Expression {

	String name;
	Expression value;

	public Assign(String name, Expression value) {
		super();
		this.name = name;
		this.value = value;
	}

	@Override
	public Value evaluate(Context context) throws LOLRuntimeEx {
		System.err.println("Evaluating assign of " + name);
		Value val = value.evaluate(context);
		
		context.getCurrentScope().assignExistingVariable(name, val);
	

		return null;
	}

	@Override
	public void debugPrint(int indent) {
		tabs(indent);
		System.err.println("Assign of var " + name);
		value.debugPrint(indent + 1);
	}

}
