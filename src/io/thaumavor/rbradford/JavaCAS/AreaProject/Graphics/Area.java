package io.thaumavor.rbradford.JavaCAS.AreaProject.Graphics;

import java.awt.Color;

import io.thaumavor.rbradford.JavaCAS.Library.Function;

public class Area {

	private Function f;
	private Function g;
	private Color toColor = null;
	private double lowerBound;
	private double upperBound;
	private Double interval = null;
	
	public Area(Function f, Function g, Color toColor, double lowerBound, double upperBound) {
		this(f, g, lowerBound, upperBound);
		this.toColor = toColor;
	}
	
	public Area(Function f, Function g, double lowerBound, double upperBound) {
		this.f = f;
		this.g = g;
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
	}
	
	public Function getF() {
		return f;
	}

	public Function getG() {
		return g;
	}
	
	public double getLowerBound() {
		return lowerBound;
	}
	
	public double getUpperBound() {
		return upperBound;
	}
	
	public Color getColor() {
		return toColor;
	}
	
	public Double getInterval() {
		return interval;
	}
	
	public void setInterval(double toSet) {
		interval = toSet;
	}
	
}
