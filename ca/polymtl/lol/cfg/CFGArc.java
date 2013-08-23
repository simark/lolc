package ca.polymtl.lol.cfg;

public class CFGArc {
	public CFGArc(CFGNode from, CFGNode to, String label) {
		System.err.println("Creating CFGArc from " + from + " to " + to);
		this.from = from;
		this.to = to;
		this.label = label;

		from.addOut(this);
		to.addIn(this);
	}

	public CFGArc(CFGNode from, CFGNode to) {
		this(from, to, "");
	}

	public CFGNode to() {
		return to;
	}

	public CFGNode from() {
		return from;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	String label;
	CFGNode from, to;
	

}
