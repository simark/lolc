package ca.polymtl.lol.cfg;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;

import ca.polymtl.lol.LoopLimit;
import ca.polymtl.lol.LoopOperation;
import ca.polymtl.lol.grammar.ASTassignationExpression;
import ca.polymtl.lol.grammar.ASTbreakExpression;
import ca.polymtl.lol.grammar.ASTcaseBlock;
import ca.polymtl.lol.grammar.ASTcastVariableExpression;
import ca.polymtl.lol.grammar.ASTconditionalExpression;
import ca.polymtl.lol.grammar.ASTconsoleInput;
import ca.polymtl.lol.grammar.ASTconsoleOutput;
import ca.polymtl.lol.grammar.ASTdefaultBlock;
import ca.polymtl.lol.grammar.ASTelseBlock;
import ca.polymtl.lol.grammar.ASTfunctionDefinition;
import ca.polymtl.lol.grammar.ASTloopExpression;
import ca.polymtl.lol.grammar.ASTprimaryExpression;
import ca.polymtl.lol.grammar.ASTreturnExpression;
import ca.polymtl.lol.grammar.ASTstart;
import ca.polymtl.lol.grammar.ASTswitchExpression;
import ca.polymtl.lol.grammar.ASTthenBlock;
import ca.polymtl.lol.grammar.ASTvariableDeclaration;
import ca.polymtl.lol.grammar.LOLPublic;
import ca.polymtl.lol.grammar.LOLVisitException;
import ca.polymtl.lol.grammar.LOLVisitorBasic;
import ca.polymtl.lol.grammar.Node;
import ca.polymtl.lol.grammar.ParseException;
import ca.polymtl.lol.grammar.SimpleNode;
import ca.polymtl.lol.grammar.VisiteurAffichageCoquet;

public class VisiteurCFG extends LOLVisitorBasic {
	public static void main(String[] args) throws ParseException,
			LOLVisitException, FileNotFoundException {
		/* Create the visitor, the parser, parse the input and */
		VisiteurCFG vis = new VisiteurCFG();
		LOLPublic lol = new LOLPublic(System.in);
		SimpleNode topNode = lol.Start();
		topNode.jjtAccept(vis, null);

		/* Get the CFG nodes */
		Vector<CFGNode> nodes = vis.getCFGNodes();

		/*
		 * Reverse the node ids so that they appear to be in order in the graph.
		 */
		int nb = nodes.size();
		for (CFGNode node : nodes) {
			node.setId(nb - node.getId() - 1);
		}

		/* Compute dominators */
		TreeMap<CFGNode, TreeSet<CFGNode>> dominators = Dominators
				.findDominators(nodes);

		System.err.println();
		System.err.println("Dominators:");
		Dominators.print(dominators, System.err);

		/* Compute immediate dominators */
		TreeMap<CFGNode, CFGNode> immediateDominators = Dominators
				.findImmediate(dominators);

		System.err.println();
		System.err.println("Immediate dominators:");
		Dominators.printImmediate(immediateDominators, System.err);

		/* Compute postdominators */
		TreeMap<CFGNode, TreeSet<CFGNode>> postDominators = Dominators
				.findPostDominators(nodes);

		System.err.println();
		System.err.println("Postdominators:");
		Dominators.print(postDominators, System.err);

		/* Compute immediate postdominators */
		TreeMap<CFGNode, CFGNode> immediatePostDominators = Dominators
				.findImmediate(postDominators);

		System.err.println();
		System.err.println("Immediate postdominators:");
		Dominators.printImmediate(immediatePostDominators, System.err);

		/* Compute reachable nodes */
		TreeMap<CFGNode, Boolean> reachable = Reachable.findReachable(nodes);

		/* Output CFG if asked */
		if (args.length > 0) {
			String filename = args[0];

			CFGDot.printCFG(nodes, reachable, new PrintStream(filename));
		}

		/* Output dom tree if asked */
		if (args.length > 1) {
			String filename = args[1];

			CFGDot.printDomTree(immediateDominators, false, new PrintStream(
					filename));
		}

		/* Output pdom tree if asked */
		if (args.length > 2) {
			String filename = args[2];

			CFGDot.printDomTree(immediatePostDominators, true, new PrintStream(
					filename));
		}

		System.err.println();
		System.err.println("Done");
	}

