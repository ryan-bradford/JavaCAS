package io.thaumavor.rbradford.JavaCAS.MathOlympics.Worker;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import io.thaumavor.rbradford.JavaCAS.Library.Function;

public class Worker extends Thread {

	ArrayList<Function> storage;
	String[] functions = { "1", "x", "abs(x)", "(x)^2", "1/(x)", "cos(x)", "sin(x)", "arctan(x)", "e^(x)", "ln(x)",
			"c" };
	int[] costs = { 1, 7, 7, 12, 4, 14, 14, 3, 42, 4, 1 };
	double[] weight = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
	int cost;
	double start;
	double end;
	double interval;
	int id;
	ArrayList<String> answers;
	double minCount;
	double startTime = System.currentTimeMillis();
	ArrayList<Integer> count;
	int timesChosen = 0;
	double biggest = 0;
	String biggestFunc = "";
	ArrayList<Double> values;
	
	public Worker(int cost, int id, double start, double end, double interval, ArrayList<String> answers,
			double minCount, ArrayList<Integer> count, ArrayList<Double> values) {
		this.storage = new ArrayList<Function>();
		this.cost = cost;
		this.start = start;
		this.end = end;
		this.id = id;
		this.interval = interval;
		this.answers = answers;
		this.minCount = minCount;
		this.count = count;
		this.values = values;
	}

	public void run() {
		while ((System.currentTimeMillis() - startTime) / 60000 < minCount) {
			try {
				addRandFunc();
				Function x = storage.get(storage.size() - 1);
				doIntegral(x);
			} catch (Exception ex) {

			}
		}
		answers.add(biggestFunc);
		count.add(storage.size());
		values.add(biggest);
	}
	
	public void doDeriv(Function x) {
		double extremas = x.calculus.biggestDerivOfFunc(start, end, interval);
		if (extremas > biggest && !undef(x)) {
			biggestFunc = x.baseFunction;
			timesChosen++;
			if (timesChosen > 20) {
				changeWeight(biggestFunc);
				timesChosen = 0;
			}
			biggest = extremas;
		}
	}
	
	public void doExtremas(Function x) {
		double extremas = x.calculus.getNumberOfExtremas(start, end, interval);
		if (extremas > biggest && !undef(x)) {
			biggestFunc = x.baseFunction;
			timesChosen++;
			if (timesChosen > 20) {
				changeWeight(biggestFunc);
				timesChosen = 0;
			}
			biggest = extremas;
		}
	}
	
	public void doIntegral(Function x) {
		double integral = x.calculus.integralOfFunc(start, end, interval);
		if (integral > biggest && !undef(x)) {
			biggestFunc = x.baseFunction;
			timesChosen++;
			if (timesChosen > 20) {
				changeWeight(biggestFunc);
				timesChosen = 0;
			}
			biggest = integral;
		}
	}

	public void addRandFunc() {
		boolean added = false;
		while (!added && (System.currentTimeMillis() - startTime) / 60000 < minCount) {
			ArrayList<Integer> next = new ArrayList<Integer>();
			for (int x = 0; x < 10 * Math.random(); x++) {
				next.add(getRandomNum());
			}
			double price = this.checkPrice(next);
			if (price <= cost) {
				ArrayList<String> toProcess = new ArrayList<String>();
				for (int x : next) {
					toProcess.add(functions[x]);
				}
				if (price < cost) {
					if (Math.random() > .5) {
						toProcess.add(optimizeConstants(cost - price));
					}
				}
				Function toAdd = randomFunction(toProcess);
				if (!storage.contains(toAdd)) {
					storage.add(toAdd);
					added = true;
				}
			}
		}
	}

	public String optimizeConstants(double money) {
		double total = 0;
		double evenSquare = Math.floor(Math.sqrt(money));
		double subtract = money - (evenSquare * evenSquare);
		if (subtract == 0) {
			total = Math.pow(evenSquare, evenSquare);
			return Double.toString(total);
		} else if (subtract == 1) {
			total = Math.pow(evenSquare, evenSquare - 1);
			total *= (evenSquare + 1);
			return Double.toString(total);
		} else {
			total = Math.pow(evenSquare, evenSquare);
			total *= subtract;
			return Double.toString(total);
		}
	}

	public int getRandomNum() {
		boolean chosen = false;
		int toReturn = 0;
		double total = 0;
		for (int x = 0; x < weight.length; x++) {
			total += weight[x];
		}
		while (!chosen) {
			for (int x = 0; x < weight.length; x++) {
				if (weight[x] / total > Math.random()) {
					chosen = true;
					toReturn = x;
					break;
				}
			}
		}
		return toReturn;
	}

