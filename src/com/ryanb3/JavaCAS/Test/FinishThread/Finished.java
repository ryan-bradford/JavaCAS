package com.ryanb3.JavaCAS.Test.FinishThread;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.ryanb3.JavaCAS.Library.Functionv2;

public class Finished extends Thread {

	ArrayList<String> answers;
	int goal;
	double start;
	double end;
	double interval;
	int length;
	
	public Finished(ArrayList<String> answers, int goal, double start, double end, double interval, int length) {
		this.answers = answers;
		this.goal = goal;
		this.start = start;
		this.end = end;
		this.interval = interval;
		this.length = length;
	}
	
	public void run() {
		while(answers.size() < length) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String biggestFunc = "1";
		double biggestVal = -1;
		for(int i = 0; i < answers.size(); i++) {
			if(new Functionv2(answers.get(i)).integralOfFunc(start, end, interval) > biggestVal) {
				biggestFunc = answers.get(i);
			}
		}
		JOptionPane.showMessageDialog(null, "The biggest integral is: " + new Functionv2(biggestFunc).integralOfFunc(start, end, interval) + 
				" of: " + biggestFunc);
	}
}
