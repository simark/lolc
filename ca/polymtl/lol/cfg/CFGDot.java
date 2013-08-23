package ca.polymtl.lol.cfg;

import java.io.PrintStream;
import java.util.Map;
import java.util.Vector;

public class CFGDot {
	public static void printCFG(Vector<CFGNode> nodes,
			Map<CFGNode, Boolean> reachable, PrintStream out) {

		out.println("digraph d {");
		out.println("graph [label=<Control Flow Graph>, labelloc=<top>];");
		out.println("node [shape=box, fontsize=10, margin=<0.05>];");
		out.println("edge [shape=box, fontsize=10, margin=<0.05>, decorate];");

		for (CFGNode node : nodes) {
			String bgcolor = "white";
			if (!reachable.get(node)) {
				bgcolor = "LightCoral";
			} else if (node.isEntry()) {
				bgcolor = "Chartreuse";
			} else if (node.isExit()) {
				bgcolor = "Gold";
			}
			int id = node.getId();

			out.println("node" + node.getId() + " [label=<" + id + "<br />"
					+ node.getLabel() + ">, style=filled, fillcolor=<"
					+ bgcolor + ">];");

		}

		for (CFGNode node : nodes) {
			for (CFGArc arc : node.getOut()) {
				CFGNode from = arc.from();
				CFGNode to = arc.to();

				String color;

				if (arc.getLabel().equals("true")) {
					color = "blue";
				} else if (arc.getLabel().equals("false")) {
					color = "red";
				} else if (arc.getLabel().equals("loop exit")) {
					color = "magenta";
				} else if (arc.getLabel().equals("break")) {
					color = "orange";
				} else if (arc.getLabel().equals("loop entry")) {
					color = "green";
				} else {
					color = "black";
				}

				out.println("node" + from.getId() + " -> node" + to.getId()
						+ " [label=<" + arc.getLabel() + ">, color=<" + color
						+ ">, fontcolor=<" + color + ">];");
			}
		}

		out.println("}");
	}

	public static void printDomTree(Map<CFGNode, CFGNode> imm,
			boolean post, PrintStream out) {

		out.println("digraph domtree {");

		if (post) {
			out.println("graph [label=<Post Dominator Tree>, labelloc=<top>];");
		} else {
			out.println("graph [label=<Dominator Tree>, labelloc=<top>];");
		}
		for (Map.Entry<CFGNode, CFGNode> entry : imm.entrySet()) {
			CFGNode child = entry.getKey();
			out.println("node" + child.getId() + "[ label=" + child.getId()
					+ " ];");
		}

		for (Map.Entry<CFGNode, CFGNode> entry : imm.entrySet()) {
			CFGNode parent = entry.getValue();
			CFGNode child = entry.getKey();

			if (parent != null) {
				out.println("node" + parent.getId() + " -> node"
						+ child.getId() + ";");
			}
		}

		out.println("}");
	}

}
