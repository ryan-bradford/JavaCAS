package io.thaumavor.rbradford.JavaCAS.Library.FunctionTools.Algebric.Simplification;

import java.util.ArrayList;

import io.thaumavor.rbradford.JavaCAS.Library.Function;

public class Simplify {

	Function superFunction;
	String newFunction;
	Expansion expand;
	Cancelation cancel;

	public Simplify(Function baseFunction) {
		this.superFunction = baseFunction;
		newFunction = "";
		expand = new Expansion(this);
		cancel = new Cancelation(this);
	}
	
	public void simplify() {
		expand.expand();
		cancel.cancel();
		System.out.println("Simplified Function: " + this.newFunction);
	}

	public ArrayList<ArrayList<String>> splitIntoLargestParts(ArrayList<String> delims, String toUse) {
		int levelsIn = 0;
		int firstTouched = 0;
		ArrayList<String> parts = new ArrayList<String>();
		ArrayList<String> opperators = new ArrayList<String>();
		ArrayList<ArrayList<String>> toReturn = new ArrayList<ArrayList<String>>();
		if (!(toUse.contains(delims.get(0)) || toUse.contains(delims.get(1)))) {
			parts.add(toUse);
			toReturn.add(parts);
			toReturn.add(opperators);			
			return toReturn;
		}
		for (int i = 0; i < toUse.length(); i++) {
			if ((delims.contains(Character.toString(toUse.charAt(i)))) && levelsIn == 0) {
				parts.add(toUse.substring(firstTouched, i));
				opperators.add(Character.toString(toUse.charAt(i)));
				firstTouched = i + 1;
			} else if (toUse.charAt(i) == '(') {
				levelsIn++;
			} else if (toUse.charAt(i) == ')') {
				levelsIn--;
			}
			if (i == toUse.length() - 1 && !delims.contains(Character.toString(toUse.charAt(i)))) {
				parts.add(toUse.substring(firstTouched, i + 1));
			}
		}
		toReturn.add(parts);
		toReturn.add(opperators);
		return toReturn;
	}

}
