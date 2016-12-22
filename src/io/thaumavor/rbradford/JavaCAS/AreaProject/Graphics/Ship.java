package io.thaumavor.rbradford.JavaCAS.AreaProject.Graphics;

import java.awt.Color;
import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;

import io.thaumavor.rbradford.JavaCAS.Library.Function;

public class Ship extends Drawing {
	
	public Ship() {
		functions = new ArrayList<Function>();
		initShip();
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
		
		Area flag = new Area(triangle, flagBase, -1.4, 1.4);
		flag.setInterval(2);
		shadedParts.add(flag);

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
		lowerDeck.limitDomain(-2, 0.1);
		shipBottom.limitDomain(-2, 2);
		functions.add(upperDeck);
		functions.add(lowerDeck);
		functions.add(shipBottom);
		upperDeck.setColor(new Color(102,51,0));
		lowerDeck.setColor(new Color(102,51,0));
		shipBottom.setColor(new Color(102,51,0));
		
		Area lowerToBase = new Area(lowerDeck, shipBottom, new Color(102,51,0), -2, 0.1);
		Area upperToBase = new Area(upperDeck, shipBottom, new Color(102,51,0), 0, 2);
		shadedParts.add(lowerToBase);
		shadedParts.add(upperToBase);
	}
	
	public void initWater() {
		Function water = new Function("0.5*sin(10*x)-1");
		functions.add(water);
		water.setColor(Color.BLUE);
		
		Area filledWater = new Area(water, new Function("-10"), Color.BLUE, -10.0, 10.0);
		shadedParts.add(filledWater);
	}
	
	public ArrayList<Function> getFunctions() {
		return functions;
	}
	
	
}
