package hr.fer.zemris.math;

import java.util.Objects;

/**
 * This class represents a vector in 2 dimensions
 * It stores the x and y value of the vector
 * @author Andrej
 *
 */

public class Vector2D {
	private double x;
	private double y;
	
	/**
	 * Constructor for the vector2D class
	 * @param x	X value of the vector
	 * @param y Y value of the vector
	 */
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Getter for the x value of the vector
	 * @return X value of the vecor
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * Getter for the y value of the vector
	 * @return Y value of the vector
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * This method translates the vector by a given vector
	 * @param offset Vector by which you wish to translate this vector
	 */
	public void translate(Vector2D offset) {
		Objects.requireNonNull(offset);
		
		x = x + offset.getX();
		y = y + offset.getY();
		
		x = Math.round(x * 1000000.0) / 1000000.0;
		y = Math.round(y * 1000000.0) / 1000000.0;
	}
	
	/**
	 * This method translates this vector by a given vector and returns the result
	 * Does not change the value of this vector
	 * @param offset Vector by which you wish to translate this vector
	 * @return New vector which is the result of translating this vector by the given vector
	 */
	public Vector2D translated(Vector2D offset) {
		return new Vector2D(x + offset.getX(), y + offset.getY());
	}
	
	/**
	 * This method rotates this vector by a given angle
	 * @param angle Angle by which you wish to rotate this vector
	 */
	public void rotate(double angle) {
		double len = Math.sqrt(x*x + y*y);
		double newAngle = Math.atan2(y, x);
		
		newAngle += angle;
		
		x = len * Math.cos(newAngle);
		y = len * Math.sin(newAngle);
		
		x = Math.round(x * 1000000.0) / 1000000.0;
		y = Math.round(y * 1000000.0) / 1000000.0;
	}
	
	/**
	 * This method rotates this vector by a given angle and returns the result
	 * Does not change the value of this vector
	 * @param angle Angle by which you wish to rotate this vector
	 * @return New vector which is the result of rotating this vector by a given angle
	 */
	public Vector2D rotated(double angle) {
		double len = Math.sqrt(x*x + y*y);
		double newAngle = Math.atan2(y, x);
		
		newAngle += angle;
		
		double newX = len * Math.cos(newAngle);
		double newY = len * Math.sin(newAngle);
		
		newX = Math.round(newX * 1000000.0) / 1000000.0;
		newY = Math.round(newY * 1000000.0) / 1000000.0;
		
		return new Vector2D(newX, newY);
	}
	
	/**
	 * This method scales this vector by the given value
	 * @param scaler Value by which you wish to scale this vector
	 */
	public void scale(double scaler) {
		double len = Math.sqrt(x*x + y*y);
		double angle = Math.atan2(y, x);
		
		len *= scaler;
		
		x = len * Math.cos(angle);
		y = len * Math.sin(angle);
		
		x = Math.round(x * 1000000.0) / 1000000.0;
		y = Math.round(y * 1000000.0) / 1000000.0;
	}
	
	/**
	 * This method scales this vector by the given value and returns the result
	 * Does not change the value of this vector
	 * @param scaler Value by which you wish to scale this vector
	 * @return New vector which is the result of scaling this vector by the given value
	 */
	public Vector2D scaled(double scaler) {
		double len = Math.sqrt(x*x + y*y);
		double angle = Math.atan2(y, x);
		
		len *= scaler;
		
		double newX = len * Math.cos(angle);
		double newY = len * Math.sin(angle);
		
		newX = Math.round(newX * 1000000.0) / 1000000.0;
		newY = Math.round(newY * 1000000.0) / 1000000.0;
		
		return new Vector2D(newX, newY);
	}
	
	/**
	 * This method returns a copy of this vector
	 * @return New vector which is the copy of this vector
	 */
	public Vector2D copy() {
		return new Vector2D(x, y);
	}
}
