package ca.polymtl.lol.grammar;

import java.util.Vector;

import ca.polymtl.lol.FunctionsScope;
import ca.polymtl.lol.LoopOperation;
import ca.polymtl.lol.Program;
import ca.polymtl.lol.expressions.Assign;
import ca.polymtl.lol.expressions.BinaryOp;
import ca.polymtl.lol.expressions.Break;
import ca.polymtl.lol.expressions.Cin;
import ca.polymtl.lol.expressions.Conditional;
import ca.polymtl.lol.expressions.Cout;
import ca.polymtl.lol.expressions.Expression;
import ca.polymtl.lol.expressions.Function;
import ca.polymtl.lol.expressions.FunctionCall;
import ca.polymtl.lol.expressions.InfArityOp;
import ca.polymtl.lol.expressions.Literal;
import ca.polymtl.lol.expressions.Loop;
import ca.polymtl.lol.expressions.Return;
import ca.polymtl.lol.expressions.Switch;
import ca.polymtl.lol.expressions.UnaryOp;
import ca.polymtl.lol.expressions.VariableRef;
import ca.polymtl.lol.expressions.VariableDecl;
import ca.polymtl.lol.grammar.ASTprimaryExpression.PrimaryExpressionType;
import ca.polymtl.lol.types.BoolValue;
import ca.polymtl.lol.types.IntegerValue;
import ca.polymtl.lol.types.StringValue;

public class VisiteurGenExecStruct extends LOLVisitorBasic {

	@Override
	public Object visit(SimpleNode node, Object data) throws LOLVisitException {
		throw new LOLVisitException("Node " + node.toString() + " not implemented");

		// return null;
	}

	@Override
	public Object visit(ASTstart node, Object data) throws LOLVisitException {

		Program prg = new Program();

		for (int i = 0; i < node.jjtGetNumChildren(); i++) {
			Expression e = (Expression) node.jjtGetChild(i).jjtAccept(this, prg);
			if (e != null) {
				prg.addExpression(e);
			}
		}

		return prg;
	}

	@Override
	public Object visit(ASTfunctionDefinition node, Object data) throws LOLVisitException {
		FunctionsScope fs = (FunctionsScope) data;

		Function fd = new Function(fs);
		fd.setParameters(node.params);
		fd.setName(node.name);

		fs.defineFunction(node.name, fd);

		for (int i = 0; i < node.jjtGetNumChildren(); i++) {
			Expression e = (Expression) node.jjtGetChild(i).jjtAccept(this, fd);
			if (e != null) {
				fd.addExpression(e);
			}
		}

		return null;
	}

	@Override
	public Object visit(ASTvariableDeclaration node, Object data) throws LOLVisitException {
		Expression init = null;
		if (node.jjtGetNumChildren() > 0) {
			init = (Expression) node.jjtGetChild(0).jjtAccept(this, data);
		}

		VariableDecl vd = new VariableDecl(node.name, init);

		return vd;
	}

	@Override
	public Object visit(ASTprimaryExpression node, Object data) throws LOLVisitException {
		if (node.type == PrimaryExpressionType.IT) {
			VariableRef vr = new VariableRef("IT");
			return vr;
		} else {
			return node.jjtGetChild(0).jjtAccept(this, data);
		}
	}

	@Override
	public Object visit(ASTliteralValue node, Object data) throws LOLVisitException {
		Literal lit = null;

		switch (node.type) {
		case BOOLEAN:
			if (node.value.equals("WIN")) {
				lit = new Literal(new BoolValue(true));
			} else {
				lit = new Literal(new BoolValue(false));
			}
			break;
		case DECIMAL:
			lit = new Literal(new IntegerValue(Integer.parseInt(node.value)));
			break;
		case FLOAT:
			lit = new Literal(new StringValue("LULZ FLOATS ARE NOT IMPLEMENTED"));
			break;
		case STRING:
			String val = node.value;
			val = val.substring(1, val.length() - 1);

			val = val.replace("\\n", "\n");
			val = val.replace("\\t", "\t");

			lit = new Literal(new StringValue(val));
			break;
		}

		return lit;
	}

