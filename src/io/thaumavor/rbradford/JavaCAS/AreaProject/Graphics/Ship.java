package io.thaumavor.rbradford.JavaCAS.AreaProject.Graphics;

import java.awt.Color;

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
		drawCloud();
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
		Function waterFirst = new Function("0.5*sin(10*x)+cos(x)*sin(x)-1");
		waterFirst.limitDomain(-10, 0);
		functions.add(waterFirst);
		waterFirst.setColor(Color.BLUE);
		
		Area filledWaterFirst = new Area(waterFirst, new Function("-10"), Color.BLUE, -10.0, 0);
		shadedParts.add(filledWaterFirst);
		
		Function waterSecond = new Function("0.5*sin(10*x)+cos(x)-(sin(x)+1)");
		waterSecond.limitDomain(-.01, 10);
		functions.add(waterSecond);
		waterSecond.setColor(Color.BLUE);
		
		Area filledWaterSecond = new Area(waterSecond, new Function("-10"), Color.BLUE, -0.1, 10);
		shadedParts.add(filledWaterSecond);
	}
	
	public void drawCloud() {
		Function cloudTop = new Function("(4-(x-5)^2+sin(4*x-5))^(1/2)+5");
		Function cloudBottom = new Function("-((4-(x-5)^2+1*sin(4*x-5))^(1/2))+5.3");
		functions.add(cloudTop);
		functions.add(cloudBottom);
		cloudTop.setColor(Color.BLACK);
		cloudBottom.setColor(Color.BLACK);
		
		Function cloudTop2 = new Function("(4-(x+5)^2+1*sin(2*x+5))^(1/2)+5");
		Function cloudBottom2 = new Function("-((4-(x+5)^2+1*sin(2*x+5))^(1/2))+5.4");
		functions.add(cloudTop2);
		functions.add(cloudBottom2);
		cloudTop2.setColor(Color.BLACK);
		cloudBottom2.setColor(Color.BLACK);
	}
	
	public ArrayList<Function> getFunctions() {
		return functions;
	}
	
	
}