	/**
	 * CFGNodes that we pass to children in the AST.
	 */
	private class NodesToLink {
		public NodesToLink() {
		}

		public NodesToLink(NodesToLink other) {
			this.next = other.next;
			this.functionEnd = other.functionEnd;
			this.loopEnd = other.loopEnd;
		}

		CFGNode next;
		CFGNode functionEnd;
		CFGNode loopEnd;
	}

	/**
	 * List of nodes in the CFG created by the visitor.
	 */
	private Vector<CFGNode> cfgNodes = new Vector<CFGNode>();

	/**
	 * Next node id to be attributed.
	 */
	private int nodeId = 0;

	/**
	 * Get next node id.
	 * 
	 * @return The node id.
	 */
	private int nextNodeId() {
		int ret = nodeId;
		nodeId++;
		return ret;
	}

	/**
	 * Creates a CFG node.
	 * 
	 * @param label
	 *            The text label to write in the node.
	 * @return The node.
	 */
	private CFGNode createNode(String label) {
		CFGNode node = new CFGNode(nextNodeId(), label);

		cfgNodes.add(node);

		return node;
	}

	/**
	 * Creates a CFG node from an AST node. The label will be the pretty printed
	 * code generated from the AST node.
	 * 
	 * @param n
	 *            AST node
	 * @return The CFG node.
	 * @throws LOLVisitException
	 */
	private CFGNode createNode(Node n) throws LOLVisitException {
		return createNode(prettyPrintNode(n));
	}

	/**
	 * Returns the CFG nodes created by the visitor.
	 * 
	 * @return That.
	 */
	public Vector<CFGNode> getCFGNodes() {
		return cfgNodes;
	}

	@Override
	public Object visit(SimpleNode node, Object data) throws LOLVisitException {

		throw new LOLVisitException("Method not defined for "
				+ node.getClass().getSimpleName());
	}

	@Override
	public Object visit(ASTstart node, Object data) throws LOLVisitException {
		CFGNode end = createNode("(end)");
		end.setExit(true);

		NodesToLink toLink = new NodesToLink();
		toLink.next = end;

		CFGNode lastCreated = containerCommon(node, toLink);

		CFGNode start = createNode("(start)");
		start.setEntry(true);
		new CFGArc(start, lastCreated);

		return null;
	}

	@Override
	public Object visit(ASTconsoleOutput node, Object data)
			throws LOLVisitException {
		return common(node, (NodesToLink) data);
	}

	@Override
	public Object visit(ASTconsoleInput node, Object data)
			throws LOLVisitException {
		return common(node, (NodesToLink) data);
	}

	@Override
	public Object visit(ASTvariableDeclaration node, Object data)
			throws LOLVisitException {
		return common(node, (NodesToLink) data);
	}

