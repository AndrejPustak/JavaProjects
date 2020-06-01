package hr.fer.zemris.java.hw17.jvdraw.geometrical;

import java.awt.Point;
import java.awt.Rectangle;

/**
 * Implementation of GeometricalObjectVisitor, used to calculate the bounding box
 * @author Andrej
 *
 */
public class GeometricalObjectBBCalculator implements GeometricalObjectVisitor{
	
	/**
	 * Top-left point
	 */
	private Point topLeft;
	/**
	 * Bottom-right point
	 */
	private Point bottomRight;
	
	public GeometricalObjectBBCalculator() {
	}

	@Override
	public void visit(Line line) {
		Point start = line.getStartPoint();
		Point end = line.getEndPoint();
		
		proccessBounds(start, end);
	}

	@Override
	public void visit(Circle circle) {
		Point start = new Point(circle.getCenter().x - circle.getRadius(), circle.getCenter().y - circle.getRadius());
		Point end = new Point(circle.getCenter().x + circle.getRadius(), circle.getCenter().y + circle.getRadius());
		
		proccessBounds(start, end);
	}

	@Override
	public void visit(FilledCircle filledCircle) {
		Point start = new Point(filledCircle.getCenter().x - filledCircle.getRadius(), filledCircle.getCenter().y - filledCircle.getRadius());
		Point end = new Point(filledCircle.getCenter().x + filledCircle.getRadius(), filledCircle.getCenter().y + filledCircle.getRadius());
		
		proccessBounds(start, end);
	}
	
	/**
	 * Getter for the bounding box
	 * @return
	 */
	public Rectangle getBoundingBox() {
		return new Rectangle(topLeft.x, topLeft.y, bottomRight.x - topLeft.x, bottomRight.y - topLeft.y);
	}
	
	/**
	 * Method used to process two points
	 * @param start staring point
	 * @param end end point
	 */
	private void proccessBounds(Point start, Point end) {
		int minX;
		int maxX;
		int minY;
		int maxY;
		if(start.x <= end.x) {
			minX = start.x;
			maxX = end.x;
		} else {
			minX = end.x;
			maxX = start.x;
		}
		
		if(start.y <= end.y) {
			minY = start.y;
			maxY = end.y;
		} else {
			minY = end.y;
			maxY = start.y;
		}
		
		if(topLeft == null || bottomRight == null) {
			topLeft = new Point(minX, minY);
			bottomRight = new Point(maxX, maxY);
		}
		
		if(minX < topLeft.x) {
			topLeft.x = minX;
		}
		if(maxX > bottomRight.x) {
			bottomRight.x = maxX;
		}
		if(minY < topLeft.y) {
			topLeft.y = minY;
		}
		if(maxY > bottomRight.y) {
			bottomRight.y = maxY;
		}
	}

}
