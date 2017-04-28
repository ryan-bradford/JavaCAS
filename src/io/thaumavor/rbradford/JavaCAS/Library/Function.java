package io.thaumavor.rbradford.JavaCAS.Library;

import io.thaumavor.rbradford.JavaCAS.Library.Algebric.Change;
import io.thaumavor.rbradford.JavaCAS.Library.Algebric.Simplfication.Expand;
import io.thaumavor.rbradford.JavaCAS.Library.Calculus.GeneralCalculus;
import io.thaumavor.rbradford.JavaCAS.Library.Tree.Branch;

import java.awt.*;

public class Function extends Branch {
	
	public GeneralCalculus calculus;
	public Change change;
	public Expand expand;
	private String baseFunction;
	private Double lowX;
	private Double highX;
	private Double lowY;
	private Double highY;
	private Color color = null;
	
	public Function(String baseFunction) {
		super(baseFunction);
		this.baseFunction = baseFunction;
		calculus = new GeneralCalculus(this);
		this.change = new Change(this);
		expand = new Expand();
	}
	
	public double getValueAt(double x) {
		if((lowX != null && x < lowX) || (highX != null && x > highX)) {
			return Double.POSITIVE_INFINITY;
		}
		double val = this.getValue(x);
		if((highY != null && val > highY) || (lowY != null && val < lowY)) {
			return Double.POSITIVE_INFINITY;
		}
		return val;
	}
	
	public double getValueAt(double x, double y) {
		return this.getValue(x, y);
	}
	
	public String getBaseFunction() {
		return baseFunction;
	}
	
	public void setBaseFunction(String newFunction) {
		this.baseFunction = newFunction;
		this.initFunction();
	}

	public String toString() {
		return super.toString();
	}
	
	public void simplfiy() {
		
	}
	
	public void limitDomain(double lowX, double highX) {
		this.lowX = lowX;
		this.highX = highX;
	}
	
	public void limitRange(double lowY, double highY) {
		this.lowY = lowY;
		this.highY = highY;
	}
	
	public Color getColor() {
		return color;
	}

	public void setColor(Color toSet) {
		this.color = toSet;
	}
	
}
