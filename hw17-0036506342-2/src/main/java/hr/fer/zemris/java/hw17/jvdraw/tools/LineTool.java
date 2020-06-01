package hr.fer.zemris.java.hw17.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw17.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.Tool;
import hr.fer.zemris.java.hw17.jvdraw.components.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.components.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.geometrical.Line;

/**
 * Class that representa a tool used for drawing lines
 * @author Andrej
 *
 */
public class LineTool implements Tool{
	
	private Point startPoint;
	private Point endPoint;
	
	private Line tempLine;
	
	private DrawingModel model;
	private IColorProvider provider;
	private JDrawingCanvas canvas;
	
	

	public LineTool(DrawingModel model, IColorProvider provider, JDrawingCanvas canvas) {
		super();
		this.model = model;
		this.provider = provider;
		this.canvas = canvas;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(startPoint == null) {
			startPoint = e.getPoint();
			tempLine = new Line(startPoint, startPoint, provider.getCurrentColor());
		} else {
			endPoint = e.getPoint();
			tempLine.setEndPoint(endPoint);
			model.add(tempLine);
			clear();
		}
	}
	
	private void clear() {
		startPoint = null;
		endPoint = null;
		tempLine = null;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		if(startPoint == null) {
		} else {
			endPoint = e.getPoint();
			tempLine.setEndPoint(endPoint);
			canvas.repaint();
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void paint(Graphics2D g2d) {
		if(startPoint != null && endPoint != null) {
			canvas.repaint();
			g2d.setColor(provider.getCurrentColor());
			g2d.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
		}
	}
}
