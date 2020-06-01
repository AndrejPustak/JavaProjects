package hr.fer.zemris.java.hw17.jvdraw.geometrical;

import java.awt.Graphics2D;

/**
 * Implementation of GeometricalObjectVisitor used to paint the object on the graphics
 * @author Andrej
 *
 */
public class GeometricalObjectPainter implements GeometricalObjectVisitor{
	
	/**
	 * Graphics
	 */
	private Graphics2D g2d;
	
	/**
	 * COnstructor for the GeometricalObjectPainter
	 * @param g2d graphics
	 */
	public GeometricalObjectPainter(Graphics2D g2d) {
		this.g2d = g2d;
	}

	@Override
	public void visit(Line line) {
		g2d.setColor(line.getColor());
		g2d.drawLine(line.getStartPoint().x, line.getStartPoint().y, line.getEndPoint().x, line.getEndPoint().y);
	}

	@Override
	public void visit(Circle circle) {
		g2d.setColor(circle.getColor());
		g2d.drawOval(circle.getCenter().x-circle.getRadius(), circle.getCenter().y-circle.getRadius(), circle.getRadius()*2, circle.getRadius()*2);
	}

	@Override
	public void visit(FilledCircle filledCircle) {
		g2d.setColor(filledCircle.getBgColor());
		g2d.fillOval(filledCircle.getCenter().x-filledCircle.getRadius(), filledCircle.getCenter().y-filledCircle.getRadius(), filledCircle.getRadius()*2, filledCircle.getRadius()*2);
		g2d.setColor(filledCircle.getFgColor());
		g2d.drawOval(filledCircle.getCenter().x-filledCircle.getRadius(), filledCircle.getCenter().y-filledCircle.getRadius(), filledCircle.getRadius()*2, filledCircle.getRadius()*2);
		
	}

}
