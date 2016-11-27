package io.thaumavor.rbradford.JavaCAS.Library.FunctionTools;

import java.util.ArrayList;
import java.util.Arrays;

import io.thaumavor.rbradford.JavaCAS.Library.Function;

public class Value {

	Function baseFunction;

	public Value(Function baseFunction) {
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
		ArrayList<String> parts = new ArrayList<String>(Arrays.asList(function.split("(\\+)|(\\-)")));
		return parts;
	}

	public double getValueAt(double at) {
		String newFunction = this.simplifyNoGroups(baseFunction.baseFunction, at);
		ArrayList<String> firstSplit = this.splitAtAddSub(newFunction);
		ArrayList<Double> values = doMultiDiv(firstSplit, at);
		if (values.contains(Double.POSITIVE_INFINITY)) {
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
			if (x == '-' && i > 0 && !baseFunction.general.isOpperator(function.charAt(i - 1))) {
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
				tempValues.add(baseFunction.general.getValueOfPart(z, at));
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

}
