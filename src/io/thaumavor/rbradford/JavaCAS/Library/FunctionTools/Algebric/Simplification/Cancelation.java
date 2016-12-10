package io.thaumavor.rbradford.JavaCAS.Library.FunctionTools.Algebric.Simplification;

import java.util.ArrayList;

import io.thaumavor.rbradford.JavaCAS.Library.Function;

public class Cancelation {

	Simplify simplify;
	Function superFunction;
	ArrayList<ArrayList<String>> mainParts;
	String newFunction;

	public Cancelation(Simplify simplify) {
		this.simplify = simplify;
		superFunction = simplify.superFunction;
		newFunction = "";
	}

	public void cancel() {
		split();
		for (int i = 0; i < mainParts.get(0).size(); i++) {
			destroyParts(mainParts.get(0).get(i));
		}
	}

	public void destroyParts(String stuff) {
		ArrayList<String> delims = new ArrayList<String>();
		delims.add("/");
		delims.add("/");
		Function part = new Function(stuff);
		ArrayList<String> multiplication = part.simplify.splitIntoLargestParts(delims, part.baseFunction).get(0);
		delims = new ArrayList<String>();
		delims.add("*");
		delims.add("*");
		for (int i = 0; i < multiplication.size(); i += 2) {
			if (i + 1 == multiplication.size()) {
				break;
			}
			Function numeratorFunc = new Function(multiplication.get(i));
			ArrayList<String> numerator = numeratorFunc.simplify
					.splitIntoLargestParts(delims, numeratorFunc.baseFunction).get(0);
			Function denomFunc = new Function(multiplication.get(i + 1));
			ArrayList<String> demon = denomFunc.simplify.splitIntoLargestParts(delims, denomFunc.baseFunction).get(0);
			if (demon.size() != 0) {
				for (int n = 0; n < numerator.size(); n++) {
					for (int d = 0; d < demon.size(); d++) {
						if (numerator.get(n).equals(demon.get(d))) {
							numerator.set(n, "1");
							demon.set(d, "1");
						}
					}
				}
			}
			for (int m = 0; m < mainParts.get(0).size(); m++) {
				if (!newFunction.equals("")) {
					newFunction += mainParts.get(1).get(m - 1);
				}
				int count = 0;
				for (String num : numerator) {
					if (count != 0) {
						newFunction += "*";
					}
					newFunction += num;
					count++;
				}
				count = 0;
				newFunction += "/";
				for (String num : demon) {
					newFunction += num;
				}
			}
		}
		simplify.newFunction = newFunction;
	}

	public void split() {
		ArrayList<String> delims = new ArrayList<String>();
		delims.add("+");
		delims.add("-");
		mainParts = simplify.splitIntoLargestParts(delims, simplify.newFunction);
	}

}
