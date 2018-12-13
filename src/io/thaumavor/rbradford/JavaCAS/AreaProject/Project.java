package io.thaumavor.rbradford.JavaCAS.AreaProject;

import io.thaumavor.rbradford.JavaCAS.AreaProject.Graphics.Drawing;
import io.thaumavor.rbradford.JavaCAS.AreaProject.Graphics.Ship;
import io.thaumavor.rbradford.JavaCAS.Library.Function;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Project {
	
	
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

	public Project(Function f) {
		GraphDisplay graph = new GraphDisplay(f);
		JFrame frame = new JFrame();
		frame.setVisible(true);
		frame.setBounds(0, 0, (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(), (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight());
		frame.add(graph);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public Project(Function f, Function g) {
		GraphDisplay graph = new GraphDisplay(f, g);
		JFrame frame = new JFrame();
		frame.setVisible(true);
		frame.setBounds(0, 0, (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(), (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight());
		frame.add(graph);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		new Project();
	}

}
