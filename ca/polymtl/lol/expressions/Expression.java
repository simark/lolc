package ca.polymtl.lol.expressions;

import ca.polymtl.lol.Context;
import ca.polymtl.lol.exceptions.LOLRuntimeEx;
import ca.polymtl.lol.types.Value;

/**
 * An expression is something that can be evaluated or called.
 */
public interface Expression {
	abstract Value evaluate(Context context) throws LOLRuntimeEx;

	abstract void debugPrint(int indent);
}
