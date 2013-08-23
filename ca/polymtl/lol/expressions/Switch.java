package ca.polymtl.lol.expressions;

import java.util.Vector;

import ca.polymtl.lol.Context;
import ca.polymtl.lol.FunctionsScope;
import ca.polymtl.lol.exceptions.LOLRuntimeEx;
import ca.polymtl.lol.types.Value;

public class Switch extends FunctionsScope implements Expression {

	Vector<Expression> conds = new Vector<Expression>();
	Vector<Vector<Expression>> expressions = new Vector<Vector<Expression>>();

	Vector<Expression> defaultExp = null;

	public Switch(FunctionsScope parent) {
		super(parent);
	}

	public void addCase(Expression cond, Vector<Expression> exp) {
		expressions.add(exp);
		conds.add(cond);
	}

	public void setDefaultExp(Vector<Expression> exp) {
		defaultExp = exp;
	}

	private void evaluateExpressions(Vector<Expression> exp, Context ctx) throws LOLRuntimeEx {
		ctx.pushScope();

		for (Expression e : exp) {
			Value ret = e.evaluate(ctx);

			if (ctx.isReturning()) {
				System.err.println("Return passing through switch");
				break;
			}

			if (ctx.isBreaking()) {
				System.err.println("Breaking switch");
				ctx.setBreaking(false);
				break;
			}

			/*
			 * If the expression returned something, put it in the IT variable.
			 */
			if (ret != null) {
				System.err.println(getFullName() + " assigning IT to " + ret.toString());
				ctx.getCurrentScope().defineVariable("IT", ret);
			}
		}

		ctx.popScope();
	}

	@Override
	public Value evaluate(Context context) throws LOLRuntimeEx {
		Value it = context.getCurrentScope().lookupVariable("IT");
		boolean executed = false;

		for (int i = 0; i < conds.size() && !executed; i++) {
			Value caseValue = conds.get(i).evaluate(context);

			if (caseValue.eq(it)) {
				// We run this case
				executed = true;

				evaluateExpressions(expressions.get(i), context);
			}
		}

		if (!executed && defaultExp != null) {
			evaluateExpressions(defaultExp, context);
		}

		return null;
	}

	@Override
	public void debugPrint(int indent) {
		System.err.println(this.toString());
	}

	@Override
	public String getName() {
		return "switch";
	}

}
