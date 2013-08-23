package ca.polymtl.lol.grammar;

import ca.polymtl.lol.LoopOperation;

class VisiteurDOTOutput extends LOLVisitorBasic {
	public static void main(String[] args) throws Exception {
		LOL lol = new LOL(System.in);

		if (args.length > 0 && args[0].equals("debug")) {
			lol.setDebug(true);
		}

		SimpleNode n = lol.Start();

		VisiteurDOTOutput vis = new VisiteurDOTOutput();

		n.jjtAccept(vis, null);

		System.err.println("DOTOutput Done.");
	}

	private int ticketDistributor = 0; // Comme lorsqu'on attend 2 heures au
										// registrariat.

	private int takeTicket() {
		ticketDistributor++;
		return ticketDistributor - 1;
	}

	@Override
	public Object visit(SimpleNode node, Object data) throws LOLVisitException {
		int n = node.jjtGetNumChildren();
		int id = takeTicket();

		String nodeName = "node" + id;

		String label = node.toString();

		if (data != null) {
			label += "<br /> [" + data + "]";
		}

		System.out.println(nodeName + " [ label=< " + label + " > ]");
		for (int i = 0; i < n; i++) {
			Node child = node.jjtGetChild(i);

			int childId = (Integer) child.jjtAccept(this, null);
			String childName = "node" + childId;
			System.out.println(nodeName + " -> " + childName);
		}

		return id;
	}

	@Override
	public Object visit(ASTliteralValue node, Object data)
			throws LOLVisitException {
		return visit((SimpleNode) node, node.type.toString() + ":" + node.value);
	}

	@Override
	public Object visit(ASTvariableOrFunctionExpression node, Object data)
			throws LOLVisitException {
		return visit((SimpleNode) node, node.name);
	}

	@Override
	public Object visit(ASTvariableDeclaration node, Object data)
			throws LOLVisitException {
		return visit((SimpleNode) node, node.name);
	}

	@Override
	public Object visit(ASTfunctionDefinition node, Object data)
			throws LOLVisitException {
		String label = node.name = "(";

		if (node.params.size() > 0) {
			label += node.params.get(0);

			for (int i = 1; i < node.params.size(); i++) {
				label += ", " + node.params.get(i);
			}
		}

		label += ")";

		return visit((SimpleNode) node, label);
	}

	@Override
	public Object visit(ASTstart node, Object data) throws LOLVisitException {
		System.out.println("digraph lol {");
		System.out
				.println("node [shape=box, fixedsize=false, fontsize=9, fontname=\"monospace\", fontcolor=\"blue\","
						+ "width=.25, height=.25, color=\"black\", fillcolor=\"white\", style=\"filled, solid\"];"
						+ "edge [arrowsize=.5, color=\"black\", style=\"\"]");
		System.out.println("  ranksep=.5;");
		System.out.println("  ordering=out;");

		int id = (Integer) visit((SimpleNode) node, null);

		System.out.println("}");

		return id;
	}

	@Override
	public Object visit(ASTprimaryExpression node, Object data)
			throws LOLVisitException {
		return visit((SimpleNode) node, node.type.toString());
	}

	@Override
	public Object visit(ASTbinaryOperator node, Object data)
			throws LOLVisitException {
		return visit((SimpleNode) node, node.type.toString());
	}

	@Override
	public Object visit(ASTassignationExpression node, Object data)
			throws LOLVisitException {
		return visit((SimpleNode) node, node.name);
	}

	@Override
	public Object visit(ASTinfiniteArityOperator node, Object data)
			throws LOLVisitException {
		return visit((SimpleNode) node, node.type.toString());
	}

	@Override
	public Object visit(ASTcastVariableExpression node, Object data)
			throws LOLVisitException {
		return visit((SimpleNode) node,
				node.name + " to " + node.type.toString());
	}

	@Override
	public Object visit(ASTcastExpression node, Object data)
			throws LOLVisitException {
		return visit((SimpleNode) node, "to " + node.type.toString());
	}

	@Override
	public Object visit(ASTconsoleInput node, Object data)
			throws LOLVisitException {
		return visit((SimpleNode) node, node.variable);
	}

	@Override
	public Object visit(ASTloopExpression node, Object data)
			throws LOLVisitException {
		String label;

		if (node.operation != LoopOperation.CUSTOM) {
			label = "op:" + node.operation.toString();
		} else {
			label = "op:" + node.customOperation;
		}

		label += "(" + node.loopVariable + ")";

		if (node.loopLimit != null) {
			label += "<br />limit:" + node.loopLimit.toString();
		}

		return visit((SimpleNode) node, label);
	}
}
