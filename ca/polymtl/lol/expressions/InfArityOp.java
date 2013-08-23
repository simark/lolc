package ca.polymtl.lol.expressions;

import java.util.Vector;
import static ca.polymtl.lol.Utils.tabs;

import ca.polymtl.lol.Context;
import ca.polymtl.lol.InfArityOpType;
import ca.polymtl.lol.exceptions.LOLRuntimeEx;
import ca.polymtl.lol.types.BoolValue;
import ca.polymtl.lol.types.StringValue;
import ca.polymtl.lol.types.Value;

public class InfArityOp implements Expression {

	private final InfArityOpType type;
	Vector<Expression> expressions = new Vector<Expression>();

	public InfArityOp(InfArityOpType type) {
		this.type = type;
	}

	public void addExpression(Expression e) {
		this.expressions.add(e);
	}

	@Override
	public Value evaluate(Context context) throws LOLRuntimeEx {
		if (type == InfArityOpType.ALL) {
			/* Logical and */
			for (Expression e : expressions) {
				BoolValue res = e.evaluate(context).toBoolValue();
				if (!res.getValue()) {
					return new BoolValue(false);
				}
			}

			return new BoolValue(true);
		} else if (type == InfArityOpType.ANY) {
			/* Logical or */
			for (Expression e : expressions) {
				BoolValue res = e.evaluate(context).toBoolValue();
				if (res.getValue()) {
					return new BoolValue(true);
				}
			}

			return new BoolValue(false);
		} else if (type == InfArityOpType.CONCAT) {
			/* Concatenation */
			StringBuilder builder = new StringBuilder();

			for (Expression e : expressions) {
				StringValue res = e.evaluate(context).toStringValue();
				builder.append(res.getValue());
			}

			return new StringValue(builder.toString());
		}

		assert (false);
		return null;
	}

	@Override
	public void debugPrint(int indent) {
		tabs(indent);
		System.err.println("Infinite arity op");

		for (Expression e : expressions) {
			e.debugPrint(indent + 1);
		}
	}

}
