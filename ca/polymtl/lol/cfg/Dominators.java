package ca.polymtl.lol.cfg;

import java.io.PrintStream;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;

public class Dominators {
	public static TreeMap<CFGNode, TreeSet<CFGNode>> findDominators(
			Vector<CFGNode> nodes) 	{
		TreeMap<CFGNode, TreeSet<CFGNode>> doms = new TreeMap<CFGNode, TreeSet<CFGNode>>();

		for (int i = 0; i < nodes.size(); i++) {
			CFGNode node = nodes.get(i);

			TreeSet<CFGNode> toAdd = new TreeSet<CFGNode>();

			if (node.isEntry()) {
				toAdd.add(node);
			} else {
				toAdd.addAll(nodes);
			}

			doms.put(node, toAdd);
		}

		boolean change = true;

		while (change) {
			change = false;

			for (int i = 0; i < nodes.size(); i++) {
				CFGNode node = nodes.get(i);

				if (node.isEntry()) {
					continue;
				}

				TreeSet<CFGNode> oldDoms = doms.get(node);
				TreeSet<CFGNode> newDoms = new TreeSet<CFGNode>();

				if (node.getIn().size() > 0) {
					/* We start with all possible nodes */
					newDoms.addAll(nodes);

					for (CFGArc arc : node.getIn()) {
						TreeSet<CFGNode> predecessorsDoms = doms
								.get(arc.from());
						newDoms.retainAll(predecessorsDoms);
					}
				}

				/* Add self */
				newDoms.add(node);

				if (!oldDoms.equals(newDoms)) {
					change = true;
					doms.put(node, newDoms);
				}
			}
		}

		return doms;
	}

	public static TreeMap<CFGNode, TreeSet<CFGNode>> findPostDominators(
			Vector<CFGNode> nodes) {
		TreeMap<CFGNode, TreeSet<CFGNode>> doms = new TreeMap<CFGNode, TreeSet<CFGNode>>();

		for (int i = 0; i < nodes.size(); i++) {
			CFGNode node = nodes.get(i);

			// System.err.println(i + " " + node.getId());

			TreeSet<CFGNode> toAdd = new TreeSet<CFGNode>();

			if (node.isExit()) {
				toAdd.add(node);
			} else {
				toAdd.addAll(nodes);
			}

			doms.put(node, toAdd);
		}

		boolean change = true;

		while (change) {
			change = false;

			for (int i = 0; i < nodes.size(); i++) {
				CFGNode node = nodes.get(i);

				if (node.isExit()) {
					continue;
				}

				TreeSet<CFGNode> oldDoms = doms.get(node);
				TreeSet<CFGNode> newDoms = new TreeSet<CFGNode>();

				if (node.getOut().size() > 0) {
					/* We start with all possible nodes */
					newDoms.addAll(nodes);

					for (CFGArc arc : node.getOut()) {
						TreeSet<CFGNode> predecessorsDoms = doms.get(arc.to());
						newDoms.retainAll(predecessorsDoms);
					}
				}

				/* Add self */
				newDoms.add(node);

				if (!oldDoms.equals(newDoms)) {
					change = true;
					doms.put(node, newDoms);
				}
			}
		}

		return doms;
	}

	public static void print(TreeMap<CFGNode, TreeSet<CFGNode>> out,
			PrintStream output) {
		for (Map.Entry<CFGNode, TreeSet<CFGNode>> entry : out.entrySet()) {
			output.print(entry.getKey().getId() + ": ");

			for (CFGNode node : entry.getValue()) {
				output.print(node.getId() + " ");
			}

			output.println();
		}
	}

	public static void printImmediate(TreeMap<CFGNode, CFGNode> imm,
			PrintStream output) {
		for (Map.Entry<CFGNode, CFGNode> entry : imm.entrySet()) {
			if (entry.getValue() == null) {
				output.println(entry.getKey().getId() + ": none");
			} else {
				output.println(entry.getKey().getId() + ": "
						+ entry.getValue().getId());
			}
		}
	}

	private static void findImm(Stack<CFGNode> curList,
			TreeSet<CFGNode> curSet,
			TreeMap<CFGNode, TreeSet<CFGNode>> dominators,
			TreeMap<CFGNode, CFGNode> imm) {

		for (Map.Entry<CFGNode, TreeSet<CFGNode>> entry : dominators.entrySet()) {
			CFGNode node = entry.getKey();
			if (!curSet.add(node)) {
				continue;
			}

			if (entry.getValue().equals(curSet)) {

				if (curList.size() > 0) {
					imm.put(node, curList.peek());
				} else {
					imm.put(node, null);
				}

				curList.push(node);

				findImm(curList, curSet, dominators, imm);

				curList.pop();
			}

			curSet.remove(node);
		}
	}

	public static TreeMap<CFGNode, CFGNode> findImmediate(
			TreeMap<CFGNode, TreeSet<CFGNode>> dominators) {

		TreeMap<CFGNode, CFGNode> imm = new TreeMap<CFGNode, CFGNode>();

		Stack<CFGNode> curList = new Stack<CFGNode>();
		TreeSet<CFGNode> curSet = new TreeSet<CFGNode>();
		findImm(curList, curSet, dominators, imm);

		return imm;
	}

}
