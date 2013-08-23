package ca.polymtl.lol.cfg;

import java.util.Vector;


public class CFGNode implements Comparable<CFGNode> {
	public CFGNode(int id) {
		this(id, "<nolabel>");
	}

	public CFGNode(int id, String label) {
		System.err.println("Creating CFGNode with label = " + label);
		this.label = label;
		this.id = id;
	}

	public void addOut(CFGArc arc) {
		this.out.add(arc);
	}

	public void addIn(CFGArc arc) {
		this.in.add(arc);
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String toString() {
		return "CFGNode[" + getId() + ", " + label + "]";
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public Vector<CFGArc> getOut() {
		return out;
	}

	public Vector<CFGArc> getIn() {
		return in;
	}
	
	public String getLabel() {
		return label;
	}

	public void setEntry(boolean entry) {
		this.entry = entry;
	}

	public boolean isEntry() {
		return entry;
	}
	
	public void setExit(boolean exit) {
		this.exit = exit;
	}
	
	public boolean isExit() {
		return exit;
	}
	

	private int id;
	private String label;
	private boolean entry = false;
	private boolean exit = false;

	private Vector<CFGArc> out = new Vector<CFGArc>();
	private Vector<CFGArc> in = new Vector<CFGArc>();

	@Override
	public int compareTo(CFGNode o) {
		return this.id - o.id;
	}

}
