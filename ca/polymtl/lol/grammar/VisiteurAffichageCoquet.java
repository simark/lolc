package ca.polymtl.lol.grammar;

import java.io.*;

import ca.polymtl.lol.BinaryOpType;
import ca.polymtl.lol.InfArityOpType;

public class VisiteurAffichageCoquet extends LOLVisitorBasic {
	public static void main(String[] args) throws ParseException, LOLVisitException, IOException {
		LOL lol = new LOL(System.in);
		if (args.length > 0 && args[0].equals("debug")) {
			lol.setDebug(true);
		}
		
		SimpleNode root = lol.Start();
		
		VisiteurAffichageCoquet visiteur = new VisiteurAffichageCoquet();
		
		root.jjtAccept(visiteur, null);
		
		System.err.println("AffichageCoquet Done.");
	}
	
	int scope = 0;
	private PrintStream out;
	
	public VisiteurAffichageCoquet() {
		this(System.out);
	}
	
	public VisiteurAffichageCoquet(OutputStream out) {
		this.out = new PrintStream(out);
	}

	/* Comportement par défaut pour les types dont le visit n'est pas défini */
	@Override
	public Object visit(SimpleNode node, Object data) throws LOLVisitException {
		visitChildren(node);
		return null;
	} 	
	
	@Override
	public Object visit(ASTfunctionDefinition node, Object data) throws LOLVisitException {
		write("HOW DUZ I " + node.name);
		
		if (node.params.size() > 0) {
			write(" YR " + node.params.get(0));
			
			for (int i = 1; i < node.params.size(); i++) {
				write(" AN YR " + node.params.get(i));
			}
		}
		
		write("\n");
		
		scope++;
		
		int nbc = node.jjtGetNumChildren();
		
		for (int i = 0; i < nbc; i++) {
			printTabs();
			node.jjtGetChild(i).jjtAccept(this, null);
			write("\n");
		}
		
		scope--;
		
		printTabs();
		write("IF U SAY SO");
		write("\n");
		
		return null;
	}
	
	@Override
	public Object visit(ASTprimaryExpression node, Object data) throws LOLVisitException {
		if (node.type == ASTprimaryExpression.PrimaryExpressionType.IT) {
			write("IT");
		} else {
			visitChildren(node);
		}
		return null;
	}
	
	@Override
	public Object visit(ASTbinaryOperator node, Object data) throws LOLVisitException {
		BinaryOpType type = node.type;
		switch (type) {
			case SUM:
				write("SUM OF ");
				break;
			case DIFF:
				write("DIFF OF ");
				break;
			case PROD:
				write("PRODUKT OF ");
				break;
			case DIV:
				write("QUOSHUNT OF ");
				break;
			case MOD:
				write("MOD OF ");
				break;
			case MAX:
				write("BIGGR OF ");
				break;
			case MIN:
				write("SMALLR OF ");
				break;
			case AND:
				write("BOTH OF ");
				break;
			case OR:
				write("EITHER OF ");
				break;
			case XOR:
				write("WON OF ");
				break;
			case EQ:
				write("BOTH SAEM ");
				break;
			case NEQ:
				write("DIFFRINT ");
				break;
		}
		
		Node child0 = node.jjtGetChild(0);
		Node child1 = node.jjtGetChild(1);
		
		child0.jjtAccept(this, null);
		
		write(" AN ");
		
		child1.jjtAccept(this, null);
		
		return null;
	}
	
	@Override
	public Object visit(ASTunaryOperator node, Object data)
			throws LOLVisitException {
		write("NOT ");
		
		node.jjtGetChild(0).jjtAccept(this, null);
		return null;
	}
	
	@Override
	public Object visit(ASTinfiniteArityOperator node, Object data) throws LOLVisitException {
		InfArityOpType type = node.type;
		switch (type) {
			case ANY:
				write("ANY OF ");
				break;
			case ALL:
				write("ALL OF ");
				break;
			case CONCAT:
				write("SMOOSH ");
				break;
		}
		
		
		Node first = node.jjtGetChild(0);
		first.jjtAccept(this, null);
		
		for (int i = 1; i < node.jjtGetNumChildren(); i++) {
			write(" AN ");
			node.jjtGetChild(i).jjtAccept(this, null);
			
		}
		
		write(" MKAY");
		
		return null;
	}
	
