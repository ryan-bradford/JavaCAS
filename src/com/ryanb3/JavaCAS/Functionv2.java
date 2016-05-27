package com.ryanb3.JavaCAS;

import java.util.ArrayList;
import java.util.Arrays;

public class Functionv2 {

	String baseFunction;
	
	public Functionv2(String function) {
		this.baseFunction = function;
	}
	
	public String simplifyNoGroups(String function, double at) {
		String newFunction = "";
		int count = 0;
		int start = 0;
		int end = 0;
		int lastEnd = 0;
		boolean found = false;
		boolean finished = false;
		if(!function.contains("(")) {
			return function;
		}
		for(int i = 0; i < function.length(); i++) { 
			if(function.charAt(i) == '(' && !found && count == 0) {
				count++;
				start = i;
				found = true;
			} else if(function.charAt(i) == '(') {
				count++;
			} else if(function.charAt(i) == ')') {
				count--;
			}
			if(found && count == 0) {
				lastEnd = end;
				end = i;
				found = false;
				finished = true;
			}
			if(finished) {
				String stringStart = function.substring(lastEnd, start);
				if(stringStart.contains(")")) {
					stringStart = stringStart.substring(1, stringStart.length());
				}
				newFunction += stringStart;
				newFunction += Double.toString(new Functionv2(function.substring(start + 1, end)).getValueAt(at));
				finished = false;
			}
		}
		newFunction += function.substring(end+1, function.length());
		return newFunction;
	}

	public ArrayList<String> splitAtMultiDiv(String function) {
		String[] split = function.split("\\*|\\/");
		return new ArrayList<String>(Arrays.asList(split));
	}

	public ArrayList<String> splitAtAddSub(String function) {
		String[] split = function.split("\\+|\\-");
		return new ArrayList<String>(Arrays.asList(split));
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
		for(char x: function.toCharArray()) {
			if(x == '+') {
				values.set(count+1, values.get(count) + values.get(count+1));
				values.remove(count);
			}
			if(x == '-') {
				values.set(count+1, values.get(count) - values.get(count+1));
				values.remove(count);
			}
		}
		return values.get(0);
	}
	
	public ArrayList<Double> doMultiDiv(ArrayList<String> firstSplit, ArrayList<Double> values, double at) {
		for (String y : firstSplit) {
			ArrayList<String> secondSplit = this.splitAtMultiDiv(y);
			for (String z : secondSplit) {
				if (z.equals("x")) {
					values.add(at);
				} else if(z.equals("x^2")) {
					values.add(Math.pow(at, 2));
				} else if(z.contains("^")) {
					String[] halves = z.split("\\^");
					values.add(Math.pow(Double.parseDouble(halves[0]), Double.parseDouble(halves[1])));
				} else {
					values.add(Double.parseDouble(z));
				}
			}
			int count = 0;
			for(char stuff: y.toCharArray()) {
				if(stuff == '*') {
					values.set(count+1, values.get(count) * values.get(count+1));
					values.remove(count);
				}
				if(stuff == '/') {
					values.set(count+1, values.get(count) / values.get(count+1));
					values.remove(count);
				}
			}
		}
		return values;
	}

	public double getValueOfPart(double at, String function) {
		if (function == "x") {
			return at;
		} else {
			return 0;
		}
	}
	
	public double integralOfFunc(double start, double end, double interval) {
		double total = 0;
		for (double i = start; i < end; i += interval) {
			total += this.getValueAt(i) * interval;
		}
		return total;
	}

}
