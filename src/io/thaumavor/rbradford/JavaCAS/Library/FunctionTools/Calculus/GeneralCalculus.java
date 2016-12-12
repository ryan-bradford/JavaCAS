package io.thaumavor.rbradford.JavaCAS.Library.FunctionTools.Calculus;

import io.thaumavor.rbradford.JavaCAS.Library.Function;

public class GeneralCalculus {

	Function superFunction;
	
	public GeneralCalculus(Function baseFunction) {
		this.superFunction = baseFunction;
	}
	
	public double integralOfFunc(double start, double end, double interval) {
		double total = 0;
		for (double i = start; i < end; i += interval) {
			total += superFunction.getValueAt(i) * interval;
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
		return (superFunction.getValueAt(pos + interval / 2) - superFunction.getValueAt(pos - interval / 2)) / interval;
	}

	public double biggestDerivOfFunc(double start, double end, double interval) {
		double biggest = 0;
		Double first = null;
		Double second = null;
		for (double i = start; i < end; i += interval) {
			if (first == null) {
				first = superFunction.getValueAt(i);
			}
			second = superFunction.getValueAt(i + interval);
			double toCheck = (second - first) / interval;
			if (toCheck > biggest) {
				biggest = toCheck;
			}
			first = second;
			second = null;
		}
		return biggest;
	}
	
}
