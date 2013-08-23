package ca.polymtl.lol.expressions;

import java.util.Vector;

import ca.polymtl.lol.Context;
import ca.polymtl.lol.FunctionsScope;
import ca.polymtl.lol.LoopLimit;
import ca.polymtl.lol.LoopOperation;
import ca.polymtl.lol.exceptions.LOLRuntimeEx;
import ca.polymtl.lol.types.IntegerValue;
import ca.polymtl.lol.types.Value;

public class Loop extends FunctionsScope implements Expression {

	public Loop(FunctionsScope parent) {
		super(parent);
	}

	/* Limit */
	LoopLimit limit;
	Expression limitExp;

	/* Operation */
	LoopOperation op;
	FunctionCall customOp;
	String opVariableName;

	/* Expressions */
	Vector<Expression> exps = new Vector<Expression>();

	public void setLimit(LoopLimit limit) {
		this.limit = limit;
	}

	public void setLimitExp(Expression limitExp) {
		this.limitExp = limitExp;
	}

	public void setOp(LoopOperation op) {
		this.op = op;
	}

	public void setCustomOp(FunctionCall customOp) {
		this.customOp = customOp;
	}

	public void setOpVariableName(String opVariableName) {
		this.opVariableName = opVariableName;
	}

	public void addExpression(Expression e) {
		exps.add(e);
	}

	@Override
	public Value evaluate(Context context) throws LOLRuntimeEx {
		mainLoop: while (true) {
			/* Verify the loop limit */
			if (limit != null) {
				boolean ret = limitExp.evaluate(context).toBoolValue().getValue();

				if (limit == LoopLimit.WHILE && !ret || limit == LoopLimit.UNTIL && ret) {
					break;
				}
			}

			/* Execute the code */
			context.pushScope();

			for (Expression e : exps) {
				Value ret = e.evaluate(context);

				if (context.isReturning()) {
					System.err.println("Return passing through loop");
					break mainLoop;
				}

				if (context.isBreaking()) {
					context.setBreaking(false);
					System.err.println("Breaking loop");
					break mainLoop;
				}

				/*
				 * If the expression returned something, put it in the IT
				 * variable.
				 */
				if (ret != null) {
					System.err.println(getFullName() + " assigning IT to " + ret.toString());
					context.getCurrentScope().defineVariable("IT", ret);
				}
			}

			context.popScope();

			/* Apply the loop operation */
			Value opVariable = context.getCurrentScope().lookupVariable(this.opVariableName);
			if (opVariable == null) {
				throw new LOLRuntimeEx("Loop variable " + opVariableName + " not defined.");
			}

			switch (op) {
			case CUSTOM:
				opVariable = customOp.evaluate(context);
				break;
			case DECREMENT:
				opVariable = new IntegerValue(opVariable.toIntegerValue().getValue() - 1);
				break;

			case INCREMENT:
				opVariable = new IntegerValue(opVariable.toIntegerValue().getValue() + 1);
				break;

			}

			context.getCurrentScope().assignExistingVariable(opVariableName, opVariable);
		}

		return null;
	}

	@Override
	public void debugPrint(int indent) {
		System.err.println(this.toString());
	}

	@Override
	public String getName() {
		return "loop";
	}

}