	@Override
	public Object visit(ASTvariableOrFunctionExpression node, Object data) throws LOLVisitException {
		if (node.nbArgs >= 0) {
			// Function call
			FunctionsScope fs = (FunctionsScope) data;
			Function func = fs.lookupFunction(node.name);
			if (func == null) {
				throw new LOLVisitException("Function " + node.name + " is not defined");
			}

			FunctionCall call = new FunctionCall(func);

			for (int i = 0; i < node.jjtGetNumChildren(); i++) {
				Expression e = (Expression) node.jjtGetChild(i).jjtAccept(this, func);
				call.addArgument(e);

			}

			return call;
		} else {
			// Variable reference
			VariableRef vce = new VariableRef(node.name);
			return vce;
		}
	}

	@Override
	public Object visit(ASTreturnExpression node, Object data) throws LOLVisitException {
		Expression retExpr = (Expression) node.jjtGetChild(0).jjtAccept(this, data);

		Return ret = new Return(retExpr);

		return ret;
	}

	@Override
	public Object visit(ASTbinaryOperator node, Object data) throws LOLVisitException {
		Expression op0 = (Expression) node.jjtGetChild(0).jjtAccept(this, data);
		Expression op1 = (Expression) node.jjtGetChild(1).jjtAccept(this, data);

		BinaryOp binaryOp = new BinaryOp(op0, op1, node.type);

		return binaryOp;

	}

	@Override
	public Object visit(ASTassignationExpression node, Object data) throws LOLVisitException {
		Expression value = (Expression) node.jjtGetChild(0).jjtAccept(this, data);

		Assign assign = new Assign(node.name, value);

		return assign;
	}

	@Override
	public Object visit(ASTunaryOperator node, Object data) throws LOLVisitException {
		Expression exp = (Expression) node.jjtGetChild(0).jjtAccept(this, data);
		UnaryOp unaryOp = new UnaryOp(exp);
		return unaryOp;
	}

	@Override
	public Object visit(ASTinfiniteArityOperator node, Object data) throws LOLVisitException {
		InfArityOp op = new InfArityOp(node.type);

		for (int i = 0; i < node.jjtGetNumChildren(); i++) {
			Expression e = (Expression) node.jjtGetChild(i).jjtAccept(this, data);

			op.addExpression(e);
		}

		return op;
	}

	@Override
	public Object visit(ASTconsoleOutput node, Object data) throws LOLVisitException {
		Expression exp = (Expression) node.jjtGetChild(0).jjtAccept(this, data);
		Cout cout = new Cout(exp);
		return cout;
	}

	@Override
	public Object visit(ASTconsoleInput node, Object data) throws LOLVisitException {
		return new Cin(node.variable);
	}

	@Override
	public Object visit(ASTconditionalExpression node, Object data) throws LOLVisitException {
		FunctionsScope fs = (FunctionsScope) data;

		Conditional c = new Conditional(fs);

		/* Process the thenBlock, which will return a vector of expressions */
		@SuppressWarnings("unchecked")
		Vector<Expression> thenExps = (Vector<Expression>) node.jjtGetChild(0).jjtAccept(this, c);
		c.setThenExpressions(thenExps);

		c.clearFunctions();

		/* Process the elseIfBlocks */
		for (int i = 1; i <= node.nbElseIf; i++) {
			@SuppressWarnings("unchecked")
			Vector<Expression> elseIfExps = (Vector<Expression>) node.jjtGetChild(i).jjtAccept(this, c);

			Expression cond = elseIfExps.remove(0);

			c.addElseIf(cond, elseIfExps);

			c.clearFunctions();
		}

		/* Process the else */
		if (node.hasElse) {
			@SuppressWarnings("unchecked")
			Vector<Expression> elseExps = (Vector<Expression>) node.jjtGetChild(node.jjtGetNumChildren() - 1)
					.jjtAccept(this, c);

			c.setElseExpression(elseExps);

			c.clearFunctions();
		}

		return c;
	}

	@Override
	public Object visit(ASTthenBlock node, Object data) throws LOLVisitException {
		Vector<Expression> thenExps = new Vector<Expression>();

		for (int i = 0; i < node.jjtGetNumChildren(); i++) {
			Expression exp = (Expression) node.jjtGetChild(i).jjtAccept(this, data);
			if (exp != null) {
				thenExps.add(exp);
			}
		}

		return thenExps;
	}

