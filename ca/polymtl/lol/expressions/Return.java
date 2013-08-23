package ca.polymtl.lol.expressions;

import ca.polymtl.lol.Context;
import ca.polymtl.lol.exceptions.LOLRuntimeEx;
import ca.polymtl.lol.types.Value;
import static ca.polymtl.lol.Utils.tabs;

public class Return implements Expression {
	Expression value;

	public Return(Expression value) {
		super();
		this.value = value;
	}

	@Override
	public Value evaluate(Context context) throws LOLRuntimeEx {
		context.setReturning(true);

		Value ret = value.evaluate(context);

		System.err.println("Returning value " + ret);

		context.setReturnValue(ret);

		return null;
	}

	@Override
	public void debugPrint(int indent) {
		tabs(indent);
		System.err.println("Return statement");
		value.debugPrint(indent + 1);
	}

}
