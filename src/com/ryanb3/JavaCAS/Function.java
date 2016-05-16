package com.ryanb3.JavaCAS;

import java.util.ArrayList;
import java.util.Arrays;

public class Function {

	String function;

	public Function(String base) {
		function = base;
	}

	public double integralOfFunc(double start, double end, double interval) {
		double total = 0;
		for (double i = start; i < end; i += interval) {
			total += this.getValueAt(i) * interval;
		}
		return total;
	}

	public double biggestDerivOfFunc(double start, double end, double interval) {
		double biggest = 0;
		Double first = null;
		Double second = null;
		for (double i = start; i < end; i += interval) {
			if (first == null) {
				first = getValueAt(i);
			}
			second = getValueAt(i + interval);
			double toCheck = (second - first) / interval;
			if (toCheck > biggest) {
				biggest = toCheck;
			}
			first = second;
			second = null;
		}
		return biggest;
	}

	public double derivOfFunc(double pos, double interval) {
		return (this.getValueAt(pos + interval / 2) - this.getValueAt(pos - interval / 2)) / interval;
	}

	public boolean insertFunc(String toInsert) { // 1 + x + e^2^(x)
		if (!function.contains("x")) {
			return false;
		}
		ArrayList<String> parts = this.functionParts(function);
		int placeToInsert = (int) (Math.random() * parts.size());
		String chosen = parts.get(placeToInsert);
		String[] halves = chosen.split("x");
		String newPart = "";
		if (halves.length == 1) {
			return insertFunc(toInsert);
		} else if (halves.length == 0) {
			newPart = toInsert;
		} else {
			newPart = halves[0] + toInsert + halves[1];
		}
		int start = 0;
		int counter = 0;
		for (int i = 0; i < function.length(); i++) {
			if (counter == placeToInsert) {
				start = i;
				break;
			}
			if (function.charAt(i) == '+' || function.charAt(i) == '-' || function.charAt(i) == '*') {
				counter++;
			}
		}
		while (function.charAt(start) == '[' || function.charAt(start) == ']' || function.charAt(start) == '+'
				|| function.charAt(start) == '-' || function.charAt(start) == '*') {
			start++;
		}

		int end = start + chosen.length() - 1;
		function = function.substring(0, start) + newPart + function.substring(end + 1, function.length());
		return true;
	}

	public void addFunc(String toAdd) {
		function += "+";
		function += toAdd;
	}

	public void multiplyFunc(String toMultiply) {
		function += "*";
		function += toMultiply;
	}

	public void subtractFunc(String toSubtract) {
		function += "-";
		function += toSubtract;
	}

	public double getValueAt(double position) {
		String workingFunction = function.toString();
		ArrayList<String> parts = functionParts(workingFunction);
		ArrayList<Double> values = convertFuncToValues(parts, position);
		values = doGrouping(position, workingFunction, values);
		workingFunction = removeGrouping(workingFunction);
		values = doMultiplication(workingFunction, values);
		return doAdditionOrSubtraction(workingFunction, values);
	}

	public double getValueAt(double position, String function, boolean toGroup) {
		String workingFunction = function.toString();
		ArrayList<String> parts = functionParts(workingFunction);
		ArrayList<Double> values = convertFuncToValues(parts, position);
		if (toGroup) {
			values = doGrouping(position, workingFunction, values);
			workingFunction = removeGrouping(workingFunction);
		}
		values = doMultiplication(workingFunction, values);
		return doAdditionOrSubtraction(workingFunction, values);
	}

	public double doAdditionOrSubtraction(String function, ArrayList<Double> currentVals) {
		double total = 0;
		total = currentVals.get(0);
		int counter = 0;
		for (int i = 0; i < function.length(); i++) {
			if (function.charAt(i) == '+') {
				total += currentVals.get(counter + 1);
				counter++;
			} else if (function.charAt(i) == '-') {
				total -= currentVals.get(counter + 1);
				counter++;
			}
		}
		return total;
	}

