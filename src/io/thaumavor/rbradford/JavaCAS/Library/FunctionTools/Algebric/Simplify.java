package io.thaumavor.rbradford.JavaCAS.Library.FunctionTools.Algebric;

import java.util.ArrayList;
import java.util.Arrays;

import io.thaumavor.rbradford.JavaCAS.Library.Function;

public class Simplify {

	Function superFunction;
	
	public Simplify(Function baseFunction) {
		this.superFunction = baseFunction;
	}
	
	public void simplify() {
		String newFunction = "";
		ArrayList<String> parts = superFunction.value.splitAtAddSub(superFunction.baseFunction);
		for (int i = 0; i < parts.size(); i++) {
			String[] div = parts.get(i).split("\\/");
			if(div.length == 2 && div[0].trim().equals(div[1].trim())) {
				newFunction += "1";
				newFunction = this.addCorrectOpperator(superFunction.baseFunction, newFunction, i, new ArrayList<String>(Arrays.asList(new String[]{"+", "-"})));
			} else {
				newFunction += parts.get(i).trim();
				newFunction = this.addCorrectOpperator(superFunction.baseFunction, newFunction, i, new ArrayList<String>(Arrays.asList(new String[]{"+", "-"})));
			}
		}
		System.out.println("Simplified Function: " + newFunction);
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
