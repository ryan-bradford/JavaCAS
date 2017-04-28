package io.thaumavor.rbradford.JavaCAS.MathOlympics.Worker;

import io.thaumavor.rbradford.JavaCAS.Library.Function;
import io.thaumavor.rbradford.JavaCAS.Library.Generator;

import javax.swing.*;
import java.util.ArrayList;

public class Worker extends Thread {

	ArrayList<Function> storage;
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
	Generator generator;
	
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
		generator = new Generator();
	}

	public void run() {
		while ((System.currentTimeMillis() - startTime) / 60000 < minCount) {
			Function newFunc = generator.randomFunction(cost);
			if (!storage.contains(newFunc)) {
				storage.add(newFunc);
				try {
					Function x = storage.get(storage.size() - 1);
					doIntegral(x);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		System.out.println("Done");
		answers.add(biggestFunc);
		count.add(storage.size());
		values.add(biggest);
	}
	
	public void doDeriv(Function x) {
		double extremas = x.calculus.biggestDerivOfFunc(start, end, interval);
		if (extremas > biggest && !undef(x)) {
			biggestFunc = x.getBaseFunction();
			timesChosen++;
			if (timesChosen > 20) {
				generator.changeWeight(biggestFunc);
				timesChosen = 0;
			}
			biggest = extremas;
		}
	}
	
	public void doExtremas(Function x) {
		double extremas = x.calculus.getNumberOfExtremas(start, end, interval);
		if (extremas > biggest && !undef(x)) {
			biggestFunc = x.getBaseFunction();
			timesChosen++;
			if (timesChosen > 20) {
				generator.changeWeight(biggestFunc);
				timesChosen = 0;
			}
			biggest = extremas;
		}
	}
	
	public void doIntegral(Function x) {
		double integral = x.calculus.integralOfFunc(start, end, interval);
		if (integral > biggest && !undef(x)) {
			biggestFunc = x.toString();
			timesChosen++;
			if (timesChosen > 20) {
				generator.changeWeight(biggestFunc);
				timesChosen = 0;
			}
			biggest = integral;
		}
	}

	public Function getMostExtremas(ArrayList<Function> toUse, double start, double end, double interval) {
		double biggestVal = 0;
		int count = 0;
		Function biggestFunc = null;
		for (Function x : toUse) {
			count++;
			double percent = 10000 * 1 / (count / toUse.size()) / 10000;
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
		return toCheck.getValueAt(Math.PI) == Double.POSITIVE_INFINITY || toCheck.getValueAt(1) == Double.POSITIVE_INFINITY
				|| toCheck.getValueAt(Math.PI / 2) == Double.POSITIVE_INFINITY
				|| toCheck.getValueAt(Math.tan(1)) == Double.POSITIVE_INFINITY
				|| toCheck.getValueAt(0) == Double.POSITIVE_INFINITY;
	}

}
