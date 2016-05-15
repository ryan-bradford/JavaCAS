package com.ryanb3.JavaCAS;

public class main {

	String[] functions = { "(1)", "(x)", "abs(x)", "2^(x)", "1/(x)", "cos(x)", "sin(x)", "arctan(x)", "e^(x)",
			"ln(x)" };
	int[] cost = { 1, 7, 7, 12, 4, 14, 14, 3, 42, 4 };

	public static void main(String[] args) {
		new main();
	}
	
	//Many Grouping
	//Opperations within Compositon
	
	public main() {
		Function thisFunction = new Function("2^(x)");
		System.out.println(thisFunction.getValueAt(4, thisFunction.function, true));
		System.out.println(thisFunction.derivOfFunc(1, .1));
		System.out.println(thisFunction.integralOfFunc(1, 5, .1));
	}
}
