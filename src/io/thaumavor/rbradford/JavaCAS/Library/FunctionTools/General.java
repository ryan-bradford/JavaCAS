package io.thaumavor.rbradford.JavaCAS.Library.FunctionTools;

public class General {

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
			toReturn = Double.POSITIVE_INFINITY;
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
		if (stuff == '+' || stuff == '-' || stuff == '/' || stuff == '*' || stuff == '^' || stuff == '(' || stuff == ')'
				|| stuff == 'n' || stuff == 's' || stuff == 'E') {
			return true;
		}
		return false;
	}
	
}
