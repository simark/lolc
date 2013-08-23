package ca.polymtl.lol.cfg;

import java.util.TreeMap;
import java.util.Vector;

public class Reachable {
	public static TreeMap<CFGNode, Boolean> findReachable(Vector<CFGNode> nodes) {
		TreeMap<CFGNode, Boolean> ret = new TreeMap<CFGNode, Boolean>();

		for (CFGNode node : nodes) {
			if (node.isEntry()) {
				ret.put(node, true);
			} else {
				ret.put(node, false);
			}
		}

		boolean change = true;

		while (change) {
			change = false;

			for (CFGNode node : nodes) {
				if (ret.get(node)) {
					continue;
				}

				if (node.getIn().size() > 0) {

					for (CFGArc arc : node.getIn()) {
						CFGNode predecessor = arc.from();
						if (ret.get(predecessor)) {
							ret.put(node, true);
							change = true;
						}
					}
				}
			}
		}

		return ret;
	}
}
