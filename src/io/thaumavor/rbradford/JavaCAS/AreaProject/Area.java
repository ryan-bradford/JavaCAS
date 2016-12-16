package io.thaumavor.rbradford.JavaCAS.AreaProject;

import java.util.ArrayList;

import javax.swing.JFrame;

import io.thaumavor.rbradford.JavaCAS.Library.Function;

public class Area {
	
	public static void main(String[] args) {
		Function f = new Function("sin(-x)");
		System.out.println(f.getValueAt(-1));
		/*
		JFrame frame = new JFrame();
		frame.setBounds(0, 0, 1280, 720);
		frame.setVisible(true);
		Function f = new Function("sin(x)");
		ArrayList<Function> array = new ArrayList<Function>();
		array.add(f);
		GraphDisplay display = new GraphDisplay(array);
		frame.add(display);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		*/
		
		
	}
	
}
