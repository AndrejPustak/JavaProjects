package hr.fer.zemris.java.hw17.jvdraw.geometrical;

import java.awt.Color;
import java.awt.Point;

/**
 * This class represents a line
 * @author Andrej
 *
 */
public class Line extends GeometricalObject{
	
	/**
	 * Staring point
	 */
	private Point startPoint;
	
	/**
	 * Ending point
	 */
	private Point endPoint;
	
	/**
	 * Color of the line
	 */
	private Color color;

	/**
	 * Constructor for the line
	 * @param startPoint staring point
	 * @param endPoint ending point
	 * @param color color of the line
	 */
	public Line(Point startPoint, Point endPoint, Color color) {
		super();
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		this.color = color;
	}



	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new LineEditor(this);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Line ");
		sb.append("(" + startPoint.x + "," + startPoint.y + ")");
		sb.append("-");
		sb.append("(" + endPoint.x + "," + endPoint.y + ")");
		
		return sb.toString();
	}
	
	/**
	 * Getter for the staring point
	 * @return
	 */
	public Point getStartPoint() {
		return startPoint;
	}
	
	/**
	 * Getter for the ending point
	 * @return ending point
	 */
	public Point getEndPoint() {
		return endPoint;
	}
	
	/**
	 * Getter for the color
	 * @return getter for the color
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * Setter for the staring point
	 * @param startPoint staring point
	 */
	public void setStartPoint(Point startPoint) {
		this.startPoint = startPoint;
		fire(this);
	}

	/**
	 * Setter for the ending point
	 * @param endPoint ending point
	 */
	public void setEndPoint(Point endPoint) {
		this.endPoint = endPoint;
		fire(this);
	}

	/**
	 * Setter for the color of the line
	 * @param color color
	 */
	public void setColor(Color color) {
		this.color = color;
		fire(this);
	}
	
	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

}
