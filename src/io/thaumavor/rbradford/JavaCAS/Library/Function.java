package io.thaumavor.rbradford.JavaCAS.Library;

import io.thaumavor.rbradford.JavaCAS.Library.Algebric.Change;
import io.thaumavor.rbradford.JavaCAS.Library.Calculus.GeneralCalculus;
import io.thaumavor.rbradford.JavaCAS.Library.Tree.Branch;

public class Function extends Branch {
	
	private String baseFunction;
	public GeneralCalculus calculus;
	public Change change;
	
	public Function(String baseFunction) {
		super(baseFunction);
		this.baseFunction = baseFunction;
		calculus = new GeneralCalculus(this);
		this.change = new Change(this);
	}
	
	public double getValueAt(double x) {
		return this.getValue(x);
	}
	
	public double getValueAt(double x, double y) {
		return this.getValue(x, y);
	}
	
	public String getBaseFunction() {
		return baseFunction;
	}
	
	public String toString() {
		return super.toString();
	}
	
	public void setBaseFunction(String newFunction) {
		this.baseFunction = newFunction;
		this.initFunction();
	}
	
	public void simplfiy() {
		
	}
	
}
