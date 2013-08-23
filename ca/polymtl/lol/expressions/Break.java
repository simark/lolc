package ca.polymtl.lol.expressions;

import ca.polymtl.lol.Context;
import ca.polymtl.lol.exceptions.LOLRuntimeEx;
import ca.polymtl.lol.types.Value;

public class Break implements Expression {

	@Override
	public Value evaluate(Context context) throws LOLRuntimeEx {
		context.setBreaking(true);
		return null;
	}

	@Override
	public void debugPrint(int indent) {
		System.err.println(this.toString());
	}

}
