package com.ryanb3.JavaCAS.Library;

import java.util.ArrayList;
import java.util.Arrays;

public class FunctionValue {

	boolean broken = false;
	Function baseFunction;
	
	public FunctionValue(Function baseFunction) {
		this.baseFunction = baseFunction;
	}
	
	public String simplifyNoGroups(String function, double at) {
		String newFunction = "";
		int count = 0;
		int start = 0;
		int end = 0;
		int lastEnd = 0;
		boolean found = false;
		boolean finished = false;
		if (!function.contains("(")) {
			return function;
		}
		for (int i = 0; i < function.length(); i++) {
			if (function.charAt(i) == '(' && !found && count == 0) {
				count++;
				start = i;
				found = true;
			} else if (function.charAt(i) == '(') {
				count++;
			} else if (function.charAt(i) == ')') {
				count--;
			}
			if (found && count == 0) {
				lastEnd = end;
				end = i;
				found = false;
				finished = true;
			}
			if (finished) {
				String stringStart = function.substring(lastEnd, start);
				if (stringStart.contains(")")) {
					stringStart = stringStart.substring(1, stringStart.length());
				}
				newFunction += stringStart;
				newFunction += Double.toString(new Function(function.substring(start + 1, end)).getValueAt(at));
				finished = false;
			}
		}
		newFunction += function.substring(end + 1, function.length());
		return newFunction;
	}

	public ArrayList<String> splitAtMultiDiv(String function) {
		String[] split = function.split("(\\*)|(\\/)");
		return new ArrayList<String>(Arrays.asList(split));
	}

	public ArrayList<String> splitAtAddSub(String function) {
		ArrayList<String> parts =  new ArrayList<String>(Arrays.asList(function.split("(\\+)|(\\-)")));
		return parts;
	}

	public double getValueAt(double at) {
		broken = false;
		String newFunction = this.simplifyNoGroups(baseFunction.baseFunction, at);
		ArrayList<String> firstSplit = this.splitAtAddSub(newFunction);
		ArrayList<Double> values = doMultiDiv(firstSplit, at);
		if(broken) {
			return Double.POSITIVE_INFINITY;
		}
		return doAddSub(newFunction, values, at);
	}

	public double doAddSub(String function, ArrayList<Double> values, double at) {
		int count = 0;
		for (int i = 0; i < function.length(); i++) {
			char x = function.charAt(i);
			if (x == '+') {
				values.set(count + 1, values.get(count) + values.get(count + 1));
				values.remove(count);
			}
			if (x == '-' && i > 0 && !isOpperator(function.charAt(i-1))) {
				values.set(count + 1, values.get(count) - values.get(count + 1));
				values.remove(count);
			}
		}
		return values.get(0);
	}

	public ArrayList<Double> doMultiDiv(ArrayList<String> firstSplit, double at) {
		ArrayList<Double> values = new ArrayList<Double>();
		for (String y : firstSplit) {
			ArrayList<Double> tempValues = new ArrayList<Double>();
			ArrayList<String> secondSplit = this.splitAtMultiDiv(y);
			for (String z : secondSplit) {
				tempValues.add(this.getValueOfPart(z, at));
			}
			int count = 0;
			for (char stuff : y.toCharArray()) {
				if (stuff == '*') {
					tempValues.set(count + 1, tempValues.get(count) * tempValues.get(count + 1));
					tempValues.remove(count);
					count++;
				} else if (stuff == '/') {
					tempValues.set(count + 1, tempValues.get(count) / tempValues.get(count + 1));
					tempValues.remove(count);
					count++;
				}
			}
			values.addAll(tempValues);
		}
		return values;
	}

	public double getValueOfPart(String z, double at) {
		double toReturn = 0;
		boolean negative = false;
		if (z.contains("-") && z.charAt(0) == '-') {
			negative = true;
			z = z.substring(1, z.length());
		}
		if (z.equals("x")) {
			toReturn = at;
		} else if (z.contains("Infinity")) {
			broken = true;
			toReturn = 0;
		} else if (z.contains("^")) {
			String[] halves = z.split("\\^");
			toReturn = (Math.pow(getValueOfPart(halves[0], at), getValueOfPart(halves[1], at)));
		} else if (z.contains("arctan")) {
			String[] halves = z.split("n");
			toReturn = (Math.atan(getValueOfPart(halves[1], at)));
		} else if (z.contains("arcsin")) {
			String[] halves = z.split("n");
			toReturn = (Math.asin(getValueOfPart(halves[1], at)));
		} else if (z.contains("arccos")) {
			String[] halves = z.split("s");
			toReturn = (Math.acos(getValueOfPart(halves[1], at)));
		} else if (z.contains("sin")) {
			String[] halves = z.split("n");
			toReturn = (Math.sin(getValueOfPart(halves[1], at)));
		} else if (z.contains("cos")) {
			String[] halves = z.split("s");
			toReturn = (Math.cos(getValueOfPart(halves[1], at)));
		} else if (z.contains("tan")) {
			String[] halves = z.split("n");
			toReturn = (Math.sin(getValueOfPart(halves[1], at)));
		} else if (z.contains("ln")) {
			String[] halves = z.split("n");
			toReturn = (Math.log(getValueOfPart(halves[1], at)));
		} else if (z.contains("log")) {
			String[] halves = z.split("n");
			toReturn = (Math.log10(getValueOfPart(halves[1], at)));
		} else if (z.contains("abs")) {
			String[] halves = z.split("s");
			toReturn = (Math.abs(getValueOfPart(halves[1], at)));
		} else if (z.equals("PI")) {
			toReturn = (Math.PI);
		} else if (z.equals("e")) {
			toReturn = (Math.E);
		} else {
			toReturn = (Double.parseDouble(z));
		}
		
		if (negative) {
			return -toReturn;
		} else {
			return toReturn;
		}
	}
	
	public boolean isOpperator(char stuff) {
		if (stuff == '+' || stuff == '-' || stuff == '/' || stuff == '*' || stuff == '^' || stuff == '(' || stuff == ')'|| stuff == 'n'|| stuff == 's'|| stuff == 'E') {
			return true;
		}
		return false;
	}

	
}
