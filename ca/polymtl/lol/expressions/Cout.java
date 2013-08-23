package ca.polymtl.lol.expressions;

import ca.polymtl.lol.Context;
import ca.polymtl.lol.exceptions.LOLRuntimeEx;
import ca.polymtl.lol.types.StringValue;
import ca.polymtl.lol.types.Value;
import static ca.polymtl.lol.Utils.tabs;

public class Cout implements Expression {

	Expression exp;

	public Cout(Expression exp) {
		this.exp = exp;
	}

	@Override
	public Value evaluate(Context context) throws LOLRuntimeEx {
		StringValue strVal = exp.evaluate(context).toStringValue();
		System.out.print(strVal.getValue());

		return null;
	}

	@Override
	public void debugPrint(int indent) {
		tabs(indent);
		System.err.println("Console output");
		exp.debugPrint(indent + 1);
	}

}