	@Override
	public Object visit(ASTconditionalExpression node, Object data)
			throws LOLVisitException {
		int nbElseIf = node.nbElseIf;
		boolean hasElse = node.hasElse;
		NodesToLink ntl = (NodesToLink) data;

		CFGNode nextCondition;

		/* Else branch */
		if (hasElse) {
			Node elseBlock = node.jjtGetChild(node.jjtGetNumChildren() - 1);
			CFGNode elseBranch = (CFGNode) elseBlock.jjtAccept(this, ntl);
			nextCondition = elseBranch;
		} else {
			nextCondition = ((NodesToLink) data).next;
		}

		/* Else if branches */
		for (int i = nbElseIf; i > 0; i--) {
			Node elseIfBlock = node.jjtGetChild(i);

			Node condition = elseIfBlock.jjtGetChild(0);

			CFGNode elseIfBranch = containerCommon(elseIfBlock, ntl, 1);
			CFGNode thisCondition = (CFGNode) condition.jjtAccept(this, null);

			/* Create the false link */
			new CFGArc(thisCondition, nextCondition, "false");

			/* Create the true link */
			new CFGArc(thisCondition, elseIfBranch, "true");

			nextCondition = thisCondition;
		}

		/* Then branch */
		Node thenBlock = node.jjtGetChild(0);
		CFGNode thenBranch = (CFGNode) thenBlock.jjtAccept(this, ntl);

		/* Create the if node and its links */
		CFGNode ifNode = createNode("IF");
		new CFGArc(ifNode, nextCondition, "false");
		new CFGArc(ifNode, thenBranch, "true");

		return ifNode;
	}

	@Override
	public Object visit(ASTthenBlock node, Object data)
			throws LOLVisitException {
		return containerCommon(node, (NodesToLink) data);
	}

	@Override
	public Object visit(ASTelseBlock node, Object data)
			throws LOLVisitException {
		return containerCommon(node, (NodesToLink) data);
	}

	@Override
	public Object visit(ASTprimaryExpression node, Object data)
			throws LOLVisitException {
		CFGNode createdNode = createNode(node);

		if (data != null) {
			CFGNode next = ((NodesToLink) data).next;
			new CFGArc(createdNode, next);
		}

		return createdNode;
	}

	@Override
	public Object visit(ASTfunctionDefinition node, Object data)
			throws LOLVisitException {
		CFGNode end = createNode("(func " + node.name + " end)");
		end.setExit(true);

		NodesToLink ntl = new NodesToLink();
		ntl.next = end;
		ntl.functionEnd = end;

		CFGNode lastCreated = containerCommon(node, ntl);

		CFGNode start = createNode("(func " + node.name + " start)");
		start.setEntry(true);
		new CFGArc(start, lastCreated);

		return null;
	}

	@Override
	public Object visit(ASTreturnExpression node, Object data)
			throws LOLVisitException {
		CFGNode toLink = ((NodesToLink) data).functionEnd;

		if (toLink == null) {
			throw new LOLVisitException("Return statement not in a function");
		}

		CFGNode newNode = createNode(node);

		new CFGArc(newNode, toLink);

		return newNode;
	}

	@Override
	public Object visit(ASTassignationExpression node, Object data)
			throws LOLVisitException {
		return common(node, (NodesToLink) data);
	}

	@Override
	public Object visit(ASTloopExpression node, Object data)
			throws LOLVisitException {
		CFGNode loopNode;
		CFGNode loopOpNode = null;

		NodesToLink ntl = new NodesToLink((NodesToLink) data);
		ntl.loopEnd = ntl.next;

		/*
		 * If there is a loop limit, control can jump from the loop node to the
		 * statement after the loop
		 */
		if (node.loopLimit == LoopLimit.UNTIL) {
			loopNode = createNode("loop until "
					+ prettyPrintNode(node.jjtGetChild(0)));
			new CFGArc(loopNode, ntl.loopEnd, "loop exit");
		} else if (node.loopLimit == LoopLimit.WHILE) {
			loopNode = createNode("loop while "
					+ prettyPrintNode(node.jjtGetChild(0)));
			new CFGArc(loopNode, ntl.loopEnd, "loop exit");
		} else {
			loopNode = createNode("infinite loop");
		}

		if (node.operation == LoopOperation.INCREMENT) {
			loopOpNode = createNode("Loop operation: increment "
					+ node.loopVariable);
		} else if (node.operation == LoopOperation.DECREMENT) {
			loopOpNode = createNode("Loop operation: decrement "
					+ node.loopVariable);
		} else if (node.operation == LoopOperation.CUSTOM) {
			loopOpNode = createNode("Loop operation: call to "
					+ node.customOperation + "(" + node.loopVariable + ")");
		}

		/*
		 * If there is a loop operation, it is the last statement before looping
		 * back
		 */
		if (loopOpNode != null) {
			new CFGArc(loopOpNode, loopNode, "loop back");
			ntl.next = loopOpNode;
		} else {
			ntl.next = loopNode;
		}

		CFGNode loopContentNode;
		if (node.loopLimit != null) {
			loopContentNode = containerCommon(node, ntl, 1);
		} else {
			loopContentNode = containerCommon(node, ntl);
		}

		/* Arc between loop node and the first element */
		new CFGArc(loopNode, loopContentNode, "loop entry");

		return loopNode;
	}

