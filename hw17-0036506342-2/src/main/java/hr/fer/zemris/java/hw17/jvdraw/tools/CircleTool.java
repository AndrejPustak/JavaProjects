package hr.fer.zemris.java.hw17.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw17.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.Tool;
import hr.fer.zemris.java.hw17.jvdraw.components.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.components.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.geometrical.Circle;

/**
 * Class that represents a tool for drawing Circles
 * @author Andrej
 *
 */
public class CircleTool implements Tool{
	private Point startPoint;
	private Point endPoint;
	
	private Circle tempCircle;
	
	private DrawingModel model;
	private IColorProvider provider;
	private JDrawingCanvas canvas;
	
	

	public CircleTool(DrawingModel model, IColorProvider provider, JDrawingCanvas canvas) {
		super();
		this.model = model;
		this.provider = provider;
		this.canvas = canvas;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(startPoint == null) {
			startPoint = e.getPoint();
			tempCircle = new Circle(startPoint, startPoint, provider.getCurrentColor());
		} else {
			endPoint = e.getPoint();
			tempCircle.setRadius((int) startPoint.distance(endPoint));;
			model.add(tempCircle);
			clear();
		}
	}
	
	private void clear() {
		startPoint = null;
		endPoint = null;
		tempCircle = null;
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
			tempCircle.setRadius((int) startPoint.distance(endPoint));;
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
			g2d.setColor(tempCircle.getColor());
			g2d.drawOval(tempCircle.getCenter().x-tempCircle.getRadius(), tempCircle.getCenter().y-tempCircle.getRadius(), tempCircle.getRadius()*2, tempCircle.getRadius()*2);
		}
	}
}
