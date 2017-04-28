package io.thaumavor.rbradford.JavaCAS.MathOlympics;

import io.thaumavor.rbradford.JavaCAS.Library.Function;

import javax.swing.*;

public class Test {
		
	double interval = .01;

	public Test() {
		standardStart();
	}

	public static void main(String[] args) {
		new Test();
	}
	
	public void standardStart() {
		String message = JOptionPane.showInputDialog("Press 1 for the Math Olympics Integral Optimization \n Press 2 for general function manipulation.");
		if(message.equals("1")) {
			double start = Double.parseDouble(JOptionPane.showInputDialog("What is the start x?"));
			double end = Double.parseDouble(JOptionPane.showInputDialog("What is the end x?"));
			int money = Integer.parseInt(JOptionPane.showInputDialog("How much money do you want to spend?"));
			Double count = Double.parseDouble(JOptionPane.showInputDialog("How many minutes do you want to run for?"));
			MathOlympicsOptimize test = new MathOlympicsOptimize(money, start, end, .001, count);
			JOptionPane.showMessageDialog(null, "Working");
		} else {
			interval = .001;
			manipulateFunctions();
		}
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
		String entered = JOptionPane.showInputDialog("Enter a function that you want the derivative of:");
		Function text = new Function(entered);
		Double start = Double.parseDouble(JOptionPane.showInputDialog("Where to get the derivative at?"));
		JOptionPane.showMessageDialog(null, "The derivative of f at " + start + " is: " + text.calculus.derivOfFunc(start, interval));
	}
	
	public void biggestDerivativeStart() {
		String entered = JOptionPane.showInputDialog("Enter a function that you want the biggest derivative of:");
		Function text = new Function(entered);
		Double start = Double.parseDouble(JOptionPane.showInputDialog("What is the start point?"));
		Double end = Double.parseDouble(JOptionPane.showInputDialog("What is the end point?"));
		JOptionPane.showMessageDialog(null, "The biggest derivative between " + start + " and " + end + " is: " + text.calculus.biggestDerivOfFunc(start, end, interval));
	}
	
	public void integralStart() {
		String entered = JOptionPane.showInputDialog("Enter a function that you want the integral of:");
		Function text = new Function(entered);
		Double start = Double.parseDouble(JOptionPane.showInputDialog("Where to get the integral from?"));
		Double end = Double.parseDouble(JOptionPane.showInputDialog("Where to get the integral to?"));
		JOptionPane.showMessageDialog(null, "The integral of f from " + start + " to  " + end + " is: " + text.calculus.integralOfFunc(start, end, interval));
	}
	
	public void functionStart() {
		String entered = JOptionPane.showInputDialog("Enter a function that you want the function of:");
		Function text = new Function(entered);
		Double start = Double.parseDouble(JOptionPane.showInputDialog("Where to get the function at?"));
		JOptionPane.showMessageDialog(null, "The f of " + start + " is: " + text.getValueAt(start));
	}
	
	public void extremasStart() {
		String entered = JOptionPane.showInputDialog("Enter a function that you want the number of extremas:");
		Function text = new Function(entered);
		Double start = Double.parseDouble(JOptionPane.showInputDialog("Start x"));
		Double end = Double.parseDouble(JOptionPane.showInputDialog("End x"));
		Double interval = .00001;
		JOptionPane.showMessageDialog(null, "The # from " + start + " to " + end + " is: " + text.calculus.getNumberOfExtremas(start, end, interval));
	}




}
