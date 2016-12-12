package io.thaumavor.rbradford.JavaCAS.Library.FunctionTools.Calculus;

import io.thaumavor.rbradford.JavaCAS.Library.Function;

public class DifferentialFunction extends Function {

	double startingX;
	double startingY;
	double eulerInterval = .01;
	
	public DifferentialFunction(String function, double startingX, double startingY) {
		super(function);
		this.startingX = startingX;
		this.startingY = startingY;
	}
	
	public double getValueAt(double xCord) {
		double currentY = startingY;
		if(xCord > startingX) {
			for(double x = startingX; x < xCord; x+=eulerInterval) {
				currentY += super.getValueAt(x, currentY) * eulerInterval;
			}	
		} else {
			for(double x = startingX; x > xCord; x-=eulerInterval) {
				currentY -= super.getValueAt(x, currentY) * eulerInterval;
			}
		}
		return currentY;
		
	}
	
	
	
}
