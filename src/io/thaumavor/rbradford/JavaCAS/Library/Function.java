package io.thaumavor.rbradford.JavaCAS.Library;

import io.thaumavor.rbradford.JavaCAS.Library.FunctionTools.General;
import io.thaumavor.rbradford.JavaCAS.Library.FunctionTools.Value;
import io.thaumavor.rbradford.JavaCAS.Library.FunctionTools.Algebric.Change;
import io.thaumavor.rbradford.JavaCAS.Library.FunctionTools.Algebric.Simplification.Simplify;
import io.thaumavor.rbradford.JavaCAS.Library.FunctionTools.Calculus.GeneralCalculus;

public class Function {

	public Change change;
	public GeneralCalculus calculus;
	public Value value;
	public String baseFunction;
	public General general;
	public Simplify simplify;
	
	public Function(String function) {
		this.baseFunction = function;
		general = new General();
		initModules();
	}

	public double getValueAt(double xCord, double yCord) {
		return value.getValueAt(xCord, yCord);
	}
	
	public double getValueAt(double xCord) {
		return value.getValueAt(xCord, 0);
	}
	
	public void initModules() {
		change = new Change(this);
		value = new Value(this);
		simplify = new Simplify(this);
		calculus = new GeneralCalculus(this);
	}
	
}