	@Override
	public Object visit(ASTelseBlock node, Object data) throws LOLVisitException {
		Vector<Expression> elseExps = new Vector<Expression>();

		for (int i = 0; i < node.jjtGetNumChildren(); i++) {
			Expression exp = (Expression) node.jjtGetChild(i).jjtAccept(this, data);
			if (exp != null) {
				elseExps.add(exp);
			}
		}

		return elseExps;
	}

	@Override
	public Object visit(ASTelseIfBlock node, Object data) throws LOLVisitException {
		Vector<Expression> elseIfExps = new Vector<Expression>();

		for (int i = 0; i < node.jjtGetNumChildren(); i++) {
			Expression exp = (Expression) node.jjtGetChild(i).jjtAccept(this, data);
			if (exp != null) {
				elseIfExps.add(exp);
			}
		}

		return elseIfExps;
	}

	@Override
	public Object visit(ASTbreakExpression node, Object data) throws LOLVisitException {
		return new Break();
	}

	@Override
	public Object visit(ASTloopExpression node, Object data) throws LOLVisitException {
		FunctionsScope fs = (FunctionsScope) data;
		Expression limitExp = null;

		Loop loop = new Loop(fs);

		int i = 0;

		/* Loop limit */
		if (node.loopLimit != null) {
			limitExp = (Expression) node.jjtGetChild(0).jjtAccept(this, data);
			i++;
		}

		loop.setLimit(node.loopLimit);
		loop.setLimitExp(limitExp);

		/* Expressions */
		for (; i < node.jjtGetNumChildren(); i++) {
			Expression e = (Expression) node.jjtGetChild(i).jjtAccept(this, loop);
			if (e != null) {
				loop.addExpression(e);
			}
		}

		/* Operation */
		loop.setOp(node.operation);
		loop.setOpVariableName(node.loopVariable);
		if (node.operation == LoopOperation.CUSTOM) {
			Function opFunc = fs.lookupFunction(node.customOperation);
			if (opFunc == null) {
				throw new LOLVisitException("Loop operation function " + node.customOperation + " is not defined");
			}

			FunctionCall fc = new FunctionCall(opFunc);
			VariableRef vr = new VariableRef(node.loopVariable);
			fc.addArgument(vr);

			loop.setCustomOp(fc);
		}

		return loop;
	}

	@Override
	public Object visit(ASTswitchExpression node, Object data) throws LOLVisitException {
		Switch sw = new Switch((FunctionsScope) data);

		int max = node.jjtGetNumChildren();

		if (node.hasDefault) {
			max--;

			@SuppressWarnings("unchecked")
			Vector<Expression> defaultExp = (Vector<Expression>) node.jjtGetChild(max).jjtAccept(this, sw);
			sw.setDefaultExp(defaultExp);

			sw.clearFunctions();
		}

		for (int i = 0; i < max; i++) {
			@SuppressWarnings("unchecked")
			Vector<Expression> caseExp = (Vector<Expression>) node.jjtGetChild(i).jjtAccept(this, sw);
			Expression caseCond = caseExp.remove(0);

			sw.addCase(caseCond, caseExp);

			sw.clearFunctions();
		}

		return sw;
	}

	@Override
	public Object visit(ASTdefaultBlock node, Object data) throws LOLVisitException {
		Vector<Expression> exp = new Vector<Expression>();

		for (int i = 0; i < node.jjtGetNumChildren(); i++) {
			Expression e = (Expression) node.jjtGetChild(i).jjtAccept(this, data);
			if (e != null) {
				exp.add(e);
			}
		}

		return exp;
	}

	@Override
	public Object visit(ASTcaseBlock node, Object data) throws LOLVisitException {
		Vector<Expression> exp = new Vector<Expression>();

		for (int i = 0; i < node.jjtGetNumChildren(); i++) {
			Expression e = (Expression) node.jjtGetChild(i).jjtAccept(this, data);
			if (e != null) {
				exp.add(e);
			}
		}

		return exp;
	}
}
