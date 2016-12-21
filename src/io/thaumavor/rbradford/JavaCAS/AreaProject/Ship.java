package io.thaumavor.rbradford.JavaCAS.AreaProject;

import java.util.ArrayList;

import io.thaumavor.rbradford.JavaCAS.Library.Function;

public class Ship {

	static ArrayList<Function> functions;

	
	public Ship() {
		functions = new ArrayList<Function>();
		//initShip();
		Function test = new Function("-abs(x)");
		functions.add(test);
	}
	
	public void initShip() {
		initFlagpole();
		initFlag();
		initShipBase();
		initWater();
	}
	
	public void initFlag() {
		Function triangle = new Function("-2.5*abs(x)+8");
		Function flagBase = new Function("4.5");
		triangle.limitDomain(-1.4, 1.4);
		flagBase.limitDomain(-1.4, 1.4);
		functions.add(triangle);
		functions.add(flagBase);
	}
	
	public void initFlagpole() {
		Function poleLeft = new Function("60*x+8");
		Function poleRight = new Function("-60*x+8");
		poleLeft.limitRange(0, 8);
		poleRight.limitRange(0, 8);
		functions.add(poleLeft);
		functions.add(poleRight);
	}
	
	public void initShipBase() {
		Function upperDeck = new Function("0.5*(x-1)^(1/3)+1");
		Function lowerDeck = new Function("0.5");
		Function shipBottom = new Function("0.5*(x+0.25)^2-1");
		upperDeck.limitDomain(0, 2);
		lowerDeck.limitDomain(-2, 0);
		shipBottom.limitDomain(-2, 2);
		functions.add(upperDeck);
		functions.add(lowerDeck);
		functions.add(shipBottom);
	}
	
	public void initWater() {
		Function water = new Function("0.5*sin(10*x)-1");
		functions.add(water);
	}
	
	public ArrayList<Function> getFunctions() {
		return functions;
	}
	
	
}
