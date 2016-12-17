package io.thaumavor.rbradford.JavaCAS.Library.Tree;

import io.thaumavor.rbradford.JavaCAS.Library.General;

public class Branch {

	Branch base;
	Branch upper;
	Operator operator;
	String value;
	String startFunction;
	String functionPart;
	boolean negative = false;
	int numOfParenthesis = 0;
	
	protected Branch(String functionPart) {
		this.functionPart = functionPart;
		this.startFunction = functionPart;
		initFunction();
	}
	
	public void intiFunction(String function) {
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
		factorNegatives();
	}
	
	public void factorNegatives() {
		if(functionPart.charAt(0) == '-') {
			base = new Branch(functionPart.substring(1, functionPart.length()));
			base.negative = true;
			operator = new Operator(" ");
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
	
	public void simplify(String toUse, Operator action) {
		if(!simplifyDivision(toUse, action)&&!simplifyMultiplication(toUse, action)) {
			if(upper == null) {
				this.intiFunction(operator.opp + "(" + base.functionPart + ")" + action.opp + toUse);
			} else {
				base.simplify(toUse, action);
				upper.simplify(toUse, action);
			}
		}
	}
	
	public boolean simplifyMultiplication(String toUse, Operator action) {
		if(!action.opp.equals("*")) {
			return false;
		}
		if(operator != null) {
			if(operator.opp.equals("/")) {
				if(upper.functionPart.equals(toUse)) {
					upper.intiFunction("1");
				} else {
					base.intiFunction(base.functionPart + "*" + toUse);
				}
				return true;
			} else {
				if(upper != null) {
					if(operator.level < action.level) {
						upper.simplify(toUse, action);
						base.simplify(toUse, action);
						return true;
					}
				} else if(base != null) {
					return true;
				}
			}
		} else {
			this.intiFunction(value + action.opp + toUse);
			return true;
		}
		return false;
	}
	
	public boolean simplifyDivision(String toUse, Operator action) {
		if(operator == null && action.opp.equals("/")) {
			if(value.equals(toUse) && action.opp.equals("/")) {
				this.intiFunction("1");
			} else {
				this.intiFunction(value + action.opp + toUse);
			}
			return true;
		} else if(operator != null && operator.opp.equals("/") && action.opp.equals("/")) {
			base.simplify(toUse, action);
			return true;
		}
		return false;
	}
	
}
