package ca.polymtl.lol.expressions;

import ca.polymtl.lol.Context;
import ca.polymtl.lol.exceptions.LOLRuntimeEx;
import ca.polymtl.lol.types.BoolValue;
import ca.polymtl.lol.types.Value;
import static ca.polymtl.lol.Utils.tabs;

public class UnaryOp implements Expression {

	public UnaryOp(Expression exp) {
		super();
		this.exp = exp;
	}

	Expression exp;

	@Override
	public Value evaluate(Context context) throws LOLRuntimeEx {
		Value val = exp.evaluate(context);

		return new BoolValue(!val.toBoolValue().getValue());
	}

	@Override
	public void debugPrint(int indent) {
		tabs(indent);
		System.err.println("UnaryOp");
		exp.debugPrint(indent + 1);
	}
}
