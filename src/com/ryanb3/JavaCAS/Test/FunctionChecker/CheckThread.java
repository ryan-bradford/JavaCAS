package com.ryanb3.JavaCAS.Test.FunctionChecker;

import java.util.ArrayList;

import com.ryanb3.JavaCAS.Library.Functionv2;
import com.ryanb3.JavaCAS.Test.MathOlympicsOptimize;

public class CheckThread extends Thread {

	boolean done = false;
	ArrayList<Functionv2> toCheck;
	Boolean[] checkDone;
	int id;
	MathOlympicsOptimize math;
	
	public CheckThread(ArrayList<Functionv2> toCheck, Boolean[] checkDone, int id, MathOlympicsOptimize math) {
		this.toCheck = toCheck;
		this.id = id;
		this.checkDone = checkDone;
		this.math = math;
	}
	
	public void run() {
		this.mostExtremas(1, 5, .001, toCheck);
	}
	
	public String mostExtremas(double start, double end, double interval, ArrayList<Functionv2> toCheck) {
		double bestIntegral = 0;
		String function = "";
		for (int i = 0; i < toCheck.size(); i++) {
			System.out.println(i);
			Functionv2 current = toCheck.get(i);
			double integral = current.getNumberOfExtremas(start, end, interval);
			if (integral > bestIntegral && !Double.isInfinite(integral)) {
				bestIntegral = integral;
				function = current.baseFunction;
			}
		}
		System.out.println(function);
		return function;
	}
	
}
