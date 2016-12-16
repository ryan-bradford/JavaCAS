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
		} else if(opp.equals("cos")) {
			return Math.cos(one);
		} else if(opp.equals("tan")) {
			return Math.tan(one);
		} else if(opp.equals("arctan")) {
			return Math.atan(one);
		} else if(opp.equals("arcsin")) {
			return Math.asin(one);
		} else if(opp.equals("arccos")) {
			return Math.acos(one);
		} else if(opp.equals("ln")) {
			return Math.log(one);
		} else if(opp.equals("log")) {
			return Math.log10(one);
		} else if(opp.equals("abs")) {
			return Math.abs(one);
		}
		return one;
	}
	
}
