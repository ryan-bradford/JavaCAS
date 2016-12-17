package io.thaumavor.rbradford.JavaCAS.AreaProject;

import java.util.ArrayList;

import javax.swing.JFrame;

import io.thaumavor.rbradford.JavaCAS.Library.Function;
import io.thaumavor.rbradford.JavaCAS.Library.Tree.Operator;

public class Area {
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setBounds(0, 0, 1280, 720);
		frame.setVisible(true);
		Function f = new Function("-x/x");
		Function g = new Function("1/x");
		f.simplify("x", new Operator("/"));
		ArrayList<Function> array = new ArrayList<Function>();
		array.add(f);
		array.add(g);
		GraphDisplay display = new GraphDisplay(array);
		frame.add(display);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
}
