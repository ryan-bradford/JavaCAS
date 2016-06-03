package com.ryanb3.JavaCAS.Test;

import java.util.ArrayList;


import com.ryanb3.JavaCAS.Test.FinishThread.Finished;
import com.ryanb3.JavaCAS.Test.Worker.Worker;

public class MathOlympicsOptimize {

	String[] functions = { "1", "x", "abs(x)", "(x)^2", "1/(x)", "cos(x)", "sin(x)", "arctan(x)", "e^(x)",
	"ln(x)" };
	int[] cost = { 1, 7, 7, 12, 4, 14, 14, 3, 42, 4 };
	double interval;
	int functionsToGen;
	Worker[] tests;
	double start;
	double end;
	ArrayList<String> answers;
	int coreCount = 4;

	public MathOlympicsOptimize(int money, double start, double end, double interval, int functionsToGen) {
		answers = new ArrayList<String>();
		this.start = start;
		this.end = end;
		this.interval = interval;
		this.functionsToGen = functionsToGen;
		this.getPossibleFunctionsLists(money);
		Finished finisher = new Finished(answers, functionsToGen, start, end, interval, coreCount);
		finisher.start();
	}
	
	public void getPossibleFunctionsLists(int cost) {
		tests = new Worker[coreCount];
		for(int i = 0; i < tests.length; i++) {
			tests[i] = new Worker(cost, functionsToGen / coreCount, i, start, end, interval, answers);
			tests[i].start();
		}
	}
	
}
