package io.thaumavor.rbradford.JavaCAS.AreaProject;

import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JFrame;

import io.thaumavor.rbradford.JavaCAS.AreaProject.Graphics.Drawing;
import io.thaumavor.rbradford.JavaCAS.AreaProject.Graphics.Ship;
import io.thaumavor.rbradford.JavaCAS.Library.Function;

public class Project {
	
	
	public static void main(String[] args) {
		//Function hi = new Function("0.5*(x+0.25)^2-1");
		//System.out.println(hi.getValue(-3));
		new Project();
	}
	
	public Project() {
		Ship toDraw = new Ship();
		JFrame frame = new JFrame();
		frame.setBounds(0, 0, (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(), (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight());
		frame.setVisible(true);
		ArrayList<Drawing> drawings = new ArrayList<Drawing>();
		drawings.add(toDraw);
		GraphDisplay display = new GraphDisplay(drawings);
		frame.add(display);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
}
