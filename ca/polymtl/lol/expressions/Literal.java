package ca.polymtl.lol.expressions;

import ca.polymtl.lol.Context;
import ca.polymtl.lol.exceptions.LOLRuntimeEx;
import ca.polymtl.lol.types.Value;
import static ca.polymtl.lol.Utils.tabs;

public class Literal implements Expression {

	final Value value;

	public Literal(Value value) {
		this.value = value;
	}

	@Override
	public Value evaluate(Context context) throws LOLRuntimeEx {
		System.err.println("Evaluating literal " + value);
		return value;
	}

	@Override
	public void debugPrint(int indent) {
		tabs(indent);
		System.err.println("Literal value " + value.toString());
	}

}
