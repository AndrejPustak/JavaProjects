package hr.fer.zemris.java.hw17.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw17.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.Tool;
import hr.fer.zemris.java.hw17.jvdraw.components.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.components.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.geometrical.FilledCircle;

/**
 * Class that represents a tool used for drawing FilledCircles
 * @author Andrej
 *
 */
public class FilledCircleTool implements Tool{
	
	private Point startPoint;
	private Point endPoint;
	
	private FilledCircle tempCircle;
	
	private DrawingModel model;
	private IColorProvider fgProvider;
	private IColorProvider bgProvider;
	private JDrawingCanvas canvas;
	
	

	public FilledCircleTool(DrawingModel model, IColorProvider fgProvider, IColorProvider bgProvider, JDrawingCanvas canvas) {
		super();
		this.model = model;
		this.fgProvider = fgProvider;
		this.bgProvider = bgProvider;
		this.canvas = canvas;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(startPoint == null) {
			startPoint = e.getPoint();
			tempCircle = new FilledCircle(startPoint, startPoint, fgProvider.getCurrentColor(), bgProvider.getCurrentColor());
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
			g2d.setColor(tempCircle.getBgColor());
			g2d.fillOval(tempCircle.getCenter().x-tempCircle.getRadius(), tempCircle.getCenter().y-tempCircle.getRadius(), tempCircle.getRadius()*2, tempCircle.getRadius()*2);
			g2d.setColor(tempCircle.getFgColor());
			g2d.drawOval(tempCircle.getCenter().x-tempCircle.getRadius(), tempCircle.getCenter().y-tempCircle.getRadius(), tempCircle.getRadius()*2, tempCircle.getRadius()*2);
		}
	}
}
