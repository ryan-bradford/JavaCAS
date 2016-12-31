package io.thaumavor.rbradford.JavaCAS.Library.Tree;

import io.thaumavor.rbradford.JavaCAS.Library.General;
import io.thaumavor.rbradford.JavaCAS.Library.Algebric.Simplify;

public class Branch {

	public Branch base;
	public Branch upper;
	public Operator operator;
	public String value;
	public String startFunction;
	public String functionPart;
	public boolean negative = false;
	public int numOfParenthesis = 0;
	public Simplify simplify;
	
	protected Branch(String functionPart) {
		this.functionPart = functionPart;
		this.startFunction = functionPart;
		initFunction();
		this.simplify = new Simplify(this);
	}
	
	public void initFunction(String function) {
		this.functionPart = function;
		this.initFunction();
	}
	
	protected void initFunction() {
		this.startFunction = functionPart;
		base = null;
		upper = null;
		operator = null;
		value = null;
		negative = false;
		numOfParenthesis = 0;
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
				this.operator = new Operator(functionPart.substring(0, i));
				base = new Branch(functionPart.substring(i+1, functionPart.length() - 1));
				return;
			}
		}
	}
	
	public void factorNegatives() {
		if(functionPart.charAt(0) == '-') {
			this.initFunction(functionPart.substring(1, functionPart.length()));
			negative = true;
		}
		splitFunction();
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
				this.operator = new Operator(Character.toString(functionPart.charAt(i)));
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
				this.operator = new Operator(Character.toString(functionPart.charAt(i)));
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
				this.operator = new Operator(Character.toString(functionPart.charAt(i)));
				return;
			}
		}
		factorNegatives();
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
				if(operator.opp.equals("^") && upper.operator != null && upper.operator.opp.equals("/")) {
					double upperVal = upper.upper.getValue(x);
					double lowerVal = upper.base.getValue(x);
					if(upperVal > lowerVal) {
						upperVal /= lowerVal;
						lowerVal = 1;
					} else {
						lowerVal /= upperVal;
						upperVal = 1;
					}
					if(upperVal != 1.0 && upperVal % 2.0 == 1.0) {
						operator.oddRoot(true);
					} else if(lowerVal != 1.0 && lowerVal % 2.0 == 1.0) {
						operator.oddRoot(true);
					} else {
						operator.oddRoot(false);
					}
				}
				return polarity*operator.useOpp(base.getValue(x), upper.getValue(x));
			} else {
				return polarity*operator.useOpp(base.getValue(x), 0);
			}
		}
	}
	
	public double getValue(double x, double y) {
		if(value != null) {
			return General.getValueOfPart(value, x, y);
		} else {
			if(operator.opp.equals("^") && base.operator.opp.equals("/")) {
				System.out.println(base.upper.getValue(x, y) % 2);
				if(base.upper.getValue(x, y) % 2 == 1) {
					operator.oddRoot(true);
				}
			}
			return operator.useOpp(base.getValue(x, y), upper.getValue(x, y));
		}
	}
	
	public void removeParenthesis() {
		numOfParenthesis = 0;
		for(int i = 0; i < functionPart.length(); i++) {
			if(functionPart.charAt(i) == '(') {
				if(containsOnly('(', functionPart.substring(0, i))) {
					numOfParenthesis++;
				}
			} else if(functionPart.charAt(i) == ')') {
				if(containsOnly(')', functionPart.substring(i, functionPart.length()))) {
					break;
				}
				numOfParenthesis--;
			}
			if(numOfParenthesis == 0 && i != functionPart.length() - 1) {
				return;
			}
		}
		functionPart = functionPart.substring(numOfParenthesis, functionPart.length() - numOfParenthesis);
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
			String surrond = "";
			String back = "";
			for(int i = 0; i < numOfParenthesis; i++) {
				surrond += "(";
				back += ")";
			}
			return surrond + base.toString() + operator.opp + upper.toString() + back;
		} else if(base != null) {
			return operator.opp + "(" +  base.toString() + ")";
		} else {
			if(negative) {
				return "-" + value;
			} else {
				return value;
			}
			
		}
	}
	
	
}