	public Function getMostExtremas(ArrayList<Function> toUse, double start, double end, double interval) {
		double biggestVal = 0;
		int count = 0;
		Function biggestFunc = null;
		for (Function x : toUse) {
			count++;
			double percent = (int) (10000 * 1 / (count / toUse.size())) / 10000;
			if (percent % 20 == 0) {
				JOptionPane.showMessageDialog(null, "%" + percent + " Done");
			}
			double current = x.calculus.getNumberOfExtremas(start, end, interval);
			if (current >= biggestVal && !undef(x)) {
				biggestVal = current;
				biggestFunc = x;
			}
		}
		return biggestFunc;
	}

	public Function getBiggestInt(ArrayList<Function> toUse, double start, double end, double interval) {
		double biggestVal = 0;
		Function biggestFunc = null;
		for (Function x : toUse) {
			double current = x.calculus.integralOfFunc(start, end, interval);
			if (current >= biggestVal && !undef(x)) {
				biggestVal = current;
				biggestFunc = x;
			}
		}
		return biggestFunc;
	}

	public boolean undef(Function toCheck) {
		if (toCheck.getValueAt(Math.PI) == Double.POSITIVE_INFINITY || toCheck.getValueAt(1) == Double.POSITIVE_INFINITY
				|| toCheck.getValueAt(Math.PI / 2) == Double.POSITIVE_INFINITY
				|| toCheck.getValueAt(Math.tan(1)) == Double.POSITIVE_INFINITY
				|| toCheck.getValueAt(0) == Double.POSITIVE_INFINITY) {

			return true;
		}
		return false;
	}

	public Function randomFunction(ArrayList<String> toUse) {
		ArrayList<String> functionVar = (ArrayList<String>) toUse.clone();
		int start = (int) (Math.random() * functionVar.size());
		double random = Math.random();
		Function toReturn = new Function(functionVar.get(start));
		functionVar.remove(start);
		for (String x : functionVar) {
			if (random < .25) {
				toReturn.change.insertFunctionAtRandomPoint(x);
			} else {
				int levelToInsertAt = (int) (toReturn.change.getLevels() * Math.random());
				if (random < .5) {
					toReturn.change.addFuncAtLevel(x, levelToInsertAt);
				} else if (random < .75) {
					toReturn.change.multiplyFuncAtLevel(x, levelToInsertAt);
				} else {
					toReturn.change.subtractFuncAtLevel(x, levelToInsertAt);
				}
			}
		}
		return toReturn;
	}

	public double checkPrice(ArrayList<Integer> toCheck) {
		int totalCost = 0;
		for (Integer x : toCheck) {
			totalCost += costs[x];
		}
		return totalCost;
	}

	public void changeWeight(String func) {
		if (func.contains("abs")) {
			if (weight[2] < 5) {
				weight[2]++;
			}
		} else {
			if (weight[2] > 1) {
				weight[2]--;
			}
		}
		if (func.contains("^2")) {
			if (weight[3] < 5) {
				weight[3]++;
			}
		} else {
			if (weight[3] > 1) {
				weight[3]--;
			}
		}
		if (func.contains("\\/")) {
			if (weight[4] < 5) {
				weight[4]++;
			}
		} else {
			if (weight[4] > 1) {
				weight[4]--;
			}
		}
		if (func.contains("cos")) {
			if (weight[5] < 5) {
				weight[5]++;
			}
		} else {
			if (weight[5] > 1) {
				weight[5]--;
			}
		}
		if (func.contains("sin")) {
			if (weight[6] < 5) {
				weight[6]++;
			}
		} else {
			if (weight[6] > 1) {
				weight[6]--;
			}
		}
		if (func.contains("arctan")) {
			if (weight[7] < 5) {
				weight[7]++;
			}
		} else {
			if (weight[7] > 1) {
				weight[7]--;
			}
		}
		if (func.contains("e^")) {
			if (weight[8] < 5) {
				weight[8]++;
			}
		} else {
			if (weight[8] > 1) {
				weight[8]--;
			}
		}
		if (func.contains("ln")) {
			if (weight[9] < 5) {
				weight[9]++;
			}
		} else {
			if (weight[9] > 1) {
				weight[9]--;
			}
		}
		if (func.contains("+1") || func.contains("/1") || func.contains("-1") || func.contains("*1")) {
			if (weight[0] < 5) {
				weight[0]++;
			}
		} else {
			if (weight[0] > 1) {
				weight[0]--;
			}
		}
		if (func.contains("+x") || func.contains("*x") || func.contains("/x") || func.contains("-1")) {
			if (weight[1] < 5) {
				weight[1]++;
			}
		} else {
			if (weight[1] > 1) {
				weight[1]--;
			}
		}
	}
}
