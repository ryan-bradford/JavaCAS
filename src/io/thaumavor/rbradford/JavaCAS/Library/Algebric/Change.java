package io.thaumavor.rbradford.JavaCAS.Library.Algebric;

import java.util.ArrayList;



import io.thaumavor.rbradford.JavaCAS.Library.Function;

public class Change {

	Function baseFunction;
	
	public Change(Function baseFunction) {
		this.baseFunction = baseFunction;
	}
	
	public void addFuncAtLevel(String toAdd, int level) {
		ArrayList<Integer> breakdown = getLevelBreakdown(level);
		double number = breakdown.size();
		double random = Math.random();
		for(int i = 0; i < number; i++) {
			if(random < (i + 1) / number) {
				String firstHalf = baseFunction.getBaseFunction().substring(0, breakdown.get(i));
				String secondHalf = baseFunction.getBaseFunction().substring(breakdown.get(i), baseFunction.getBaseFunction().length());
				baseFunction.setBaseFunction(firstHalf + "+" + toAdd + secondHalf);
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
				String firstHalf = baseFunction.getBaseFunction().substring(0, breakdown.get(i));
				String secondHalf = baseFunction.getBaseFunction().substring(breakdown.get(i), baseFunction.getBaseFunction().length());
				baseFunction.setBaseFunction(firstHalf + "*" + toMultiply + secondHalf);
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
				String firstHalf = baseFunction.getBaseFunction().substring(0, breakdown.get(i));
				String secondHalf = baseFunction.getBaseFunction().substring(breakdown.get(i), baseFunction.getBaseFunction().length());
				baseFunction.setBaseFunction(firstHalf + "-" + toSubtract + secondHalf);
				break;
			}
		}
	}
	
	public void insertFunctionAtRandomPoint(String function) {
		double totalX = 0;
		for(int i = 0; i < baseFunction.getBaseFunction().length(); i++) {
			if(baseFunction.getBaseFunction().charAt(i) == 'x') {
				totalX++;
			}
		}
		double probMod = 1 / totalX;
		boolean toBreak = false;
		for(int x = 1; x < totalX+1; x++) {
			for(int i = 0; i < baseFunction.getBaseFunction().length(); i++) {
				if(baseFunction.getBaseFunction().charAt(i) == 'x' && Math.random() <= probMod * x) {
					String firstHalf = baseFunction.getBaseFunction().substring(0, i);
					String secondHalf = baseFunction.getBaseFunction().substring(i+1, baseFunction.getBaseFunction().length());
					String toSet = "";
					toSet += firstHalf;
					toSet += function;
					toSet += secondHalf;
					baseFunction.setBaseFunction(toSet);
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
		for(char x: baseFunction.getBaseFunction().toCharArray()) {
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
			toReturn.add(baseFunction.getBaseFunction().length());
			return toReturn;
		}
		int currentDepth = 1;
		for(int i = 0; i < baseFunction.getBaseFunction().length(); i++) {
			char x = baseFunction.getBaseFunction().charAt(i);
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
