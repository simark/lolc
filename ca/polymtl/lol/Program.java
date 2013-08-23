package ca.polymtl.lol;

import java.util.Vector;

import ca.polymtl.lol.exceptions.LOLRuntimeEx;
import ca.polymtl.lol.expressions.Expression;
import ca.polymtl.lol.types.Value;

public class Program extends FunctionsScope {
	Vector<Expression> expressions = new Vector<Expression>();
	
	public Program() {
		super(null);
	}
	
	void run() throws LOLRuntimeEx {
		Context ctx = new Context();
		
		for (Expression e : expressions) {
			Value ret = e.evaluate(ctx);

			if (ctx.isBreaking()) {
				throw new LOLRuntimeEx("Can't break from main program");
			}
			
			if (ctx.isReturning()) {
				throw new LOLRuntimeEx("Can't return from main program");
			}
			
			/* If the expression returned something, put it in the IT variable. */
			if (ret != null) {
				System.err.println(getFullName() + " assigning IT to " + ret.toString());
				ctx.getCurrentScope().defineVariable("IT", ret);
			}
		}
	}

	public void addExpression(Expression e) {
		expressions.add(e);		
	}

	@Override
	public String getName() {
		return "Program";
	}
	
	public void debugPrint() {
		System.err.println(this.toString());
		
		for (Expression e : expressions) {
			e.debugPrint(1);
		}
	}
	
	@Override
	public String toString() {
		return "Program";
	}

}
