package io.thaumavor.rbradford.JavaCAS.Library.Tree;

import io.thaumavor.rbradford.JavaCAS.Library.General;

public class Branch {

	Branch base;
	Branch upper;
	Opperator opperator;
	String value;
	String startFunction;
	String functionPart;
	boolean negative = false;

	public Branch(String functionPart) {
		this.functionPart = functionPart;
		this.startFunction = functionPart;
		removeParenthesis();
		if(!functionPart.contains("+") && !functionPart.contains("-") && !functionPart.contains("*") && !functionPart.contains("/") && !functionPart.contains("^")) {
			if(General.isFunction(functionPart)) {
				splitFunction();
			} else {
				value = functionPart;
			}
		} else {
			splitAtOpperators();
		}
	}
	
	public void splitFunction() {
		for(int i = 0; i < functionPart.length(); i++) {
			if(functionPart.charAt(i) == '(') {
				this.opperator = new Opperator(functionPart.substring(0, i));
				base = new Branch(functionPart.substring(i+1, functionPart.length() - 1));
				return;
			}
		}
		factorNegatives();
	}
	
	public void factorNegatives() {
		if(functionPart.charAt(0) == '-') {
			base = new Branch(functionPart.substring(1, functionPart.length()));
			base.negative = true;
			opperator = new Opperator(" ");
		}
	}
	
	public void splitAtOpperators() {
		int partsIn = 0;
		
		for(int i = 0; i < functionPart.length(); i++) {
			if(functionPart.charAt(i) == '(') {
				partsIn++;
			} else if(functionPart.charAt(i) == ')') {
				partsIn--;
			} 
			if(partsIn == 0 && (functionPart.charAt(i) == '+' || functionPart.charAt(i) == '-')
					&& i>0
					&& (functionPart.charAt(i-1) == ')'||functionPart.charAt(i-1) == 'x'||Character.isDigit(functionPart.charAt(i-1)))
					&& !General.isOpperator(functionPart.charAt(i-1))) {
				createBases(i);
				this.opperator = new Opperator(Character.toString(functionPart.charAt(i)));
				return;
			}
		}
		
		for(int i = 0; i < functionPart.length(); i++) {
			if(functionPart.charAt(i) == '(') {
				partsIn++;
			} else if(functionPart.charAt(i) == ')') {
				partsIn--;
			} 
			if(partsIn == 0 && (functionPart.charAt(i) == '*' || functionPart.charAt(i) == '/')) {
				createBases(i);
				this.opperator = new Opperator(Character.toString(functionPart.charAt(i)));
				return;
			}
		}
		for(int i = 0; i < functionPart.length(); i++) {
			if(functionPart.charAt(i) == '(') {
				partsIn++;
			} else if(functionPart.charAt(i) == ')') {
				partsIn--;
			} 
			if(partsIn == 0 && functionPart.charAt(i) == '^') {
				createBases(i);
				this.opperator = new Opperator(Character.toString(functionPart.charAt(i)));
				return;
			}
		}
		splitFunction();
	}
	
	public void createBases(int i) {
		String basePart = functionPart.substring(0, i);
		String upperPart = functionPart.substring(i+1, functionPart.length());
		base = new Branch(basePart);
		upper = new Branch(upperPart);	
	}
	
	public double getValue(double x) {
		double polarity = 1;
		if(negative) {
			polarity = -1;
		}
		if(value != null) {
			return polarity*General.getValueOfPart(value, x, 0);
		} else {
			if(upper != null) {
				return polarity*opperator.useOpp(base.getValue(x), upper.getValue(x));
			} else {
				return polarity*opperator.useOpp(base.getValue(x), 0);
			}
		}
	}
	
	public double getValue(double x, double y) {
		if(value != null) {
			return General.getValueOfPart(value, x, y);
		} else {
			return opperator.useOpp(base.getValue(x, y), upper.getValue(x, y));
		}
	}
	
	public void removeParenthesis() {
		int countsIn = 0;
		for(int i = 0; i < functionPart.length(); i++) {
			if(functionPart.charAt(i) == '(') {
				if(containsOnly('(', functionPart.substring(0, i))) {
					countsIn++;
				}
			} else if(functionPart.charAt(i) == ')') {
				if(containsOnly(')', functionPart.substring(i, functionPart.length()))) {
					break;
				}
				countsIn--;
			}
			if(countsIn == 0 && i != functionPart.length() - 1) {
				return;
			}
		}
		countsIn += 0;
		functionPart = functionPart.substring(countsIn, functionPart.length() - countsIn);
	}
	
	public boolean containsOnly(char toCheck, String toUse) {
		for(char x: toUse.toCharArray()) {
			if(x != toCheck) {
				return false;
			}
		}
		return true;
	}
	
	public String toString() {
		if(upper != null) {
			return base.toString() + opperator.opp + upper.toString();
		} else if(base != null) {
			return opperator.opp + "(" +  base.toString() + ")";
		} else {
			if(negative) {
				return "-" + value;
			} else {
				return value;
			}
			
		}
	}
}
