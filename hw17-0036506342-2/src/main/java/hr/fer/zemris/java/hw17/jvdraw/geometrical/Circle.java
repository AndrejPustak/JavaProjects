package hr.fer.zemris.java.hw17.jvdraw.geometrical;

import java.awt.Color;
import java.awt.Point;

/**
 * This class repesents a Circle
 * @author Andrej
 *
 */
public class Circle extends GeometricalObject {
	
	/**
	 * Center of the circle
	 */
	private Point center;
	/**
	 * Radius of the circle
	 */
	private int radius;
	/**
	 * Color of the circle
	 */
	private Color color;
	
	/**
	 * COnstructor for the circle
	 * @param startPoint staring point (center)
	 * @param endPoint end point (one outer point)
	 * @param color color of the circle
	 */
	public Circle(Point startPoint, Point endPoint, Color color) {
		radius = (int) startPoint.distance(endPoint);
		this.color = color;
		this.center = startPoint;
	}

	/**
	 * Constructor for the circle
	 * @param center center of the circle
	 * @param radius radius of the circle
	 * @param color color of the circle
	 */
	public Circle(Point center, int radius, Color color) {
		super();
		this.center = center;
		this.radius = radius;
		this.color = color;
	}



	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new CircleEditor(this);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Circle ");
		sb.append("(" + center.x + "," + center.y + ")");
		sb.append(", ");
		sb.append(radius);
		
		return sb.toString();
	}


	/**
	 * Getter for the center of the circle
	 * @return
	 */
	public Point getCenter() {
		return center;
	}

	/**
	 * Getter for the radius of the circle
	 * @return
	 */
	public int getRadius() {
		return radius;
	}


	/**
	 * Getter for the color of the cirlce
	 * @return
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Setter for the center of the circle
	 * @param center
	 */
	public void setCenter(Point center) {
		this.center = center;
		fire(this);
	}

	/**
	 * Setter for the radius of the circle
	 * @param radius
	 */
	public void setRadius(int radius) {
		this.radius = radius;
		fire(this);
	}

	/**
	 * Setter for the color of circle
	 * @param color
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
