package io.thaumavor.rbradford.JavaCAS.Library;

import io.thaumavor.rbradford.JavaCAS.Library.FunctionTools.Calculus;
import io.thaumavor.rbradford.JavaCAS.Library.FunctionTools.General;
import io.thaumavor.rbradford.JavaCAS.Library.FunctionTools.Value;
import io.thaumavor.rbradford.JavaCAS.Library.FunctionTools.Algebric.Change;
import io.thaumavor.rbradford.JavaCAS.Library.FunctionTools.Algebric.Simplification.Simplify;

public class Function {

	public Change change;
	public Calculus calculus;
	public Value value;
	public String baseFunction;
	public General general;
	public Simplify simplify;
	
	public Function(String function) {
		this.baseFunction = function;
		general = new General();
		initModules();
	}

	public double getValueAt(double at) {
		return value.getValueAt(at);
	}
	
	public void initModules() {
		change = new Change(this);
		value = new Value(this);
		simplify = new Simplify(this);
		calculus = new Calculus(this);
	}
	
}
