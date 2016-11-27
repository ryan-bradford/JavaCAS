package io.thaumavor.rbradford.JavaCAS.Library.FunctionTools.Algebric;

import java.util.ArrayList;

import io.thaumavor.rbradford.JavaCAS.Library.Function;

public class Simplify {

	Function superFunction;
	String newFunction;

	public Simplify(Function baseFunction) {
		this.superFunction = baseFunction;
		newFunction = "";
	}
	
	public void simplify() {
		expandExponents();
		superFunction.baseFunction = newFunction;
		System.out.println("Simplified Function: " + newFunction);
	}
	
	public void expandExponents() {
		int firstPointTouched = 0;
		int lastPointTouched = 0;
		for(int i = 0; i < superFunction.baseFunction.toCharArray().length; i++) {
			if(superFunction.baseFunction.toCharArray()[i] == '^') {
				int[] beforeRange = rangeAffectedByOpperator(i, false, 3);
				int[] afterRange = rangeAffectedByOpperator(i, true, 3);
				String before = superFunction.baseFunction.substring(beforeRange[0], beforeRange[1]);
				String after = superFunction.baseFunction.substring(afterRange[0], afterRange[1]);
				if(beforeRange[0] != firstPointTouched) {
					newFunction = superFunction.baseFunction.substring(firstPointTouched, beforeRange[0]);
				}
				lastPointTouched = afterRange[1];
				if(!after.contains("x")) {
					double val = new Function(after).getValueAt(1);
					for(int x = 0; x < Math.floor(val / 1); x++) {
						newFunction += before;
						if(x != Math.floor(val / 1) - 1) {
							newFunction += "*";
						}
					}
					if(val % 1.0 != 0) {
						newFunction += "*" + before + "^" + val % 1.0;
					}
				}
				System.out.println(superFunction.baseFunction.substring(afterRange[0], afterRange[1]));
			}
		}
		newFunction += superFunction.baseFunction.substring(lastPointTouched, superFunction.baseFunction.length());
	}
	
	public int[] rangeAffectedByOpperator(int position, boolean forwardsOrBackWards, int level) { //level 1 = addion, 2 = multiplication, 3 = exponents, 4 = grouping
		if(level == 3) {
			int toClose = 0;
			if(forwardsOrBackWards == false) {
				for(int i = position - 1; i >= 0; i--) {
					if(superFunction.baseFunction.charAt(i) == ')') {
						toClose++;
					} else  {
						if(superFunction.baseFunction.charAt(i) == '(') {
							toClose--;
	
						} 
						if(toClose == 0) {
							return new int[]{i, position};
						}
					}

				}
			} else {
				for(int i = position+1; i < superFunction.baseFunction.length(); i++) {
					if(superFunction.baseFunction.charAt(i) == '(') {
						toClose++;
					} else  {
						if(superFunction.baseFunction.charAt(i) == ')') {
							toClose--;
	
						} 
						if(toClose == 0) {
							return new int[]{position+1, i+1};
						}
					}

				}
			}
		}
		return new int[]{0,0};
	}
	
	public String addCorrectOpperator(String toCheck, String toAddTo, int position, ArrayList<String> opperatorsToCheck) {
		int count = 0;
		for(char x: toCheck.toCharArray()) {
			if(opperatorsToCheck.contains(Character.toString(x))) {
				if(count == position) {
					toAddTo += x;
					break;
				}
				count++;
			}
		}
		return toAddTo;
	}
	
}
