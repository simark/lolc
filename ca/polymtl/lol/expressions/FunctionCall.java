package ca.polymtl.lol.expressions;

import java.util.Vector;

import ca.polymtl.lol.Context;
import ca.polymtl.lol.exceptions.LOLRuntimeEx;
import ca.polymtl.lol.types.Value;

public class FunctionCall implements Expression {
	public FunctionCall(Function func) {
		this.func = func;
	}

	Function func;
	Vector<Expression> arguments = new Vector<Expression>();

	@Override
	public Value evaluate(Context context) throws LOLRuntimeEx {
		System.err.println("Evaluation function call of " + func.getName());
		if (this.arguments.size() != func.getParameters().size()) {
			throw new LOLRuntimeEx("Function " + func.getName() + " needs "
					+ func.getParameters().size() + " arguments, got "
					+ this.arguments.size());
		}

		/* Evaluate all the arguments */
		Vector<Value> argValues = new Vector<Value>();
		
		for (Expression e : arguments) {
			argValues.add(e.evaluate(context));
		}
		
		context.pushScope();
		
		/* Add the arguments to the scope of the function */
		
		for (int i = 0; i < arguments.size(); i++) {
			System.err.println("Argument #" + i  + " is " + argValues.get(i));
			context.getCurrentScope().defineVariable(func.getParameter(i), argValues.get(i));
		}
		
		Value funcRet = func.evaluate(context);
		
		context.popScope();
		
		return funcRet;
	}

	@Override
	public void debugPrint(int indent) {
		// TODO Auto-generated method stub

	}

	public void addArgument(Expression e) {
		arguments.add(e);
	}

}
