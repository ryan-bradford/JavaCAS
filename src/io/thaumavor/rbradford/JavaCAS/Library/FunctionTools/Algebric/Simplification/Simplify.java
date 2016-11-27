package io.thaumavor.rbradford.JavaCAS.Library.FunctionTools.Algebric.Simplification;

import io.thaumavor.rbradford.JavaCAS.Library.Function;

public class Simplify {

	Function superFunction;
	String newFunction;
	Expansion expand;

	public Simplify(Function baseFunction) {
		this.superFunction = baseFunction;
		newFunction = "";
		expand = new Expansion(this);
	}
	
	public void simplify() {
		expand.expand();
		System.out.println("Simplified Function: " + this.newFunction);
	}

}
