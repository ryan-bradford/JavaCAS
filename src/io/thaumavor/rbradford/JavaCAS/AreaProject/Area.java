package io.thaumavor.rbradford.JavaCAS.AreaProject;

import java.util.ArrayList;

import javax.swing.JFrame;

import io.thaumavor.rbradford.JavaCAS.Library.Function;
import io.thaumavor.rbradford.JavaCAS.Library.FunctionTools.Calculus.DifferentialFunction;

public class Area {

	public static void main(String[] args) {
		
		JFrame frame = new JFrame();
		frame.setBounds(0, 0, 1920, 1080);
		frame.setVisible(true);
		Function f = new Function("ln(x)");
		Function g = new Function("cos(x)");		
		ArrayList<Function> array = new ArrayList<Function>();
		array.add(f);
		array.add(g);
		GraphDisplay display = new GraphDisplay(array, 1, 2, 0, 1);
		frame.add(display);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
	}
	
}
