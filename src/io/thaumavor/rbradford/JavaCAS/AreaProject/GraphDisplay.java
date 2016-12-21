package io.thaumavor.rbradford.JavaCAS.AreaProject;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.ArrayList;

import javax.swing.JPanel;

import io.thaumavor.rbradford.JavaCAS.Library.Function;

public class GraphDisplay extends JPanel {

	ArrayList<Function> function;
	boolean toDrawArea;
	int lowerBound;
	int upperBound;
	int functionFIndex;
	int functionGIndex;
	int startX = -10;
	int startY = 0;
	int graphWidth = 20;
	int graphHeight = 20;
	int graphPixelWidth = 1920;
	int graphPixelHeight = 1080;

	public GraphDisplay(ArrayList<Function> function) {
		this.function = function;
		this.setBounds(0, 0, graphPixelWidth, graphPixelWidth);
		// this.setBackground(Color.BLACK);
	}

	public GraphDisplay(ArrayList<Function> function, int lowerBound, int upperBound, int functionF, int functionG) {
		toDrawArea = true;
		this.function = function;
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
		this.functionFIndex = functionF;
		this.functionGIndex = functionG;
		this.setBounds(0, 0, graphPixelWidth, graphPixelWidth);
	}

	@Override
	protected void paintComponent(Graphics g) {
		if (toDrawArea) {
			drawArea(function.get(functionFIndex), function.get(functionGIndex), g);
		}
		for (Function f : function) {
			drawFunction(f, g);
		}
	}

	public void drawArea(Function f, Function g, Graphics graph) {
		Integer lastX = null;
		Integer lastYG = null;
		Integer lastYF = null;
		double interval = new Double(graphWidth) * new Double(upperBound - lowerBound) / new Double(graphPixelWidth);
		for (double x = lowerBound; x < (upperBound); x += interval) {
			double yCordF = f.getValueAt(x);
			yCordF *= -1;
			yCordF *= new Double(graphPixelHeight);
			yCordF /= new Double(graphHeight);
			yCordF += graphPixelHeight / 2;
			double yCordG = g.getValueAt(x);
			yCordG *= -1;
			yCordG *= new Double(graphPixelHeight);
			yCordG /= new Double(graphHeight);
			yCordG += graphPixelHeight / 2;
			double shift = (new Double(startX));
			double xCord = (x - shift) * new Double(graphPixelWidth);
			xCord /= new Double(graphWidth);
			if (!new Double(yCordF).toString().contains("Infinity") && !new Double(yCordF).toString().equals("NaN")
					&& yCordF != Double.NEGATIVE_INFINITY) {
				if (lastX != null && lastYF != null && lastYG != null) {
					Polygon toDraw = new Polygon();
					toDraw.addPoint(lastX, lastYF);
					toDraw.addPoint(lastX, lastYG);
					toDraw.addPoint((int) xCord, (int) yCordG);
					toDraw.addPoint((int) xCord, (int) yCordF);
					graph.setColor(Rainbow.getNextColor());
					graph.fillPolygon(toDraw);
				}
			}
			lastX = (int) xCord;
			if(!Double.toString(yCordF).equals("NaN")) {
				lastYF = (int)yCordF;
			}			
			if(!Double.toString(yCordG).equals("NaN")) {
				lastYG = (int)yCordG;
			}	
		}
	}

	public void drawFunction(Function f, Graphics graph1) {
		Graphics2D graph = (Graphics2D) graph1;
		graph.setColor(Rainbow.getRandom());
		graph.setStroke(new BasicStroke(5));
		Integer lastX = null;
		Integer lastY = null;
		double interval = new Double(graphWidth) / new Double(graphPixelWidth);
		for (double x = startX; x < (startX + graphWidth); x += interval) {
			Double yCord = f.getValueAt(x);
			yCord *= -1;
			yCord *= new Double(graphPixelHeight);
			yCord /= new Double(graphHeight);
			yCord += graphPixelHeight / 2;
			double shift = (new Double(startX));
			double xCord = (x - shift) * new Double(graphPixelWidth);
			xCord /= new Double(graphWidth);
			if (lastX != null && lastY != null && !yCord.toString().equals("NaN")
					&& Double.isFinite(yCord) && Double.isFinite(lastY) && !lastY.toString().equals("NaN") && lastY>0) {
					graph.drawLine(lastX, lastY, (int) xCord, yCord.intValue());
				}
			lastX = (int) xCord;
			if(!yCord.toString().equals("NaN")) {
				lastY = yCord.intValue();
			}
			
		}
	}

}
