package hr.fer.zemris.java.hw17.jvdraw.geometrical;

import java.awt.Color;
import java.awt.Point;

/**
 * This class represents a filled circle
 * @author Andrej
 *
 */
public class FilledCircle extends GeometricalObject {
	
	/**
	 * Center of the circle
	 */
	private Point center;
	/**
	 * Radius of the circle
	 */
	private int radius;
	/**
	 * fgColor of the cirlce
	 */
	private Color fgColor;
	/**
	 * bgColor of the circle
	 */
	private Color bgColor;
	
	/**
	 * Constructor for the FIlledCircle
	 * @param startPoint staring point(center)
	 * @param endPoint end point (one outer point)
	 * @param fgColor fgColor of the circle
	 * @param bgColor bgColor of the circle
	 */
	public FilledCircle(Point startPoint, Point endPoint, Color fgColor, Color bgColor) {
		radius = (int) startPoint.distance(endPoint);
		this.fgColor = fgColor;
		this.bgColor = bgColor;
		this.center = startPoint;
	}
	
	/**
	 * Constructor for the FilledCircle
	 * @param center center of the circle
	 * @param radius radius of the circle
	 * @param fgColor fgColor of the circle
	 * @param bgColor bgColor of the circle
	 */
	public FilledCircle(Point center, int radius, Color fgColor, Color bgColor) {
		super();
		this.center = center;
		this.radius = radius;
		this.fgColor = fgColor;
		this.bgColor = bgColor;
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new FilledCircleEditor(this);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Filled circle ");
		sb.append("(" + center.x + "," + center.y + ")");
		sb.append(", ");
		sb.append(radius);
		sb.append(", ");
		sb.append(String.format("#%02X%02X%02X", bgColor.getRed(), bgColor.getGreen(), bgColor.getBlue()));
		
		return sb.toString();
	}

	/**
	 * Getter for the center of the circle
	 * @return center of the circle
	 */
	public Point getCenter() {
		return center;
	}

	/**
	 * Getter for the radius of the circle
	 * @return radius
	 */
	public int getRadius() {
		return radius;
	}

	/**
	 * Getter for the fgColor
	 * @return fgColor
	 */
	public Color getFgColor() {
		return fgColor;
	}

	/**
	 * Getter for the bgColor
	 * @return bgColor
	 */
	public Color getBgColor() {
		return bgColor;
	}

	/**
	 * Setter for the center of the circle
	 * @param center center
	 */
	public void setCenter(Point center) {
		this.center = center;
		fire(this);
	}

	/**
	 * Setter for the radius of the circle
	 * @param radius radius of the circle
	 */
	public void setRadius(int radius) {
		this.radius = radius;
		fire(this);
	}

	/**
	 * Setter for the fgColor
	 * @param fgColor fgColor
	 */
	public void setFgColor(Color fgColor) {
		this.fgColor = fgColor;
		fire(this);
	}

	/**
	 * Setter for the bgColor
	 * @param bgColor bgColor
	 */
	public void setBgColor(Color bgColor) {
		this.bgColor = bgColor;
		fire(this);
	}
	
	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

}
