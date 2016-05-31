package com.ryanb3.JavaCAS;

import javax.swing.JOptionPane;

public class Test {
	
	double interval = .001;
	int runs = 100;

	String[] functions = { "1", "x", "abs(x)", "(x)^2", "1/(x)", "cos(x)", "sin(x)", "arctan(x)", "e^(x)",
			"ln(x)" };
	int[] cost = { 1, 7, 7, 12, 4, 14, 14, 3, 42, 4 };

	public static void main(String[] args) {
		new Test();
	}

	public Test() {
		//Functionv2 text = new Functionv2("1/(ln(arctan(x)))-x*cos(x)-1");
		//System.out.println(text.getValueAt(1));
		//Functionv2 test = new Functionv2(this.getBestIntegral(1, 5, interval, 33));
		//System.out.println(test.baseFunction);
		//System.out.println(test.integralOfFunc(1, 5, interval));
		manipulateFunctions();
	}
	
	public void manipulateFunctions() {
		String message = "Press 1 for # of extremas \n press 2 for function value at a point \n press 3 for the integral of a function \n press 4 to find the biggest derivative on an interval \n press 5 to find the derivative at a point.";
		String entered = JOptionPane.showInputDialog(message);
		if(entered.equals("1")) {
			extremasStart();
		} else if(entered.equals("2")) {
			functionStart();
		} else if(entered.equals("3")) {
			integralStart();
		} else if(entered.equals("4")) {
			biggestDerivativeStart();
		} else if(entered.equals("5")) {
			derivativeStart();			
		}
	}
	
	public void derivativeStart() {
		JOptionPane.showMessageDialog(null, "Use * for multiplication \n ^ for exponents");
		String entered = JOptionPane.showInputDialog("Enter a function that you want the derivative of:");
		Functionv2 text = new Functionv2(entered);
		Double start = Double.parseDouble(JOptionPane.showInputDialog("Where to get the derivative at?"));
		JOptionPane.showMessageDialog(null, "The derivative of f at " + start + " is: " + text.derivOfFunc(start, interval));
	}
	
	public void biggestDerivativeStart() {
		String entered = JOptionPane.showInputDialog("Enter a function that you want the biggest derivative of:");
		Functionv2 text = new Functionv2(entered);
		Double start = Double.parseDouble(JOptionPane.showInputDialog("What is the start point?"));
		Double end = Double.parseDouble(JOptionPane.showInputDialog("What is the end point?"));
		JOptionPane.showMessageDialog(null, "The biggest derivative between " + start + " and " + end + " is: " + text.biggestDerivOfFunc(start, end, interval));
	}
	
	public void integralStart() {
		String entered = JOptionPane.showInputDialog("Enter a function that you want the integral of:");
		Functionv2 text = new Functionv2(entered);
		Double start = Double.parseDouble(JOptionPane.showInputDialog("Where to get the integral from?"));
		Double end = Double.parseDouble(JOptionPane.showInputDialog("Where to get the integral to?"));
		JOptionPane.showMessageDialog(null, "The integral of f from " + start + " to  " + end + " is: " + text.integralOfFunc(start, end, interval));
	}
	
	public void functionStart() {
		String entered = JOptionPane.showInputDialog("Enter a function that you want the function of:");
		Functionv2 text = new Functionv2(entered);
		Double start = Double.parseDouble(JOptionPane.showInputDialog("Where to get the function at?"));
		JOptionPane.showMessageDialog(null, "The f of " + start + " is: " + text.getValueAt(start));
	}
	
	public void extremasStart() {
		String entered = JOptionPane.showInputDialog("Enter a function that you want the number of extremas:");
		Functionv2 text = new Functionv2(entered);
		Double start = Double.parseDouble(JOptionPane.showInputDialog("Start x"));
		Double end = Double.parseDouble(JOptionPane.showInputDialog("End x"));
		Double interval = .0001;
		JOptionPane.showMessageDialog(null, "The # from " + start + " to " + end + " is: " + text.getNumberOfExtremas(start, end, interval));
	}
	
	public String mostExtremas(double start, double end, double interval, int moneyToSpend) {
		double bestIntegral = 0;
		String function = "";
		for (int i = 0; i < runs; i++) {
			Functionv2 current = genRandomFunction(moneyToSpend, "cos(x)");
			double integral = current.getNumberOfExtremas(start, end, interval);
			if (integral > bestIntegral && !Double.isInfinite(integral)) {
				bestIntegral = integral;
				function = current.baseFunction;
			}
		}
		return function;
	}
	
	public String biggestDeriv(double start, double end, double interval, int moneyToSpend) {
		double bestIntegral = 0;
		String function = "";
		for (int i = 0; i < runs; i++) {
			Functionv2 current = genRandomFunction(moneyToSpend, "");
			double integral = current.biggestDerivOfFunc(start, end, interval);
			if (integral > bestIntegral && !Double.isInfinite(integral)) {
				bestIntegral = integral;
				function = current.baseFunction;
			}
		}
		return function;
	}
	
	public String getBestIntegral(double start, double end, double interval, int moneyToSpend) {
		double bestIntegral = 0;
		String function = "";
		for (int i = 0; i < runs; i++) {
			System.out.println(i);
			Functionv2 current = genRandomFunction(moneyToSpend, "");
			System.out.println(current.baseFunction);
			double integral = current.integralOfFunc(start, end, interval);
			if (integral > bestIntegral && !Double.isInfinite(integral)) {
				bestIntegral = integral;
				function = current.baseFunction;
			}
		}
		return function;
	}

	public Functionv2 genRandomFunction(int totalMoney, String base) {
		Functionv2 toReturn = new Functionv2(base);
		while (totalMoney > 0) {
			int toAdd = getRandomPart(totalMoney);
			totalMoney -= cost[toAdd];
			if(toReturn.baseFunction.equals("")) {
				toReturn = new Functionv2(functions[toAdd]);
			} else {
				toReturn = addFunc(toReturn, functions[toAdd]);
			}
		}
		return toReturn;
	}

	public Functionv2 addFunc(Functionv2 base, String toAdd) {
		double percentage = Math.random();
		if(base.baseFunction.equals("")) {
			base = new Functionv2(toAdd);
		} else if (percentage < 1.0 / 4.0) {
			base.addFunc(toAdd);
		} else if (percentage > 1.0 / 4.0 && percentage < 2.0 / 4.0) {
			base.subtractFunc(toAdd);
		} else if (percentage > 2.0 / 4.0 && percentage < 3.0 / 4.0) {
			base.insertFunctionAtRandomPoint(toAdd);
		} else {
			base.multiplyFunc(toAdd);
		}
		return base;
	}

	public Functionv2 addFuncDiff(Functionv2 base, String toAdd) {
		double percentage = Math.random();
		if (percentage > 0.0 / 2.0) {
			boolean result;
			base.insertFunctionAtRandomPoint(toAdd);
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
