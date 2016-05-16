package com.ryanb3.JavaCAS;

public class main {

	String[] functions = { "(1)", "(x)", "abs(x)", "2^(x)", "1/(x)", "cos(x)", "sin(x)", "arctan(x)", "e^(x)",
			"ln(x)" };
	int[] cost = { 1, 7, 7, 12, 4, 14, 14, 3, 42, 4 };

	public static void main(String[] args) {
		new main();
	}

	// Many Grouping
	// Opperations within Compositon

	public main() {
		//Function test = new Function("2^(x)*ln(x)*ln(x)*ln(x)*ln(x)");
		//System.out.println(test.getValueAt(5));
		String best = biggestDeriv(1, 5, .1, 48);
		System.out.println(best);
		System.out.println(new Function(best).biggestDerivOfFunc(1, 5, .1));
	}

	public String biggestDeriv(double start, double end, double interval, int moneyToSpend) {
		double bestIntegral = 0;
		String function = "";
		for (int i = 0; i < 100000; i++) {
			Function current = genRandomFunction(moneyToSpend);
			double integral = current.biggestDerivOfFunc(start, end, interval);
			if (integral > bestIntegral && !Double.isInfinite(integral)) {
				bestIntegral = integral;
				function = current.function;
			}
		}
		return function;
	}
	
	public String getBestIntegral(double start, double end, double interval, int moneyToSpend) {
		double bestIntegral = 0;
		String function = "";
		for (int i = 0; i < 10000; i++) {
			Function current = genRandomFunction(moneyToSpend);
			double integral = current.integralOfFunc(start, end, interval);
			if (integral > bestIntegral && !Double.isInfinite(integral)) {
				bestIntegral = integral;
				function = current.function;
			}
		}
		return function;
	}

	public Function genRandomFunction(int totalMoney) {
		Function toReturn = new Function("");
		while (totalMoney > 0) {
			int toAdd = getRandomPart(totalMoney);
			totalMoney -= cost[toAdd];
			if(toReturn.function.equals("")) {
				toReturn = new Function(functions[toAdd]);
			} else {
				toReturn = addFuncDiff(toReturn, functions[toAdd]);
			}
		}
		return toReturn;
	}

	public Function addFunc(Function base, String toAdd) {
		double percentage = Math.random();
		if(base.function.equals("")) {
			base = new Function(toAdd);
		} else if (percentage < 1.0 / 4.0) {
			base.addFunc(toAdd);
		} else if (percentage > 1.0 / 4.0 && percentage < 2.0 / 4.0) {
			base.subtractFunc(toAdd);
		} else if (percentage > 2.0 / 4.0 && percentage < 3.0 / 4.0) {
			boolean result;
			result = base.insertFunc(toAdd);
			if (result == false) {
				return addFunc(base, toAdd);
			}
		} else {
			base.multiplyFunc(toAdd);
		}
		return base;
	}

	public Function addFuncDiff(Function base, String toAdd) {
		double percentage = Math.random();
		if (percentage > 1.0 / 2.0) {
			boolean result;
			result = base.insertFunc(toAdd);
			if (result == false) {
				return addFunc(base, toAdd);
			}
		} else {
			base.multiplyFunc(toAdd);
		}
		return base;
	}

	public int getRandomPart(int moneyToSpend) {
		int toPick = (int) (Math.random() * functions.length);
		if (cost[toPick] <= moneyToSpend) {
			return toPick;
		}
		return getRandomPart(moneyToSpend);
	}
}