	public ArrayList<Double> doMultiplication(String function, ArrayList<Double> currentVals) {
		int numOfMultiply = 0;
		for (int i = 0; i < function.length(); i++) {
			if (function.charAt(i) == '*') {
				numOfMultiply++;
			}
		}
		for (int x = 0; x < numOfMultiply; x++) {
			int counter = 0;
			for (int i = 1; i < function.length(); i++) {
				if (function.charAt(i) == '*') {
					currentVals.set(counter, currentVals.get(counter) * currentVals.get(counter + 1));
					currentVals.remove(counter + 1);
					counter++;
					break;
				} else if (function.charAt(i) == '+') {
					counter++;
				}
				if (function.charAt(i) == '-') {
					counter++;
				}
			}
		}
		return currentVals;
	}

	public String removeGrouping(String function) {
		String[] parts = function.split("\\[|\\]");
		String newFunction = "";
		for (int i = 0; i < parts.length; i += 2) {
			newFunction += parts[i];
			if (i != parts.length - 1) {
				newFunction += "x";
			}
		}
		return newFunction;
	}

	public ArrayList<Double> doGrouping(double position, String function, ArrayList<Double> currentVals) {
		String[] parts = function.split("\\[|\\]");
		ArrayList<Integer> domain = new ArrayList<Integer>();
		domain.add(0);
		int counter = 0;
		for (int i = 0; i < function.length(); i++) {
			if (function.charAt(i) == '+') {
				counter++;
			}
			if (function.charAt(i) == '-') {
				counter++;
			}
			if (function.charAt(i) == '*') {
				counter++;
			}
			if (function.charAt(i) == '[' || function.charAt(i) == ']') {
				domain.add(counter);
			}
		}
		domain.add(function.length() - 1);
		if (parts.length == 1) {
			return currentVals;
		}
		ArrayList<Double> toReturn = new ArrayList<Double>();
		if (parts.length % 2 == 0) {
			for (int i = 1; i < parts.length; i += 2) {
				for (int x = domain.get(i - 1); x < domain.get(i); x++) {
					if (parts[i - 1].contains("x") || parts[i - 1].contains("1")) {
						toReturn.add(currentVals.get(x));
					}
				}
				toReturn.add(getValueAt(position, parts[i], true));
			}
		} else {
			for (int i = 0; i < parts.length; i += 2) {
				for (int x = domain.get(i); x < domain.get(i + 1); x++) {
					if (parts[i].contains("x") || parts[i].contains("1")) {
						toReturn.add(currentVals.get(i));
					}
				}
				if (i != parts.length - 1) {
					toReturn.add(getValueAt(position, parts[i + 1], true));
				}
			}
		}
		return toReturn;
	}

	public ArrayList<Double> convertFuncToValues(ArrayList<String> parts, double at) {
		ArrayList<Double> values = new ArrayList<Double>();
		for (String part : parts) {
			String[] inners = part.split("\\(");
			double val = 0;
			for (int i = inners.length - 1; i >= 0; i--) {
				String inner = inners[i];
				inner = inner.split("\\)")[0];
				if (inner.equals("1")) {
					val = 1;
				} else if (inner.equals("x")) {
					val = at;
				} else if (inner.equals("abs")) {
					val = Math.abs(val);
				} else if (inner.equals("2^")) {
					val = Math.pow(val, 2);
				} else if (inner.equals("e^")) {
					val = Math.pow(Math.E, val);
				} else if (inner.equals("sin")) {
					val = Math.sin(val);
				} else if (inner.equals("cos")) {
					val = Math.cos(val);
				} else if (inner.equals("arctan")) {
					val = Math.atan(val);
				} else if (inner.equals("1/")) {
					val = 1 / val;
				} else if (inner.equals("ln")) {
					val = Math.log(val);
				} else {
					// val = this.getValueAt(val, inner, true);
				}
			}
			values.add(val);
		}
		return values;

	}

	public ArrayList<String> functionParts(String function) {
		ArrayList<String> parts = new ArrayList<String>(Arrays.asList(function.split("-|\\+|\\*")));
		for (int i = 0; i < parts.size(); i++) {
			if (parts.get(i).equals("")) {
				parts.remove(i);
			}
		}
		return parts;
	}
}
