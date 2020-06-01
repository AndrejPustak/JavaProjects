package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javax.swing.JComponent;

/**
 * Class that extends JComponent and draws a bar chart.
 * @author Andrej
 *
 */
public class BarChartComponent extends JComponent{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * BarChart to draw
	 */
	private BarChart chart;

	/**
	 * Constructor for the BarChartComponent
	 * @param chart BarChart to draw
	 */
	public BarChartComponent(BarChart chart) {
		super();
		this.chart = chart;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Dimension dim = getSize();
		g.setColor(Color.DARK_GRAY);
		
		int yToText = 5;
		int xToText = 5;
		int yTextToNum = 5;
		int xTextToNum = 5;
		int yNum = Integer.toString(chart.getyMax()).length() * 8;
		int xNum = 10;
		int yNumToAxis = 3;
		int xNumToAxis = 3;
		int yAxisToTop = 5;
		int xAxisToRight = 5;
		int arrowLength = 5;
		int arrowAngle = 3;
		
		int textSize = g.getFont().getSize();
		
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform defaultAt = g2d.getTransform();
		
		g2d.rotate(-Math.PI / 2);
		
		
		g.drawString(chart.getyDesc(), -(dim.height/2 + (chart.getyDesc().length()/2 * 10)), 0 + yToText + textSize);
		g2d.setTransform(defaultAt);
		g.drawString(chart.getxDesc(), dim.width/2 - (chart.getyDesc().length()/2 * 20), dim.height-yToText);
		
		int xOrigin = yToText + textSize + yTextToNum + yNum + yNumToAxis;
		int yOrigin = dim.height - (xToText + textSize + xTextToNum + xNum + xNumToAxis);
		
		//axis
		g.drawLine(xOrigin, yOrigin, xOrigin, yAxisToTop);
		g.drawLine(xOrigin, yOrigin, dim.width - xAxisToRight, yOrigin);
		
		//arrows
		g.fillPolygon(new int[]{xOrigin, xOrigin + arrowAngle, xOrigin - arrowAngle}, new int[]{yAxisToTop,yAxisToTop+arrowLength,yAxisToTop+arrowLength}, 3);
		g.fillPolygon(new int[]{dim.width - xAxisToRight, dim.width - xAxisToRight - arrowLength, dim.width - xAxisToRight - arrowLength}, new int[]{yOrigin,yOrigin + arrowAngle,yOrigin - arrowAngle}, 3);
		
		//yAxis
		int ySpacing = (yOrigin - (yAxisToTop + arrowLength + 4)) / ((chart.getyMax()-chart.getyMin())/chart.getyDiff());
		for(int i = 0; i < (chart.getyMax()-chart.getyMin()) / chart.getyDiff() + 1; i++) {
			g.drawLine(dim.width - xAxisToRight, yOrigin - ySpacing*i, xOrigin-3, yOrigin - ySpacing*i);
			g.drawString(Integer.toString(i*chart.getyDiff()), xOrigin - 3 - 2 - Integer.toString(i*chart.getyDiff()).length() * 8, yOrigin - ySpacing*i + textSize/2);
		}
		
		//xAxis
		int xSpacing = (dim.width - xAxisToRight - arrowLength - 4 - xOrigin) / chart.getValues().size();
		int i = 1;
		g.drawLine(xOrigin, yOrigin, xOrigin, yOrigin+3);
		for(XYValue value : chart.getValues()) {
			g.drawLine(xOrigin + xSpacing*i, yOrigin, xOrigin + xSpacing*i, yOrigin + 3);
			g.drawString(Integer.toString(value.getX()), xOrigin + xSpacing*i - xSpacing/2 - (int)(Integer.toString(value.getX()).length()/2.0*8), yOrigin + 3 + 2 + textSize);
			g.setColor(Color.ORANGE);
			g.fillRect(xOrigin + (i-1)*xSpacing + 1, yOrigin - value.getY()/chart.getyDiff() * ySpacing, xSpacing-1, value.getY()/chart.getyDiff() * ySpacing);
			g.setColor(Color.DARK_GRAY);
			i++;
		}
	}
	
}
