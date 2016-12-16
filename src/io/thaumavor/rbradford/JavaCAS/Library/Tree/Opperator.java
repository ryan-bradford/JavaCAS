package io.thaumavor.rbradford.JavaCAS.Library.Tree;

public class Opperator {

	String opp;
	
	public Opperator(String string) {
		this.opp = string;
	}
	
	public double useOpp(double one, double two) {
		if(opp.equals("+")) {
			return one + two;
		} else if(opp.equals("-")) {
			return one - two;
		} else if(opp.equals("*")) {
			return one * two;
		} else if(opp.equals("/")) {
			return one / two;
		} else if(opp.equals("^")) {
			return Math.pow(one, two);
		} else if(opp.equals("sin")) {
			return Math.sin(one);
		}
		return one;
	}
	
}
