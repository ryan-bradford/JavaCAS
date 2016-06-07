package com.ryanb3.JavaCAS.Test.Worker;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.ryanb3.JavaCAS.Library.Functionv2;

public class Worker extends Thread {

	ArrayList<Functionv2> storage;
	String[] functions = { "1", "x", "abs(x)", "(x)^2", "1/(x)", "cos(x)", "sin(x)", "arctan(x)", "e^(x)", "ln(x)" };
	int[] costs = { 1, 7, 7, 12, 4, 14, 14, 3, 42, 4 };
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

	public Worker(int cost, int id, double start, double end, double interval, ArrayList<String> answers,
			double minCount, ArrayList<Integer> count) {
		this.storage = new ArrayList<Functionv2>();
		this.cost = cost;
		this.start = start;
		this.end = end;
		this.id = id;
		this.interval = interval;
		this.answers = answers;
		this.minCount = minCount;
		this.count = count;
	}

	public void run() {
		double biggest = 0;
		String biggestFunc = "";
		while ((System.currentTimeMillis() - startTime) / 60000 < minCount) {
			addRandFunc();
			Functionv2 x = storage.get(storage.size() - 1);
			double integral = x.integralOfFunc(start, end, interval);
			if (integral > biggest&& !undef(x)) {
				biggestFunc = x.baseFunction;
				timesChosen++;
				if(timesChosen > 5) {
					changeWeight(biggestFunc);
					timesChosen = 0;
				}
				biggest = integral;
			}
		}
		answers.add(biggestFunc);
		count.add(storage.size());
	}

	public void changeWeight(String func) {
		if(func.contains("abs")) {
			weight[2]++;
		} else if(func.contains("^2")) {
			weight[3]++;
		} else if(func.contains("\\/")) {
			weight[4]++;
		} else if(func.contains("cos")) {
			weight[5]++;
		} else if(func.contains("sin")) {
			weight[6]++;
		} else if(func.contains("arctan")) {
			weight[7]++;
		} else if(func.contains("e^")) {
			weight[8]++;
		} else if(func.contains("ln")) {
			weight[9]++;
		} else if(func.contains("+1") || func.contains("/1") || func.contains("-1")|| func.contains("*1")) {
			weight[0]++;
		} else if(func.contains("+x") || func.contains("*x")||func.contains("/x")||func.contains("-1")) {
			weight[1]++;
		}
	}
	
	public void addRandFunc() {
		boolean added = false;
		while (!added && (System.currentTimeMillis() - startTime) / 60000 < minCount) {
			ArrayList<Integer> next = new ArrayList<Integer>();
			for (int x = 0; x < 10 * Math.random(); x++) {
				next.add(getRandomNum());
			}
			if (this.checkPrice(cost, next)) {
				ArrayList<String> toProcess = new ArrayList<String>();
				for (int x : next) {
					toProcess.add(functions[x]);
				}
				Functionv2 toAdd = randomFunction(toProcess);
				if (!storage.contains(toAdd)) {
					storage.add(toAdd);
					added = true;
				}
			}
		}
	}

	public int getRandomNum() {
		boolean chosen = false;
		int toReturn = 0;
		double total = 0;
		for(int x = 0; x < weight.length; x++) {
			total += weight[x];
		}
		while(!chosen) {
			for(int x = 0; x < weight.length; x++) {
				if(weight[x] / total > Math.random()) {
					chosen = true;
					toReturn = x;
					break;
				}
			}
		}
		return toReturn;
	}
	
	public Functionv2 getMostExtremas(ArrayList<Functionv2> toUse, double start, double end, double interval) {
		double biggestVal = 0;
		int count = 0;
		Functionv2 biggestFunc = null;
		for (Functionv2 x : toUse) {
			count++;
			double percent = (int) (10000 * 1 / (count / toUse.size())) / 10000;
			if (percent % 20 == 0) {
				JOptionPane.showMessageDialog(null, "%" + percent + " Done");
			}
			double current = x.getNumberOfExtremas(start, end, interval);
			if (current >= biggestVal && !undef(x)) {
				biggestVal = current;
				biggestFunc = x;
			}
		}
		return biggestFunc;
	}

	public Functionv2 getBiggestInt(ArrayList<Functionv2> toUse, double start, double end, double interval) {
		double biggestVal = 0;
		Functionv2 biggestFunc = null;
		for (Functionv2 x : toUse) {
			double current = x.integralOfFunc(start, end, interval);
			if (current >= biggestVal && !undef(x)) {
				biggestVal = current;
				biggestFunc = x;
			}
		}
		return biggestFunc;
	}

	public boolean undef(Functionv2 toCheck) {
		if (toCheck.getValueAt(Math.PI) == Double.POSITIVE_INFINITY || toCheck.getValueAt(1) == Double.POSITIVE_INFINITY
				|| toCheck.getValueAt(Math.PI / 2) == Double.POSITIVE_INFINITY 
				|| toCheck.getValueAt(Math.tan(1)) == Double.POSITIVE_INFINITY
				|| toCheck.getValueAt(Math.PI / 2) == Double.POSITIVE_INFINITY
				|| toCheck.getValueAt(0) == Double.POSITIVE_INFINITY) {
			
			return true;
		}
		return false;
	}

	public Functionv2 randomFunction(ArrayList<String> toUse) {
		ArrayList<String> functionVar = (ArrayList<String>) toUse.clone();
		int start = (int) (Math.random() * functionVar.size());
		double random = Math.random();
		Functionv2 toReturn = new Functionv2(functionVar.get(start));
		functionVar.remove(start);
		for (String x : functionVar) {
			if (random < .25) {
				toReturn.insertFunctionAtRandomPoint(x);
			} else {
				int levelToInsertAt = (int) (toReturn.getLevels() * Math.random());
				if (random < .5) {
					toReturn.addFuncAtLevel(x, levelToInsertAt);
				} else if (random < .75) {
					toReturn.multiplyFuncAtLevel(x, levelToInsertAt);
				} else {
					toReturn.subtractFuncAtLevel(x, levelToInsertAt);
				}
			}
		}
		return toReturn;
	}

	public boolean checkPrice(int toCheckTo, ArrayList<Integer> toCheck) {
		int totalCost = 0;
		for (Integer x : toCheck) {
			totalCost += costs[x];
		}
		if (totalCost > toCheckTo) {
			return false;
		}
		return true;
	}

}
