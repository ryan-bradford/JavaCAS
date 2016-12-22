package io.thaumavor.rbradford.JavaCAS.AreaProject;

import java.util.ArrayList;

import javax.swing.JFrame;

import io.thaumavor.rbradford.JavaCAS.AreaProject.Graphics.Drawing;
import io.thaumavor.rbradford.JavaCAS.AreaProject.Graphics.Ship;
import io.thaumavor.rbradford.JavaCAS.Library.Function;

public class Project {
	
	
	public static void main(String[] args) {
		Ship toDraw = new Ship();
		JFrame frame = new JFrame();
		frame.setBounds(0, 0, 1920, 1080);
		frame.setVisible(true);
		ArrayList<Drawing> drawings = new ArrayList<Drawing>();
		drawings.add(toDraw);
		GraphDisplay display = new GraphDisplay(drawings);
		frame.add(display);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
}
