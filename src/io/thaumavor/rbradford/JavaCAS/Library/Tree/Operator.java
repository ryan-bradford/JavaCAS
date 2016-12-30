package io.thaumavor.rbradford.JavaCAS.Library.Tree;

public class Operator {

	public String opp;
	public int level; //+and- = 1, *and/ = 2, ^=3
	boolean oddRoot = false;
	
	public Operator(String string) {
		this.opp = string;
		if(opp.equals("+") || opp.equals("-")) {
			level = 1;
		} else if(opp.equals("/") || opp.equals("*")) {
			level = 2;
		} else if(opp.equals("^")) {
			level = 3;
		} else {
			level = 4;
		}
	}
	
	public void oddRoot(boolean isOdd) {
		oddRoot = isOdd;
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
			double temp = Math.pow(Math.abs(one), two);
			if(oddRoot == true && one < 0 && two < 1 && two > -1) {
				return -temp;
			} else if(oddRoot == false && one < 0 && two < 1 && two > -1) {
				return Double.POSITIVE_INFINITY;
			}
			return temp;
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
