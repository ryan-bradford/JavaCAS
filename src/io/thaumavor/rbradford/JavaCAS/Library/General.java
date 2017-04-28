package io.thaumavor.rbradford.JavaCAS.Library;

public class General {

	public static double getValueOfPart(String z, double xCord, double yCord) {
		double toReturn = 0;
		boolean negative = false;
		if (z.contains("-") && z.charAt(0) == '-') {
			negative = true;
			z = z.substring(1, z.length());
		}
		if (z.equals("x")) {
			toReturn = xCord;
		} else if (z.equals("y")) {
			toReturn = yCord;
		} else if (z.contains("Infinity")) {
			toReturn = Double.POSITIVE_INFINITY;
		} else if (z.equals("PI")) {
			toReturn = (Math.PI);
		} else if (z.equals("e")) {
			toReturn = (Math.E);
		} else {
			toReturn = (Double.parseDouble(z));
		}

		if (negative) {
			return -toReturn;
		} else {
			return toReturn;
		}
	}

	public static boolean isOpperator(char stuff) {
        return stuff == '+' || stuff == '-' || stuff == '/' || stuff == '*' || stuff == '^' || stuff == 'n' || stuff == 's' || stuff == 'E';
    }

    public static boolean isFunction(String toCheck) {
        return toCheck.contains("sin")||toCheck.contains("cos")||toCheck.contains("tan")||toCheck.contains("sec")||toCheck.contains("csc")
				||toCheck.contains("cot")||toCheck.contains("arcsin")||toCheck.contains("arccos")||toCheck.contains("log")||toCheck.contains("ln")
		||toCheck.contains("abs")||toCheck.contains("arctan");
	}
	
}
