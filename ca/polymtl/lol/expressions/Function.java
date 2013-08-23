package ca.polymtl.lol.expressions;

import java.util.Vector;

import ca.polymtl.lol.Context;
import ca.polymtl.lol.FunctionsScope;
import ca.polymtl.lol.exceptions.LOLRuntimeEx;
import ca.polymtl.lol.types.NullValue;
import ca.polymtl.lol.types.Value;
import static ca.polymtl.lol.Utils.tabs;

public class Function extends FunctionsScope implements Expression {
	Vector<String> parameters;
	Vector<Expression> expressions = new Vector<Expression>();
	String name;

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	public Function(FunctionsScope parentFunctionsScope) {
		super(parentFunctionsScope);
	}

	public Vector<Expression> getExpressions() {
		return expressions;
	}

	public void addExpression(Expression e) {
		assert(e != null);
		expressions.add(e);
	}

	public int getNbParameters() {
		return parameters.size();
	}

	public void setParameters(Vector<String> parameters) {
		this.parameters = parameters;
	}

	public String getParameter(int i) {
		return parameters.get(i);
	}

	public Vector<String> getParameters() {
		return parameters;
	}

	@Override
	public Value evaluate(Context context) throws LOLRuntimeEx {
		System.err.println("Evaluating function " + name);
		
		/* If no return expression is found, the function returns a NullValue */
		Value ret = new NullValue();
		context.pushScope();

		for (Expression e : expressions) {
			Value expRet = e.evaluate(context);

			if (context.isReturning()) {
				context.setReturning(false);
				
				ret = context.getReturnValue();
				
				break;
			}
			
			if (context.isBreaking()) {
				throw new LOLRuntimeEx("Can't break out of a function");
			}

			/* If the expression returned something, put it in the IT variable. */
			if (expRet != null) {
				context.getCurrentScope().defineVariable("IT", expRet);
			}
		}

		context.popScope();

		return ret;
	}
	
	@Override
	public void debugPrint(int indent) {
		tabs(indent);
		System.err.println("Function " + name);
	}
}
