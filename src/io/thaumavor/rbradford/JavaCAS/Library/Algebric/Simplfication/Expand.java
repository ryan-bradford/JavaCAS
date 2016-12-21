package io.thaumavor.rbradford.JavaCAS.Library.Algebric.Simplfication;

import io.thaumavor.rbradford.JavaCAS.Library.Tree.Branch;

public class Expand {

	public Branch expandBranch(Branch toExpand) {
		toExpand = expandExponents(toExpand);
		factorBranch(toExpand);
		return toExpand;
	}
	
	public Branch expandExponents(Branch toExpand) {
		if(toExpand.operator != null) {
			if(toExpand.operator.opp.equals("^")) {
				try {
					double exponent = Double.parseDouble(toExpand.upper.functionPart);
					int exponentFor = (int) Math.floor(exponent);
					double exponentLeftover = exponent - exponentFor;
					String newBase = "";
					for(int i = 0; i < exponentFor; i++) {
						if(i > 0) {
							newBase += "*";
						}
						newBase += "(" + toExpand.base.functionPart + ")";
					}
					if(exponentLeftover != 0.0) {
						newBase += "*" + "(" + toExpand.base.functionPart + ")^" + exponentLeftover;
					}
					toExpand.initFunction(newBase);
				} catch(Exception ex) {
					
				}
			} else if(toExpand.upper != null) {
				toExpand.upper = expandExponents(toExpand.upper);
				toExpand.base = expandExponents(toExpand.base);
			} else {
				toExpand.base = expandExponents(toExpand.base);
			}
		}
		return toExpand;
	}
	
	public void factorBranch(Branch toFactor) {
		if(toFactor.operator.opp.equals("*")) {
			Branch multiplyOne = toFactor.base;
			Branch multiplyTwo = toFactor.upper;
			while(multiplyTwo.operator != null && !toFactor.operator.opp.equals("+") && !toFactor.operator.opp.equals("-")) {
				multiplyTwo = multiplyTwo.base;
				System.out.println(multiplyTwo.toString());
			}
			System.out.println(multiplyTwo.toString());
		}
	}
	
}
