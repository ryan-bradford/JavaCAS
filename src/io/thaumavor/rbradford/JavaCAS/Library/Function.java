package io.thaumavor.rbradford.JavaCAS.Library;

import io.thaumavor.rbradford.JavaCAS.Library.Algebric.Change;
import io.thaumavor.rbradford.JavaCAS.Library.Calculus.GeneralCalculus;
import io.thaumavor.rbradford.JavaCAS.Library.Tree.Branch;

public class Function {
	
	private Branch branch;
	private String baseFunction;
	public GeneralCalculus calculus;
	public Change change;
	
	public Function(String baseFunction) {
		this.baseFunction = baseFunction;
		this.branch = new Branch(baseFunction);
		calculus = new GeneralCalculus(this);
		this.change = new Change(this);
	}
	
	public double getValueAt(double x) {
		return branch.getValue(x);
	}
	
	public double getValueAt(double x, double y) {
		return branch.getValue(x, y);
	}
	
	public String getBaseFunction() {
		return this.baseFunction;
	}
	
	public String toString() {
		return branch.toString();
	}
	
	public void setBaseFunction(String newFunction) {
		this.baseFunction = newFunction;
		this.branch = new Branch(baseFunction);
	}
	
}