	@Override
	public Object visit(ASTvariableOrFunctionExpression node, Object data) throws LOLVisitException {
		write(node.name);
		
		for (int i = 0; i < node.jjtGetNumChildren(); i++) {
			write(" ");
			node.jjtGetChild(i).jjtAccept(this, null);
		}
		
		return null;
	}
	
	@Override
	public Object visit(ASTliteralValue node, Object data)  throws LOLVisitException {
		
		write(node.value);
		
		return null;
	}

	@Override
	public Object visit(ASTconditionalExpression node, Object data) throws LOLVisitException {
		
		write("O RLY?\n");
		scope++;
		visitChildren(node);
		scope--;
		printTabs();
		write("OIC");
		write("\n");
		
		return null;
	}
	
	@Override
	public Object visit(ASTthenBlock node, Object data) throws LOLVisitException {
		
		scope--;
		printTabs();
		write("YA RLY\n");
		scope++;
		
		int nbc = node.jjtGetNumChildren();
		
		for (int i = 0; i < nbc; i++) {
			printTabs();
			node.jjtGetChild(i).jjtAccept(this, null);
			write("\n");
		}
		
		return null;
	}
	
	@Override
	public Object visit(ASTelseIfBlock node, Object data) throws LOLVisitException {
		
		scope--;
		printTabs();
		write("MEBBE ");
		node.jjtGetChild(0).jjtAccept(this, null);
		write("\n");
		scope++;
		
		int nbc = node.jjtGetNumChildren();
		
		for (int i = 1; i < nbc; i++) {
			printTabs();
			node.jjtGetChild(i).jjtAccept(this, null);
			write("\n");
		}
		
		return null;
	}
	
	@Override
	public Object visit(ASTelseBlock node, Object data) throws LOLVisitException {
		scope--;
		printTabs();
		write("NO WAY\n");
		scope++;
		
		int nbc = node.jjtGetNumChildren();
		
		for (int i = 0; i < nbc; i++) {
			printTabs();
			node.jjtGetChild(i).jjtAccept(this, null);
			write("\n");
		}
		
		return null;
	}
	
	@Override
	public Object visit(ASTreturnExpression node, Object data) throws LOLVisitException {
		write("FOUND YR ");
		
		visitChildren(node);
		
		return null;
	}
	
	@Override
	public Object visit(ASTvariableDeclaration node, Object data) throws LOLVisitException {
		write("I HAS A " + node.name);
		
		if (node.jjtGetNumChildren() > 0) {
			write(" ITZ ");
			visitChildren(node);
		}

		
		return null;
	}
	
	@Override
	public Object visit(ASTconsoleOutput node, Object data) throws LOLVisitException {
		write("VISIBLE");
		
		int nbc = node.jjtGetNumChildren();
		
		for (int i = 0; i < nbc; i++) {
			write(" ");
			
			node.jjtGetChild(i).jjtAccept(this, null);
		}
	
		
		return null;
	}
	
	@Override
	public Object visit(ASTconsoleInput node, Object data) throws LOLVisitException {
		write("GIMMEH " + node.variable);
	
		return null;
	}
	
	@Override
	public Object visit(ASTassignationExpression node, Object data) throws LOLVisitException {
		write(node.name + " R ");
		visitChildren(node);
		return null;
	}

