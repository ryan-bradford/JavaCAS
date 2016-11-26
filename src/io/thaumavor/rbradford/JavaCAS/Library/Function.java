package io.thaumavor.rbradford.JavaCAS.Library;

import java.util.ArrayList;
import java.util.Arrays;

public class Function {

	FunctionChangers change;
	FunctionValue value;
	public String baseFunction;
	
	public Function(String function) {
		this.baseFunction = function;
		change = new FunctionChangers(this);
		value = new FunctionValue(this);
	}

	public boolean isOpperator(char stuff) {
		return value.isOpperator(stuff);
	}

	public String simplifyNoGroups(String function, double at) {
		return value.simplifyNoGroups(function, at);
	}

	public ArrayList<String> splitAtMultiDiv(String function) {
		return value.splitAtMultiDiv(function);
	}

	public ArrayList<String> splitAtAddSub(String function) {
		return value.splitAtAddSub(function);
	}

	public double getValueAt(double at) {
		return value.getValueAt(at);
	}

	public double doAddSub(String function, ArrayList<Double> values, double at) {
		return value.doAddSub(function, values, at);
	}

	public ArrayList<Double> doMultiDiv(ArrayList<String> firstSplit, double at) {
		return value.doMultiDiv(firstSplit, at);
	}

	public double getValueOfPart(String z, double at) {
		return value.getValueOfPart(z, at);
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
			if(deriv < .00001 && deriv > -.00001) {
				deriv = 0;
			}
			boolean currentDec = deriv < 0;
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

	public void addFuncAtLevel(String toAdd, int level) {
		change.addFuncAtLevel(toAdd, level);
	}

	public void multiplyFuncAtLevel(String toMultiply, int level) {
		change.multiplyFuncAtLevel(toMultiply, level);
	}

	public void subtractFuncAtLevel(String toSubtract, int level) {
		change.subtractFuncAtLevel(toSubtract, level);
	}
	
	public void insertFunctionAtRandomPoint(String function) {
		change.insertFunctionAtRandomPoint(function);
	}
	
	public int getLevels() {
		return change.getLevels();
	}
	
	public ArrayList<Integer> getLevelBreakdown(int level) {
		return change.getLevelBreakdown(level);
	}
	
	public void simplify() {
		ArrayList<String> parts = this.splitAtAddSub(baseFunction);
		for (String x: parts) {
			
		}
	}

}
