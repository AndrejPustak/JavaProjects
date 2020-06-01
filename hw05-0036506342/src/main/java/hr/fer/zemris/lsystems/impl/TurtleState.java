package hr.fer.zemris.lsystems.impl;

import java.awt.Color;
import java.util.Objects;

import hr.fer.zemris.math.Vector2D;

/**
 * Object of this class represents a single state of the turtle
 * @author Andrej
 *
 */
public class TurtleState {
	
	private Vector2D positionVector;
	private Vector2D angleVector;
	private Color color;
	private double effectiveLength;
	
	/**
	 * Constructor for the TurtleState
	 * @param positionVector position vector of the turtle
	 * @param angleVector angle vector of the turtle
	 * @param color color of the turtle
	 * @param effectiveLength effective length of the turtle 
 	 */
	public TurtleState(Vector2D positionVector, Vector2D angleVector, Color color, double effectiveLength) {
		this.positionVector = Objects.requireNonNull(positionVector);
		this.angleVector = Objects.requireNonNull(angleVector);
		this.color = Objects.requireNonNull(color);
		this.effectiveLength = effectiveLength;
	}
	
	/**
	 * This method returns the copy of the turtle state
	 * @return
	 */
	public TurtleState copy() {
		return new TurtleState(positionVector.copy(), angleVector.copy(), new Color(color.getRGB()), effectiveLength);
	}
	
	/**
	 * Getter for the position vector
	 * @return position vector of the turtle
	 */
	public Vector2D getPositionVector() {
		return positionVector;
	}
	
	/**
	 * Setter for the position vector
	 * @param positionVector position vector of the turtle
	 */
	public void setPositionVector(Vector2D positionVector) {
		this.positionVector = positionVector;
	}
	
	/**
	 * Getter for the angle vector
	 * @return position vector of the turtle
	 */
	public Vector2D getAngleVector() {
		return angleVector;
	}
	
	/**
	 * Setter for the angle vector of the turtle
	 * @param angleVector angle vector of the turtle
	 */
	public void setAngleVector(Vector2D angleVector) {
		this.angleVector = angleVector;
	}
	
	/**
	 * Getter for the color of the turtle
	 * @return color of the turlte
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Setter for the color of the turtle
	 * @param color color of the turtle
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Getter for the effective length of the turtle
	 * @return effective length of the turtle
	 */
	public double getEffectiveLength() {
		return effectiveLength;
	}

	/**
	 * Setter for the effective length of the turtle
	 * @param effectiveLength effective length of the turtle
	 */
	public void setEffectiveLength(double effectiveLength) {
		this.effectiveLength = effectiveLength;
	}
	
	
}
