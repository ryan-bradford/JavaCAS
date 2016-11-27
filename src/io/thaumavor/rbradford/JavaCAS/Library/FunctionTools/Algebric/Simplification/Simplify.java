package io.thaumavor.rbradford.JavaCAS.Library.FunctionTools.Algebric.Simplification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import io.thaumavor.rbradford.JavaCAS.Library.Function;

public class Simplify {

	Function superFunction;
	String newFunction;
	Expansion expand;

	public Simplify(Function baseFunction) {
		this.superFunction = baseFunction;
		newFunction = "";
		expand = new Expansion(this);
	}
	
	public void simplify() {
		expand.expand();
		System.out.println("Simplified Function: " + this.newFunction);
	}

	public ArrayList<ArrayList<String>> splitIntoLargestParts(ArrayList<String> delims) {
		int levelsIn = 0;
		int firstTouched = 0;
		ArrayList<String> parts = new ArrayList<String>();
		ArrayList<String> opperators = new ArrayList<String>();
		ArrayList<ArrayList<String>> toReturn = new ArrayList<ArrayList<String>>();
		if (!(newFunction.contains(delims.get(0)) || newFunction.contains(delims.get(1)))) {
			parts.add(newFunction);
			toReturn.add(parts);
			toReturn.add(opperators);			
			return toReturn;
		}
		for (int i = 0; i < newFunction.length(); i++) {
			if ((delims.contains(Character.toString(newFunction.charAt(i)))) && levelsIn == 0) {
				parts.add(newFunction.substring(firstTouched, i));
				opperators.add(Character.toString(newFunction.charAt(i)));
				firstTouched = i + 1;
			} else if (newFunction.charAt(i) == '(') {
				levelsIn++;
			} else if (newFunction.charAt(i) == ')') {
				levelsIn--;
			}
			if (i == newFunction.length() - 1 && !delims.contains(Character.toString(newFunction.charAt(i)))) {
				parts.add(newFunction.substring(firstTouched, i + 1));
			}
		}
		toReturn.add(parts);
		toReturn.add(opperators);
		return toReturn;
	}

}
