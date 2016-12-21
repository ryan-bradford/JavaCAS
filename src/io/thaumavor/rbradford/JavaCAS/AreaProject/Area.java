package io.thaumavor.rbradford.JavaCAS.AreaProject;

import javax.swing.JFrame;

import io.thaumavor.rbradford.JavaCAS.Library.Function;

public class Area {
	
	
	public static void main(String[] args) {
		Ship toDraw = new Ship();
		JFrame frame = new JFrame();
		frame.setBounds(0, 0, 1920, 1080);
		frame.setVisible(true);
		GraphDisplay display = new GraphDisplay(toDraw.getFunctions());
		frame.add(display);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
}
