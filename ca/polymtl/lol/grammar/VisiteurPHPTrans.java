package ca.polymtl.lol.grammar;

import java.io.FileNotFoundException;

import ca.polymtl.lol.BinaryOpType;
import ca.polymtl.lol.LoopLimit;

public class VisiteurPHPTrans extends LOLVisitorBasic {
	int indent = 0;

	/**
	 * @param args
	 * @throws ParseException
	 * @throws LOLVisitException
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws ParseException,
			LOLVisitException, FileNotFoundException {
		LOL lol = new LOL(System.in);
		if (args.length > 0 && args[0].equals("debug")) {
			lol.setDebug(true);
		}

		SimpleNode root = lol.Start();

		VisiteurPHPTrans visiteur = new VisiteurPHPTrans();

		System.out.println("<?php");
		root.jjtAccept(visiteur, null);
		System.out.println("?>");
	}

	@Override
	public Object visit(SimpleNode node, Object data) throws LOLVisitException {
		System.out.print("<NODE TYPE NOT IMPLEMENTED " + node + ">");
		return null;
	}

	@Override
	public Object visit(ASTstart node, Object data) throws LOLVisitException {
		codeBlock(node);
		return null;
	}

	@Override
	public Object visit(ASTconsoleOutput node, Object data)
			throws LOLVisitException {

		System.out.print("echo ");
		System.out.print("/*");
		System.out.print(node.firstToken.beginLine);
		System.out.print("*/");

		node.jjtGetChild(0).jjtAccept(this, null);

		for (int i = 1; i < node.jjtGetNumChildren(); i++) {
			System.out.print(" . ");
			node.jjtGetChild(i).jjtAccept(this, null);
		}

