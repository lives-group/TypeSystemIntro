package lang.ast;

import lang.ast.types.SType;

public class Pair extends SuperNode{
	private SType x;
	private String y;
	
	public Pair(SType x, String y) {
		this.x = x;
		this.y = y;
	}
	
	public SType getFirst(){ return x;}
	public String getSecond(){return y;}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Pair) {
			Pair pair = (Pair) obj;
			return x.equals(pair.x) && y.equals(pair.y);
		}
		return false;
	}

	@Override
	public String toString() {
		return "(" + x.toString() + ", " + y.toString() + ")";
	}
	
	public void accept(NodeVisitor v){ v.visit(this);}

}
