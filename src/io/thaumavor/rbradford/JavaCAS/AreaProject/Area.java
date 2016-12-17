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
		Function f = new Function("(1+x)/x");
		System.out.println(f.getValue(2));
		f.simplify("x", new Operator("/"));
		System.out.println(f.toString());
		System.out.println(f.getValue(2));
		ArrayList<Function> array = new ArrayList<Function>();
		array.add(f);
		GraphDisplay display = new GraphDisplay(array);
		frame.add(display);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
}
