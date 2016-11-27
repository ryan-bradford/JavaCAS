package io.thaumavor.rbradford.JavaCAS.Library.FunctionTools.Algebric.Simplification;

import java.util.ArrayList;

import io.thaumavor.rbradford.JavaCAS.Library.Function;

public class Expansion {

	Simplify simplify;
	Function superFunction;
	
	public Expansion(Simplify simplify) {
		this.simplify = simplify;
		superFunction = simplify.superFunction;
	}
	
	public void expand() {
		expandExponents();
		ArrayList<ArrayList<String>> parts = splitIntoLargestParts();
		simplify.newFunction = "";
		if (parts.get(0).size() != 1) {
			int count = 0;
			for (String part : parts.get(0)) {
				Function functionPart = new Function(part);
				functionPart.simplify.simplify();
				simplify.newFunction += functionPart.baseFunction;
				if (count != parts.get(0).size() - 1) {
					simplify.newFunction += parts.get(1).get(count);
				}
				count++;
			}
		} else {
			simplify.newFunction = parts.get(0).get(0);
		}
		superFunction.baseFunction = simplify.newFunction;
	}
	
	public void expandExponents() {
		int firstPointTouched = 0;
		int lastPointTouched = 0;
		for (int i = 0; i < superFunction.baseFunction.toCharArray().length; i++) {
			if (superFunction.baseFunction.toCharArray()[i] == '^') {
				int[] beforeRange = rangeAffectedByOpperator(i, false, 3);
				int[] afterRange = rangeAffectedByOpperator(i, true, 3);
				String before = superFunction.baseFunction.substring(beforeRange[0], beforeRange[1]);
				String after = superFunction.baseFunction.substring(afterRange[0], afterRange[1]);
				if (beforeRange[0] != firstPointTouched) {
					simplify.newFunction = superFunction.baseFunction.substring(firstPointTouched, beforeRange[0]);
				}
				lastPointTouched = afterRange[1];
				if (!after.contains("x")) {
					double val = new Function(after).getValueAt(1);
					for (int x = 0; x < Math.floor(val / 1); x++) {
						simplify.newFunction += before;
						if (x != Math.floor(val / 1)) {
							simplify.newFunction += "*";
						}
					}
					if (val % 1.0 != 0) {
						simplify.newFunction += before + "^" + val % 1.0;
					}
				}
			}
		}
		simplify.newFunction += superFunction.baseFunction.substring(lastPointTouched, superFunction.baseFunction.length());
	}

	public int[] rangeAffectedByOpperator(int position, boolean forwardsOrBackWards, int level) {
		if (level == 3) {
			int toClose = 0;
			if (forwardsOrBackWards == false) {
				for (int i = position - 1; i >= 0; i--) {
					if (superFunction.baseFunction.charAt(i) == ')') {
						toClose++;
					} else {
						if (superFunction.baseFunction.charAt(i) == '(') {
							toClose--;

						}
						if (toClose == 0 && (superFunction.general.isOpperator(superFunction.baseFunction.charAt(i))
								|| i == 0)) {
							return new int[] { i, position };
						}
					}

				}
			} else {
				for (int i = position + 1; i < superFunction.baseFunction.length(); i++) {
					if (superFunction.baseFunction.charAt(i) == '(') {
						toClose++;
					} else {
						if (superFunction.baseFunction.charAt(i) == ')') {
							toClose--;
						}
						if (toClose == 0)
							
							if(superFunction.general.isOpperator(superFunction.baseFunction.charAt(i))) {
								int offset = 1;
								if(superFunction.baseFunction.charAt(i) != ')') {
									offset = 0;
								}
								return new int[] { position + 1, i + offset};
							} else if(i == superFunction.baseFunction.length() - 1) {
								return new int[] { position + 1, i + 1};
							}
								
						}
					}

				}
			}
		return new int[]{0,0};

	}

	public ArrayList<ArrayList<String>> splitIntoLargestParts() {
		int levelsIn = 0;
		int firstTouched = 0;
		ArrayList<String> parts = new ArrayList<String>();
		ArrayList<String> opperators = new ArrayList<String>();
		ArrayList<ArrayList<String>> toReturn = new ArrayList<ArrayList<String>>();
		if (!(simplify.newFunction.contains("+") || simplify.newFunction.contains("-"))) {
			parts.add(simplify.newFunction);
			toReturn.add(parts);
			toReturn.add(opperators);
			return toReturn;
		}
		for (int i = 0; i < simplify.newFunction.length(); i++) {
			if ((simplify.newFunction.charAt(i) == '+' || simplify.newFunction.charAt(i) == '-') && levelsIn == 0) {
				parts.add(simplify.newFunction.substring(firstTouched, i));
				opperators.add(Character.toString(simplify.newFunction.charAt(i)));
				firstTouched = i + 1;
			} else if (simplify.newFunction.charAt(i) == '(') {
				levelsIn++;
			} else if (simplify.newFunction.charAt(i) == ')') {
				levelsIn--;
			}
			if (i == simplify.newFunction.length() - 1 && simplify.newFunction.charAt(i) != '+') {
				parts.add(simplify.newFunction.substring(firstTouched, i + 1));
			}
		}
		toReturn.add(parts);
		toReturn.add(opperators);
		return toReturn;
	}
	
}
