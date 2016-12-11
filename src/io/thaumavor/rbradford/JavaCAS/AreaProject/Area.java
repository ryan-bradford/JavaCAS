package io.thaumavor.rbradford.JavaCAS.AreaProject;

import java.util.ArrayList;

import javax.swing.JFrame;

import io.thaumavor.rbradford.JavaCAS.Library.Function;

public class Area {

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setBounds(0, 0, 1920, 1080);
		frame.setVisible(true);
		Function f = new Function("sin(3*x)");
		Function g = new Function("tan(x)");		
		ArrayList<Function> array = new ArrayList<Function>();
		array.add(f);
		array.add(g);
		GraphDisplay display = new GraphDisplay(array, 1, 2, 0, 1);
		frame.add(display);
		display.repaint();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
	}
	
}
