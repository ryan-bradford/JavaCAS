package io.thaumavor.rbradford.JavaCAS.AreaProject.Graphics;

import java.awt.Color;

public class Rainbow {

	public static Color[] rainbow = new Color[] {Color.RED, Color.orange, Color.yellow, Color.green, Color.blue, Color.pink};
	static int current = 0;
	
	public static Color getNextColor() {
		current++;
		if(current == rainbow.length) {
			current = 0;
		}
		return rainbow[current];
	}
	
	public static Color getRandom() {
		return rainbow[(int)Math.floor(Math.random() * (double)(rainbow.length))];
	}
	
	
}
