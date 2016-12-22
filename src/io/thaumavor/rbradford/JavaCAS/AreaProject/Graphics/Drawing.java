package io.thaumavor.rbradford.JavaCAS.AreaProject.Graphics;

import java.util.ArrayList;

import io.thaumavor.rbradford.JavaCAS.Library.Function;

public class Drawing {

	ArrayList<Function> functions;
	ArrayList<Area> shadedParts;
	
	public Drawing() {
		functions = new ArrayList<Function>();
		shadedParts = new ArrayList<Area>();
	}
	
	public ArrayList<Function> getFunctions() {
		return functions;
	}

	public ArrayList<Area> getAreas() {
		return shadedParts;
	}
	
}
