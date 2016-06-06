package com.ryanb3.JavaCAS.Test.FinishThread;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.ryanb3.JavaCAS.Library.Functionv2;

public class Finished extends Thread {

	ArrayList<String> answers;
	double start;
	double end;
	double interval;
	int length;
	ArrayList<Integer> count;
	
	public Finished(ArrayList<String> answers, double start, double end, double interval, int length, ArrayList<Integer> count) {
		this.answers = answers;
		this.start = start;
		this.end = end;
		this.interval = interval;
		this.length = length;
		this.count = count;
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
		double total = 0;
		for(Integer x: count) {
			total += x;
		}
		JOptionPane.showMessageDialog(null, "The biggest integral is: " + new Functionv2(biggestFunc).integralOfFunc(start, end, interval) + 
				" of: " + biggestFunc + ". Checked " + total + " functions");
	}
}
