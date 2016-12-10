package io.thaumavor.rbradford.JavaCAS.MathOlympics.FinishThread;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import io.thaumavor.rbradford.JavaCAS.Library.Function;

public class Finished extends Thread {

	ArrayList<String> answers;
	ArrayList<Double> values;
	ArrayList<Integer> count;
	int length;
	
	public Finished(ArrayList<String> answers, int length, ArrayList<Double> values, ArrayList<Integer> count) {
		this.answers = answers;
		this.count = count;
		this.values = values;
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
			if(Math.abs(values.get(i)) > biggestVal) {
				biggestFunc = answers.get(i);
				biggestVal = Math.abs(values.get(i));
			}
		}
		double total = 0;
		for(Integer x: count) {
			total += x;
		}
		JOptionPane.showMessageDialog(null, "The biggest value is: " + biggestVal + 
				" of: " + biggestFunc + ". Checked " + total + " functions");
	}
}
