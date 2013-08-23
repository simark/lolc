package ca.polymtl.lol.expressions;

import ca.polymtl.lol.BinaryOpType;
import ca.polymtl.lol.Context;
import static ca.polymtl.lol.Utils.tabs;
import ca.polymtl.lol.exceptions.LOLRuntimeEx;
import ca.polymtl.lol.types.BoolValue;
import ca.polymtl.lol.types.IntegerValue;
import ca.polymtl.lol.types.Value;

public class BinaryOp implements Expression {

	public BinaryOp(Expression op1, Expression op2, BinaryOpType op) {
		super();
		this.op0 = op1;
		this.op1 = op2;
		this.op = op;
	}

	Expression op0;
	Expression op1;

	BinaryOpType op;

	@Override
	public Value evaluate(Context context) throws LOLRuntimeEx {
		Value left = op0.evaluate(context);
		Value right = op1.evaluate(context);
		Value ret = null;

		IntegerValue leftInt = left.toIntegerValue();
		IntegerValue rightInt = right.toIntegerValue();
		BoolValue leftBool = left.toBoolValue();
		BoolValue rightBool = right.toBoolValue();

		switch (op) {
		case AND:
			ret = new BoolValue(leftBool.getValue() && rightBool.getValue());
			break;

		case DIFF:
			ret = new IntegerValue(leftInt.getValue() - rightInt.getValue());
			break;

		case DIV:
			ret = new IntegerValue(leftInt.getValue() / rightInt.getValue());
			break;

		case EQ:
			ret = new BoolValue(left.eq(right));
			System.err.println("Equal ? " + left.toString() + " " + right.toString() + " " + ret);
			break;

		case MAX:
			ret = new IntegerValue(Math.max(leftInt.getValue(), rightInt.getValue()));
			System.err.println("Max of " + leftInt.getValue() + " and " + rightInt.getValue() + " is "
					+ Math.max(leftInt.getValue(), leftInt.getValue()));
			break;

		case MIN:
			ret = new IntegerValue(Math.min(leftInt.getValue(), rightInt.getValue()));
			break;

		case MOD:
			ret = new IntegerValue(leftInt.getValue() % rightInt.getValue());
			break;

		case NEQ:
			ret = new BoolValue(!left.eq(right));
			break;

		case OR:
			ret = new BoolValue(leftBool.getValue() || rightBool.getValue());
			break;

		case PROD:
			ret = new IntegerValue(leftInt.getValue() * rightInt.getValue());
			break;

		case SUM:
			ret = new IntegerValue(leftInt.getValue() + rightInt.getValue());
			break;

		case XOR:
			ret = new BoolValue(leftBool.getValue() != rightBool.getValue());
			break;
		}

		assert (ret != null);
		return ret;
	}

	@Override
	public void debugPrint(int indent) {
		tabs(indent);
		System.err.println("BinaryOp ");

	}

}
