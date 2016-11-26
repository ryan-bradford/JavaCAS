package com.ryanb3.JavaCAS.Library;

import java.util.ArrayList;

public class FunctionChangers {

	Function baseFunction;
	
	public FunctionChangers(Function baseFunction) {
		this.baseFunction = baseFunction;
	}
	
	public void addFuncAtLevel(String toAdd, int level) {
		ArrayList<Integer> breakdown = getLevelBreakdown(level);
		double number = breakdown.size();
		double random = Math.random();
		for(int i = 0; i < number; i++) {
			if(random < (i + 1) / number) {
				String firstHalf = baseFunction.baseFunction.substring(0, breakdown.get(i));
				String secondHalf = baseFunction.baseFunction.substring(breakdown.get(i), baseFunction.baseFunction.length());
				baseFunction.baseFunction = firstHalf + "+" + toAdd + secondHalf;
				break;
			}
		}
	}

	public void multiplyFuncAtLevel(String toMultiply, int level) {
		ArrayList<Integer> breakdown = getLevelBreakdown(level);
		double number = breakdown.size();
		double random = Math.random();
		for(int i = 0; i < number; i++) {
			if(random < (i + 1) / number) {
				String firstHalf = baseFunction.baseFunction.substring(0, breakdown.get(i));
				String secondHalf = baseFunction.baseFunction.substring(breakdown.get(i), baseFunction.baseFunction.length());
				baseFunction.baseFunction = firstHalf + "*" + toMultiply + secondHalf;
				break;
			}
		}
	}

	public void subtractFuncAtLevel(String toSubtract, int level) {
		ArrayList<Integer> breakdown = getLevelBreakdown(level);
		double number = breakdown.size();
		double random = Math.random();
		for(int i = 0; i < number; i++) {
			if(random < (i + 1) / number) {
				String firstHalf = baseFunction.baseFunction.substring(0, breakdown.get(i));
				String secondHalf = baseFunction.baseFunction.substring(breakdown.get(i), baseFunction.baseFunction.length());
				baseFunction.baseFunction = firstHalf + "-" + toSubtract + secondHalf;
				break;
			}
		}
	}
	
	public void insertFunctionAtRandomPoint(String function) {
		double totalX = 0;
		for(int i = 0; i < baseFunction.baseFunction.length(); i++) {
			if(baseFunction.baseFunction.charAt(i) == 'x') {
				totalX++;
			}
		}
		double probMod = 1 / totalX;
		boolean toBreak = false;
		for(int x = 1; x < totalX+1; x++) {
			for(int i = 0; i < baseFunction.baseFunction.length(); i++) {
				if(baseFunction.baseFunction.charAt(i) == 'x' && Math.random() <= probMod * x) {
					String firstHalf = baseFunction.baseFunction.substring(0, i);
					String secondHalf = baseFunction.baseFunction.substring(i+1, baseFunction.baseFunction.length());
					baseFunction.baseFunction = "";
					baseFunction.baseFunction += firstHalf;
					baseFunction.baseFunction += function;
					baseFunction.baseFunction += secondHalf;
					toBreak = true;
					break;
				}
			}
			if(toBreak) {
				break;
			}
		}
	}
	
	public int getLevels() {
		int biggestDepth = 1;
		int currentDepth = 1;
		for(char x: baseFunction.baseFunction.toCharArray()) {
			if(x == '(') {
				biggestDepth++;
			} else if(x == ')') {
				if(currentDepth > biggestDepth) {
					biggestDepth = currentDepth;
				}
				currentDepth = 1;
			}
		}
		return biggestDepth;
	}
	
	public ArrayList<Integer> getLevelBreakdown(int level) {
		ArrayList<Integer> toReturn = new ArrayList<Integer>();
		if(level == 1) {
			toReturn.add(baseFunction.baseFunction.length());
			return toReturn;
		}
		int currentDepth = 1;
		for(int i = 0; i < baseFunction.baseFunction.length(); i++) {
			char x = baseFunction.baseFunction.charAt(i);
			if(x == '(') {
				currentDepth++;
			} else if(x == ')') {
				if(currentDepth == level) {
					toReturn.add(i);
				}
				currentDepth--;
			}
		}
		return toReturn;
	}

}
