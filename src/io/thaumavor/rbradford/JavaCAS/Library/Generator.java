package io.thaumavor.rbradford.JavaCAS.Library;

import io.thaumavor.rbradford.JavaCAS.Library.Tree.Operator;

import java.util.ArrayList;

/**
 * Created by ryan_bradford on 4/27/17.
 */
public class Generator {

    private String[] functions = {"1", "x", "abs(x)", "(x)^2", "1/(x)", "cos(x)", "sin(x)", "arctan(x)", "e^(x)", "ln(x)",
            "c"};
    private int[] costs = {1, 7, 7, 12, 4, 14, 14, 3, 42, 4, 1};
    private double[] weight = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1};

    public Generator() {

    }

    private Function randomFunctionFromParts(ArrayList<String> toUse) {
        ArrayList<String> functionVar = (ArrayList<String>) toUse.clone();
        int start = (int) (Math.random() * functionVar.size());
        double random = Math.random();
        Function toReturn = new Function(functionVar.get(start));
        functionVar.remove(start);
        for (String x : functionVar) {
            boolean hasX = toReturn.toString().contains("x");
            if (!hasX) {
                int levelToInsertAt = (int) (toReturn.change.getLevels() * Math.random());
                if (random < .33) {
                    toReturn.injectFunction(x, new Operator("+"), levelToInsertAt);
                } else if (random < .66) {
                    toReturn.injectFunction(x, new Operator("*"), levelToInsertAt);
                } else {
                    toReturn.injectFunction(x, new Operator("-"), levelToInsertAt);
                }
            } else if (random < .25) {
                toReturn.replaceX(x);
            } else {
                int levelToInsertAt = (int) (toReturn.change.getLevels() * Math.random());
                if (random < .5) {
                    toReturn.injectFunction(x, new Operator("+"), levelToInsertAt);
                } else if (random < .75) {
                    toReturn.injectFunction(x, new Operator("*"), levelToInsertAt);
                } else {
                    toReturn.injectFunction(x, new Operator("-"), levelToInsertAt);
                }
            }
        }
        return toReturn;
    }

    public Function randomFunction(int cost) {
        ArrayList<Integer> next = new ArrayList<Integer>();
        for (int x = 0; x < 10 * Math.random(); x++) {
            next.add(getRandomNum());
        }
        double price = this.checkPrice(next);
        if (price <= cost) {
            ArrayList<String> toProcess = new ArrayList<String>();
            for (int x : next) {
                toProcess.add(functions[x]);
            }
            if (price < cost) {
                if (Math.random() > .5) {
                    toProcess.add(optimizeConstants(cost - price));
                }
            }
            Function toAdd = randomFunctionFromParts(toProcess);
            return toAdd;
        }
        return randomFunction(cost);
    }

    private String optimizeConstants(double money) {
        double total = 0;
        double evenSquare = Math.floor(Math.sqrt(money));
        double subtract = money - (evenSquare * evenSquare);
        if (subtract == 0) {
            total = Math.pow(evenSquare, evenSquare);
            return Double.toString(total);
        } else if (subtract == 1) {
            total = Math.pow(evenSquare, evenSquare - 1);
            total *= (evenSquare + 1);
            return Double.toString(total);
        } else {
            total = Math.pow(evenSquare, evenSquare);
            total *= subtract;
            return Double.toString(total);
        }
    }

    private int getRandomNum() {
        boolean chosen = false;
        int toReturn = 0;
        double total = 0;
        for (int x = 0; x < weight.length; x++) {
            total += weight[x];
        }
        while (!chosen) {
            for (int x = 0; x < weight.length; x++) {
                if (weight[x] / total > Math.random()) {
                    chosen = true;
                    toReturn = x;
                    break;
                }
            }
        }
        return toReturn;
    }


    private double checkPrice(ArrayList<Integer> toCheck) {
        int totalCost = 0;
        for (Integer x : toCheck) {
            totalCost += costs[x];
        }
        return totalCost;
    }

    public void changeWeight(String func) {
        if (func.contains("abs")) {
            if (weight[2] < 5) {
                weight[2]++;
            }
        } else {
            if (weight[2] > 1) {
                weight[2]--;
            }
        }
        if (func.contains("^2")) {
            if (weight[3] < 5) {
                weight[3]++;
            }
        } else {
            if (weight[3] > 1) {
                weight[3]--;
            }
        }
        if (func.contains("\\/")) {
            if (weight[4] < 5) {
                weight[4]++;
            }
        } else {
            if (weight[4] > 1) {
                weight[4]--;
            }
        }
        if (func.contains("cos")) {
            if (weight[5] < 5) {
                weight[5]++;
            }
        } else {
            if (weight[5] > 1) {
                weight[5]--;
            }
        }
        if (func.contains("sin")) {
            if (weight[6] < 5) {
                weight[6]++;
            }
        } else {
            if (weight[6] > 1) {
                weight[6]--;
            }
        }
        if (func.contains("arctan")) {
            if (weight[7] < 5) {
                weight[7]++;
            }
        } else {
            if (weight[7] > 1) {
                weight[7]--;
            }
        }
        if (func.contains("e^")) {
            if (weight[8] < 5) {
                weight[8]++;
            }
        } else {
            if (weight[8] > 1) {
                weight[8]--;
            }
        }
        if (func.contains("ln")) {
            if (weight[9] < 5) {
                weight[9]++;
            }
        } else {
            if (weight[9] > 1) {
                weight[9]--;
            }
        }
        if (func.contains("+1") || func.contains("/1") || func.contains("-1") || func.contains("*1")) {
            if (weight[0] < 5) {
                weight[0]++;
            }
        } else {
            if (weight[0] > 1) {
                weight[0]--;
            }
        }
        if (func.contains("+x") || func.contains("*x") || func.contains("/x") || func.contains("-1")) {
            if (weight[1] < 5) {
                weight[1]++;
            }
        } else {
            if (weight[1] > 1) {
                weight[1]--;
            }
        }
    }

}
