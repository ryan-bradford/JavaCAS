package io.thaumavor.rbradford.JavaCAS.Library.Algebric;

import io.thaumavor.rbradford.JavaCAS.Library.Tree.Branch;
import io.thaumavor.rbradford.JavaCAS.Library.Tree.Operator;

public class Simplify {

	Branch current;
	
	public Simplify(Branch current) {
		this.current = current;
	}
	
	public void simplify(String toUse, Operator action) {
		if(!simplifyDivision(toUse, action) && !simplifyMultiplication(toUse, action)
				&& !simplifySubtraction(toUse, action)) {
			if(current.base == null) {
				current.initFunction(current.value + action.opp + toUse);
			} else if(current.upper == null) {
				current.initFunction(current.operator.opp + "(" + current.functionPart + ")" + action.opp + toUse);
			} else { 
				current.base.simplify.simplify(toUse, action);
				current.upper.simplify.simplify(toUse, action);
			}
		}
	}
	
	public boolean simplifyMultiplication(String toUse, Operator action) {
		if(!action.opp.equals("*")) {
			return false;
		}
		if(current.operator != null) {
			if(current.operator.opp.equals("/")) {
				if(current.upper.functionPart.equals(toUse)) {
					current.upper.initFunction("1");
				} else {
					current.base.initFunction(current.base.functionPart + "*" + toUse);
				}
				return true;
			} else {
				if(current.upper != null) {
					if(current.operator.level < action.level) {
						current.upper.simplify.simplify(toUse, action);
						current.base.simplify.simplify(toUse, action);
						return true;
					}
				} else if(current.base != null) {
					return true;
				}
			}
		} else {
			current.initFunction(current.value + action.opp + toUse);
			return true;
		}
		return false;
	}
	
	public boolean simplifyDivision(String toUse, Operator action) {
		if(!action.opp.equals("/")) {
			return false;
		}
		if(current.operator == null) {
			if(current.value.equals(toUse)) {
				current.initFunction("1");
			} else {
				current.initFunction(current.value + action.opp + toUse);
			}
			return true;
		} else if(current.operator != null && current.operator.opp.equals("/")) {
			current.base.simplify.simplify(toUse, action);
			return true;
		} else if(current.operator != null && current.base.negative && current.base.functionPart.equals(toUse)) {
			current.base.initFunction("-1");
			return true;
		} else if(current.operator.opp.equals("^")) {
			if(current.base.functionPart.equals(toUse)) {
				current.upper.simplify.simplify("1", new Operator("-"));
			} else {
				current.initFunction("(" + current.base.toString() + current.operator.opp + current.upper.toString() + ")" + action.opp + toUse);
			}
			return true;
		}
		return false;
	}
	
	public boolean simplifySubtraction(String toUse, Operator action) {
		if(current.operator != null) {
			if(current.operator.opp.equals("+")) {
				if(current.base.simplify.simplifySubtraction(toUse, action)) {
					return true;
				} else if(current.upper.simplify.simplifySubtraction(toUse, action)) {
					return true;
				} else {
					current.initFunction(current.base.toString() + "+" + current.upper.toString() + "-" + toUse);
					return true;
				}
			}
		} else {
			if(current.value.equals(toUse)) {
				current.initFunction("0");
				return true;
			}
		}
		return false;
	}
	
}
