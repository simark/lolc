package ca.polymtl.lol.grammar;

public abstract class LOLVisitorBasic implements LOLVisitor {
	@Override
	abstract public Object visit(SimpleNode node, Object data) throws LOLVisitException;

	@Override
	public Object visit(ASTliteralValue node, Object data) throws LOLVisitException {
		return visit((SimpleNode) node, data);
	}

	@Override
	public Object visit(ASTunaryOperator node, Object data) throws LOLVisitException {
		return visit((SimpleNode) node, data);
	}

	@Override
	public Object visit(ASTbinaryOperator node, Object data) throws LOLVisitException {
		return visit((SimpleNode) node, data);
	}

	@Override
	public Object visit(ASTinfiniteArityOperator node, Object data) throws LOLVisitException {
		return visit((SimpleNode) node, data);
	}

	@Override
	public Object visit(ASTvariableOrFunctionExpression node, Object data) throws LOLVisitException {
		return visit((SimpleNode) node, data);
	}

	@Override
	public Object visit(ASTprimaryExpression node, Object data) throws LOLVisitException {
		return visit((SimpleNode) node, data);
	}

	@Override
	public Object visit(ASTvariableDeclaration node, Object data) throws LOLVisitException {
		return visit((SimpleNode) node, data);
	}

	@Override
	public Object visit(ASTconsoleOutput node, Object data) throws LOLVisitException {
		return visit((SimpleNode) node, data);
	}

	@Override
	public Object visit(ASTconsoleInput node, Object data) throws LOLVisitException {
		return visit((SimpleNode) node, data);
	}

	@Override
	public Object visit(ASTthenBlock node, Object data) throws LOLVisitException {
		return visit((SimpleNode) node, data);
	}

	@Override
	public Object visit(ASTelseIfBlock node, Object data) throws LOLVisitException {
		return visit((SimpleNode) node, data);
	}

	@Override
	public Object visit(ASTelseBlock node, Object data) throws LOLVisitException {
		return visit((SimpleNode) node, data);
	}

	@Override
	public Object visit(ASTconditionalExpression node, Object data) throws LOLVisitException {
		return visit((SimpleNode) node, data);
	}

	@Override
	public Object visit(ASTcaseBlock node, Object data) throws LOLVisitException {
		return visit((SimpleNode) node, data);
	}

	@Override
	public Object visit(ASTdefaultBlock node, Object data) throws LOLVisitException {
		return visit((SimpleNode) node, data);
	}

	@Override
	public Object visit(ASTswitchExpression node, Object data) throws LOLVisitException {
		return visit((SimpleNode) node, data);
	}

	@Override
	public Object visit(ASTloopExpression node, Object data) throws LOLVisitException {
		return visit((SimpleNode) node, data);
	}

	@Override
	public Object visit(ASTassignationExpression node, Object data) throws LOLVisitException {
		return visit((SimpleNode) node, data);
	}

	@Override
	public Object visit(ASTfunctionDefinition node, Object data) throws LOLVisitException {
		return visit((SimpleNode) node, data);
	}
	
	@Override
	public Object visit(ASTreturnExpression node, Object data) throws LOLVisitException {
		return visit((SimpleNode) node, data);
	}

	@Override
	public Object visit(ASTstart node, Object data) throws LOLVisitException {
		return visit((SimpleNode) node, data);
	}
	
	@Override
	public Object visit(ASTbreakExpression node, Object data) throws LOLVisitException {
		return visit((SimpleNode) node, data);
	}
	
	@Override
	public Object visit(ASTcastVariableExpression node, Object data) throws LOLVisitException {
		return visit((SimpleNode) node, data);
	}
	
	@Override
	public Object visit(ASTcastExpression node, Object data) throws LOLVisitException {
		return visit((SimpleNode) node, data);
	}
}