	@Override
	public Object visit(ASTbreakExpression node, Object data)
			throws LOLVisitException {
		CFGNode toLink = ((NodesToLink) data).loopEnd;

		if (toLink == null) {
			throw new LOLVisitException("Break statement not in loop or switch");
		}

		CFGNode newNode = createNode(node);

		new CFGArc(newNode, toLink, "break");

		return newNode;
	}

	@Override
	public Object visit(ASTcastVariableExpression node, Object data)
			throws LOLVisitException {
		return common(node, (NodesToLink) data);
	}

	@Override
	public Object visit(ASTswitchExpression node, Object data)
			throws LOLVisitException {

		CFGNode switchNode = createNode("switch");
		CFGNode afterSwitchNode = ((NodesToLink) data).next;

		for (int i = 0; i < node.jjtGetNumChildren(); i++) {
			NodesToLink ntl = new NodesToLink((NodesToLink) data);

			ntl.loopEnd = ntl.next;

			Node child = node.jjtGetChild(i);

			CFGNode createdNode = (CFGNode) child.jjtAccept(this, ntl);

			if (node.hasDefault && i == node.jjtGetNumChildren() - 1) {
				new CFGArc(switchNode, createdNode, "default");
			} else {
				new CFGArc(switchNode, createdNode,
						prettyPrintNode(((ASTcaseBlock) child).jjtGetChild(0)));
			}
		}

		if (!node.hasDefault) {
			new CFGArc(switchNode, afterSwitchNode, "default");
		}

		return switchNode;
	}

	@Override
	public Object visit(ASTcaseBlock node, Object data)
			throws LOLVisitException {
		return containerCommon(node, (NodesToLink) data, 1);
	}

	@Override
	public Object visit(ASTdefaultBlock node, Object data)
			throws LOLVisitException {
		return containerCommon(node, (NodesToLink) data);
	}

	private CFGNode containerCommon(Node node, NodesToLink toLink, int stop)
			throws LOLVisitException {
		CFGNode lastCreated = toLink.next;

		for (int i = node.jjtGetNumChildren() - 1; i >= stop; i--) {
			NodesToLink ntl = new NodesToLink(toLink);
			ntl.next = lastCreated;

			Node child = node.jjtGetChild(i);

			CFGNode created = (CFGNode) child.jjtAccept(this, ntl);

			if (created != null) {
				ntl.next = created;
				lastCreated = created;
			}

		}

		return lastCreated;
	}

	private CFGNode containerCommon(Node node, NodesToLink toLink)
			throws LOLVisitException {
		return containerCommon(node, toLink, 0);
	}

	private CFGNode common(Node node, NodesToLink nodesToLink)
			throws LOLVisitException {
		CFGNode toLink = nodesToLink.next;

		CFGNode newNode = createNode(node);

		new CFGArc(newNode, toLink);

		return newNode;
	}

	private String prettyPrintNode(Node n) throws LOLVisitException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		VisiteurAffichageCoquet v = new VisiteurAffichageCoquet(out);

		n.jjtAccept(v, null);

		return out.toString();
	}
}
