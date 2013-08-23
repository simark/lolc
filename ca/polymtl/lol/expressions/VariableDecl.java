package ca.polymtl.lol.expressions;

import ca.polymtl.lol.Context;
import ca.polymtl.lol.exceptions.LOLRuntimeEx;
import ca.polymtl.lol.types.NullValue;
import ca.polymtl.lol.types.Value;
import static ca.polymtl.lol.Utils.tabs;

public class VariableDecl implements Expression {

	public VariableDecl(String name, Expression init) {
		this.name = name;
		this.init = init;
	}

	final String name;

	final Expression init;

	public String getName() {
		return name;
	}

	public Expression getInit() {
		return init;
	}

	@Override
	public Value evaluate(Context context) throws LOLRuntimeEx {

		Value val;

		if (init != null) {
			val = init.evaluate(context);
		} else {
			val = new NullValue();
		}

		System.err.println("Evaluating variable declaration of " + name + " with value " + val);

		context.getCurrentScope().defineVariable(name, val);

		return null;
	}

	@Override
	public void debugPrint(int indent) {
		tabs(indent);

		if (init != null) {
			System.err.println("Declaration of variable " + name + " with init");
			init.debugPrint(indent + 1);
		} else {
			System.err.println("Declaration of variable " + name + " without init");
		}
	}
}
