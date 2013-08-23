package ca.polymtl.lol.expressions;

import java.util.Vector;

import ca.polymtl.lol.Context;
import ca.polymtl.lol.FunctionsScope;
import ca.polymtl.lol.exceptions.LOLRuntimeEx;
import ca.polymtl.lol.types.Value;

public class Conditional extends FunctionsScope implements Expression {

	Vector<Expression> thenExps = null;
	Vector<Expression> elseExp = null;
	Vector<Expression> elseIfConds = new Vector<Expression>();
	Vector<Vector<Expression>> elseIfExps = new Vector<Vector<Expression>>();

	public Conditional(FunctionsScope parent) {
		super(parent);
	}

	public void setThenExpressions(Vector<Expression> exps) {
		thenExps = exps;
	}

	public void addElseIf(Expression cond, Vector<Expression> exps) {
		elseIfConds.add(cond);
		elseIfExps.add(exps);
	}

	public void setElseExpression(Vector<Expression> exps) {
		elseExp = exps;
	}

	private void evaluateExpressions(Vector<Expression> exp, Context context) throws LOLRuntimeEx {
		context.pushScope();

		for (Expression e : exp) {
			Value ret = e.evaluate(context);

			if (context.isReturning()) {
				System.err.println("Return passing through if");
				break;
			}

			if (context.isBreaking()) {
				System.err.println("Break passing through if");
				break;
			}

			/*
			 * If the expression returned something, put it in the IT variable.
			 */
			if (ret != null) {
				System.err.println(getFullName() + " assigning IT to " + ret.toString());
				context.getCurrentScope().defineVariable("IT", ret);
			}
		}

		context.popScope();
	}

	@Override
	public Value evaluate(Context context) throws LOLRuntimeEx {
		Value it = context.getCurrentScope().lookupVariable("IT");

		assert (thenExps != null);

		if (it.toBoolValue().getValue()) {
			System.err.println("If executing then block");
			evaluateExpressions(thenExps, context);
		} else {
			System.err.println("If, it's not the then");
			boolean executedSomething = false;

			for (int i = 0; i < elseIfConds.size() && !executedSomething; i++) {
				Expression condition = elseIfConds.get(i);
				
				System.err.println("If testing elseif condition #" + i);
				if (condition.evaluate(context).toBoolValue().getValue()) {
					System.err.println("If executing elseif block #" + i);
					executedSomething = true;

					Vector<Expression> exp = elseIfExps.get(i);

					evaluateExpressions(exp, context);
				} else {
					System.err.println("If, nope, its not else if condition #" + i);
				}
			}

			if (!executedSomething && elseExp != null) {
				System.err.println("If executing else block");
				evaluateExpressions(elseExp, context);
			}
		}

		return null;
	}

	@Override
	public void debugPrint(int indent) {
		System.err.println(this.toString());
	}

	@Override
	public String getName() {
		return "if";
	}

}