		return true;
	}

	@Override
	public Object visit(ASTfunctionDefinition node, Object data)
			throws LOLVisitException {

		System.out.print("function " + node.name);

		System.out.print("(");

		StringBuilder paramList = new StringBuilder();
		for (String param : node.params) {
			paramList.append("$" + param + ", ");
		}

		if (paramList.length() > 0) {
			paramList.deleteCharAt(paramList.length() - 1);
			paramList.deleteCharAt(paramList.length() - 1);
		}

		System.out.print(paramList.toString());

		System.out.println(")");

		codeBlock(node);

		return null;
	}

	@Override
	public Object visit(ASTprimaryExpression node, Object data)
			throws LOLVisitException {
		if (data != null) {
			System.out.print("$it = ");
		}

		node.childrenAccept(this, null);

		return data;
	}

	@Override
	public Object visit(ASTbinaryOperator node, Object data)
			throws LOLVisitException {
		Node child0 = node.jjtGetChild(0);
		Node child1 = node.jjtGetChild(1);

		if (node.type == BinaryOpType.MIN) {
			System.out.print("min(");
			child0.jjtAccept(this, null);
			System.out.print(", ");
			child1.jjtAccept(this, null);
			System.out.print(")");
		} else if (node.type == BinaryOpType.MAX) {
			System.out.print("max(");
			child0.jjtAccept(this, null);
			System.out.print(", ");
			child1.jjtAccept(this, null);
			System.out.print(")");
		} else {
			System.out.print("(");

			child0.jjtAccept(this, null);

			System.out.print(")");

			String op = "YOU SHOULD NOT SEE THIS";
			switch (node.type) {
			case AND:
				op = "&&";
				break;
			case DIFF:
				op = "-";
				break;
			case DIV:
				op = "/";
				break;
			case EQ:
				op = "==";
				break;
			case MOD:
				op = "%";
				break;
			case NEQ:
				op = "!=";
				break;
			case OR:
				op = "||";
				break;
			case PROD:
				op = "*";
				break;
			case SUM:
				op = "+";
				break;
			case XOR:
				op = "^";
				break;

			}

			System.out.print(" " + op + " ");

			System.out.print("(");

			child1.jjtAccept(this, null);

			System.out.print(")");
		}

		return null;
	}

	@Override
	public Object visit(ASTvariableOrFunctionExpression node, Object data)
			throws LOLVisitException {
		// if nbArgs is -1, node is a variable. Otherwise, it is a function
		// call.
		if (node.nbArgs == -1) {
			System.out.print("$" + node.name);
		} else {
			System.out.print(node.name + "(");

			int nc = node.jjtGetNumChildren();

			if (nc > 0) {
				node.jjtGetChild(0).jjtAccept(this, null);

				for (int i = 1; i < nc; i++) {
					System.out.print(", ");
					node.jjtGetChild(i).jjtAccept(this, null);
				}
			}

			System.out.print(")");
		}

		return null;
	}

	@Override
	public Object visit(ASTconditionalExpression node, Object data)
			throws LOLVisitException {
		System.out.println("if ($it)");

		node.childrenAccept(this, null);

		return null;
	}

	@Override
	public Object visit(ASTthenBlock node, Object data)
			throws LOLVisitException {

		codeBlock(node);

		return null;
	}

	@Override
	public Object visit(ASTelseBlock node, Object data)
			throws LOLVisitException {
		printIndent();
		System.out.println("else");

		codeBlock(node);

		return null;
	}
	
	@Override
	public Object visit(ASTelseIfBlock node, Object data)
			throws LOLVisitException {
		printIndent();
		System.out.print("else if (");
		
		node.jjtGetChild(0).jjtAccept(this, null);
		
		System.out.println(")");
		
		codeBlock(node, 1);
		
		return null;
	}

	@Override
	public Object visit(ASTliteralValue node, Object data)
			throws LOLVisitException {
		switch (node.type) {
		case BOOLEAN:
			if (node.value.equals("WIN")) {
				System.out.print("true");
			} else {
				System.out.print("false");
			}
			break;
		case DECIMAL:
		case FLOAT:
		case STRING:
			System.out.print(node.value);
			break;
		}

		return null;
	}

	@Override
	public Object visit(ASTreturnExpression node, Object data)
			throws LOLVisitException {
		System.out.print("return ");
		node.childrenAccept(this, null);
		return true;
	}

	@Override
	public Object visit(ASTvariableDeclaration node, Object data)
			throws LOLVisitException {
		System.out.print("$" + node.name + " = ");

		if (node.jjtGetNumChildren() > 0) {
			node.jjtGetChild(0).jjtAccept(this, null);
		} else {
			System.out.print("NULL");
		}

		return true;
	}

	@Override
	public Object visit(ASTconsoleInput node, Object data)
			throws LOLVisitException {
		System.out.print(String.format("$%s = trim(fgets(STDIN))", node.variable));
		return true;
	}

	@Override
	public Object visit(ASTloopExpression node, Object data)
			throws LOLVisitException {
		int codeBlockStart = 0;
		System.out.print("for (; ");

		if (node.loopLimit == LoopLimit.UNTIL) {
			System.out.print("!");
			node.jjtGetChild(0).jjtAccept(this, null);

			codeBlockStart = 1;
		} else if (node.loopLimit == LoopLimit.WHILE) {

			node.jjtGetChild(0).jjtAccept(this, null);

			codeBlockStart = 1;
		}

		System.out.print("; ");

		switch (node.operation) {
		case CUSTOM:
			System.out.print(String.format("$%s = %s($%s)", node.loopVariable,
					node.customOperation, node.loopVariable));
			break;
		case DECREMENT:
			System.out.print(String.format("$%s--", node.loopVariable));
			break;
		case INCREMENT:
			System.out.print(String.format("$%s++", node.loopVariable));
			break;

		}
		
		System.out.println(")");

		codeBlock(node, codeBlockStart);

		return null;
	}

	@Override
	public Object visit(ASTbreakExpression node, Object data)
			throws LOLVisitException {
		System.out.print("break");
		return true;
	}

	@Override
	public Object visit(ASTinfiniteArityOperator node, Object data)
			throws LOLVisitException {
		String op = "";

		switch (node.type) {
		case ALL:
			op = " &&";
			break;
		case ANY:
			op = " || ";
			break;
		case CONCAT:
			op = " . ";
			break;
		}

		node.jjtGetChild(0).jjtAccept(this, null);

		for (int i = 1; i < node.jjtGetNumChildren(); i++) {
			System.out.print(op);
			node.jjtGetChild(i).jjtAccept(this, null);
		}

		return null;
	}

	@Override
	public Object visit(ASTassignationExpression node, Object data)
			throws LOLVisitException {
		System.out.print(String.format("$%s = ", node.name));
		
		node.jjtGetChild(0).jjtAccept(this, null);
		
		return true;
	}
	
	@Override
	public Object visit(ASTunaryOperator node, Object data)
			throws LOLVisitException {
		System.out.print("!(");
		node.childrenAccept(this, null);
		System.out.print(")");
		return null;
	}
	
	@Override
	public Object visit(ASTswitchExpression node, Object data)
			throws LOLVisitException {
		System.out.println("switch ($it)");
		
		codeBlock(node);
		
		return null;
	}
	
	@Override
	public Object visit(ASTcaseBlock node, Object data)
			throws LOLVisitException {
		System.out.print("case ");
		node.jjtGetChild(0).jjtAccept(this, null);
		System.out.println(":");
		
		codeBlock(node, 1);
		
		return null;
	}
	
	@Override
	public Object visit(ASTdefaultBlock node, Object data)
			throws LOLVisitException {
		System.out.println("default:");
		
		codeBlock(node);
		return null;
	}
	
	void codeBlock(Node n) throws LOLVisitException {
		codeBlock(n, 0);
	}

	void codeBlock(Node n, int start) throws LOLVisitException {
		printIndent();
		System.out.println("{");
		indent++;

		for (int i = start; i < n.jjtGetNumChildren(); i++) {
			printIndent();
			Object result = n.jjtGetChild(i).jjtAccept(this, true);
			if (result != null) {
				System.out.println(";");
			} else {
				System.out.println();
			}
		}

		indent--;
		printIndent();
		System.out.println("}");
	}

	void printIndent() {
		for (int i = 0; i < indent; i++) {
			System.out.print("    ");
		}
	}
}
