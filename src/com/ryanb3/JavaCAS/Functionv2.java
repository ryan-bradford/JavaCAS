package com.ryanb3.JavaCAS;

import java.util.ArrayList;
import java.util.Arrays;

public class Functionv2 {

	String baseFunction;

	public Functionv2(String function) {
		this.baseFunction = function;
	}

	public boolean isOpperator(char stuff) {
		if (stuff == '+' || stuff == '-' || stuff == '/' || stuff == '*' || stuff == '^' || stuff == '(' || stuff == ')'|| stuff == 'n'|| stuff == 's'|| stuff == 'E') {
			return true;
		}
		return false;
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
				newFunction += Double.toString(new Functionv2(function.substring(start + 1, end)).getValueAt(at));
				finished = false;
			}
		}
		newFunction += function.substring(end + 1, function.length());
		return newFunction;
	}

	public ArrayList<String> splitAtMultiDiv(String function) {
		String[] split = function.split("\\*|\\/");
		return new ArrayList<String>(Arrays.asList(split));
	}

	public ArrayList<String> splitAtAddSub(String function) {
		ArrayList<String> split = new ArrayList<String>();
		int last = 0;
		for(int i = 0; i < function.length(); i++) {
			if(function.charAt(i) == '+') {
				split.add(function.substring(last, i));
				last = i;
			} else if(function.charAt(i) == '-') {
				if(i > 0 && !isOpperator(function.charAt(i - 1))) {
					split.add(function.substring(last, i));
					last = i;
				}
			}
		}
		if(split.size() == 0) {
			split.add(function);
		}
		return split;
	}

	public double getValueAt(double at) {
		ArrayList<Double> values = new ArrayList<Double>();
		String newFunction = this.simplifyNoGroups(baseFunction, at);
		ArrayList<String> firstSplit = this.splitAtAddSub(newFunction);
		values = doMultiDiv(firstSplit, values, at);
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

	public ArrayList<Double> doMultiDiv(ArrayList<String> firstSplit, ArrayList<Double> values, double at) {
		for (String y : firstSplit) {
			ArrayList<String> secondSplit = this.splitAtMultiDiv(y);
			for (String z : secondSplit) {
				values.add(this.getValueOfPart(z, at));
			}
			int count = 0;
			for (char stuff : y.toCharArray()) {
				if (stuff == '*') {
					values.set(count + 1, values.get(count) * values.get(count + 1));
					values.remove(count);
				}
				if (stuff == '/') {
					values.set(count + 1, values.get(count) / values.get(count + 1));
					values.remove(count);
				}
			}
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
		} else if (z.contains("^")) {
			String[] halves = z.split("\\^");
			toReturn = (Math.pow(getValueOfPart(halves[0], at), getValueOfPart(halves[1], at)));
		} else if (z.contains("sin")) {
			String[] halves = z.split("n");
			toReturn = (Math.sin(getValueOfPart(halves[1], at)));
		} else if (z.contains("cos")) {
			String[] halves = z.split("s");
			toReturn = (Math.cos(getValueOfPart(halves[1], at)));
		} else if (z.contains("tan")) {
			String[] halves = z.split("n");
			toReturn = (Math.sin(getValueOfPart(halves[1], at)));
		} else if (z.contains("atan")) {
			String[] halves = z.split("n");
			toReturn = (Math.atan(getValueOfPart(halves[1], at)));
		} else if (z.contains("asin")) {
			String[] halves = z.split("n");
			toReturn = (Math.asin(getValueOfPart(halves[1], at)));
		} else if (z.contains("acos")) {
			String[] halves = z.split("s");
			toReturn = (Math.acos(getValueOfPart(halves[1], at)));
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

	public double integralOfFunc(double start, double end, double interval) {
		double total = 0;
		for (double i = start; i < end; i += interval) {
			total += this.getValueAt(i) * interval;
		}
		return total;
	}

	public double getNumberOfExtremas(double start, double end, double interval) {
		int total = 0;
		Boolean lastDecreased = null;
		for (double i = start; i < end; i += interval) {
			double deriv = derivOfFunc(i, interval);
			boolean currentDec = deriv <= 0;
			if (lastDecreased != null && currentDec != lastDecreased) {
				total++;
			}
			lastDecreased = currentDec;
		}
		return total;
	}

	public double derivOfFunc(double pos, double interval) {
		return (this.getValueAt(pos + interval / 2) - this.getValueAt(pos - interval / 2)) / interval;
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

	public void addFunc(String toAdd) {
		baseFunction += "+";
		baseFunction += toAdd;
	}

	public void multiplyFunc(String toMultiply) {
		baseFunction += "*";
		baseFunction += toMultiply;
	}

	public void subtractFunc(String toSubtract) {
		baseFunction += "-";
		baseFunction += toSubtract;
	}

}