	@Override
	public Object visit(ASTloopExpression node, Object data) throws LOLVisitException {
		
		write("\n");
		printTabs();
		write("IM IN YR " + node.name + " ");
		
		switch (node.operation) {
		case INCREMENT:
			write("UPPIN YR ");
			break;
		case DECREMENT:
			write("NERFIN YR ");
			break;
		case CUSTOM:
			write(node.customOperation + " YR ");
			break;
		}
		
		write(node.loopVariable);
		
		if (node.loopLimit != null) {
			switch (node.loopLimit) {
			case UNTIL:
				write(" TIL ");	
				break;
			case WHILE:
				write(" WILE ");
				break;
			}
			
			node.jjtGetChild(0).jjtAccept(this, null);
		}		
		
		write("\n");
		
		String loopName = node.name;
		
		
		int i = 0;
		if (node.loopLimit != null) {
			i = 1;
		}

		int nbc = node.jjtGetNumChildren();
		scope++;
		for (; i < nbc; i++) {
			printTabs();
			node.jjtGetChild(i).jjtAccept(this, null);
			write("\n");
		}
		scope--;
		printTabs();
		write("IM OUTTA YR " + loopName + "\n");
		
		return null;
	}
	
	@Override
	public Object visit(ASTswitchExpression node, Object data) throws LOLVisitException {
		write("WTF?\n");
		scope++;
		
		visitChildren(node);
		
		scope--;
		printTabs();
		write("OIC\n");
		
		return null;
	}
	
	@Override
	public Object visit(ASTcaseBlock node, Object data) throws LOLVisitException {
		scope--;
		printTabs();
		write("OMG ");
		scope++;
		
		node.jjtGetChild(0).jjtAccept(this, null);
		
		write("\n");
		
		for (int i = 1; i < node.jjtGetNumChildren(); i++) {
			printTabs();
			node.jjtGetChild(i).jjtAccept(this, null);
			write("\n");
		}
		
		return null;
	}
	
	@Override
	public Object visit(ASTdefaultBlock node, Object data) throws LOLVisitException {
		scope--;
		printTabs();
		write("OMGWTF\n");
		scope++;
		
		for (int i = 0; i < node.jjtGetNumChildren(); i++) {
			printTabs();
			node.jjtGetChild(i).jjtAccept(this, null);
			write("\n");
		}
		
		return null;
	}
	
	@Override
	public Object visit(ASTcastVariableExpression node, Object data) throws LOLVisitException {
		write(node.name + " IS NOW A ");
		
		
		switch (node.type) {
			case BOOLEAN:
				write("TROOF");
				break;
			case STRING:
				write("YARN");
				break;
			case NOTYPE:
				write("NOOB");
				break;
			case INTEGER:
				write("NUMBR");
				break;
			case FLOAT:
				write("NUMBAR");
				break;
		}
		
		return null;
	}
	
	@Override
	public Object visit(ASTcastExpression node, Object data) throws LOLVisitException {
		write("MAEK ");
		
		Node child = node.jjtGetChild(0);
		
		child.jjtAccept(this, null);
		
		write(" A ");
		
		switch (node.type) {
			case BOOLEAN:
				write("TROOF");
				break;
			case STRING:
				write("YARN");
				break;
			case NOTYPE:
				write("NOOB");
				break;
			case INTEGER:
				write("NUMBR");
				break;
			case FLOAT:
				write("NUMBAR");
				break;
		}
		
		return null;
	}
	

	@Override
	public Object visit(ASTstart node, Object data) throws LOLVisitException {
		
		int nbc = node.jjtGetNumChildren();
		
		write("HAI 1.2\n\n");
		scope++;
		for (int i = 0; i < nbc; i++) {
			printTabs();
			node.jjtGetChild(i).jjtAccept(this, null);
			write("\n");
		}
		
		
		scope--;
		printTabs(); // scope should be 0 here, so if KTHXBYE is indented, something is wrong
		write("KTHXBYE\n");
		
		return null;
	}
	
	@Override
	public Object visit(ASTbreakExpression node, Object data)
			throws LOLVisitException {
		
		write("GTFO");
		return null;
	}

	private void write(String s) {
			out.print(s);
	}
	
	private void printTabs() throws LOLVisitException {
		for (int i = 0; i < scope; i++) {
			write("    ");
		}
	}
	
	private void visitChildren(SimpleNode node) throws LOLVisitException {
		int nbc = node.jjtGetNumChildren();
		
		for (int i = 0; i < nbc; i++) {
			node.jjtGetChild(i).jjtAccept(this, null);
		}
	}
}
