package hr.fer.zemris.math;

import java.util.Objects;

/**
 * Implementation of vector in 3D.
 * @author Andrej
 *
 */
public class Vector3 {
	
	private final double DECIMAL_ROUNDING = 1e-5;
	
	private final double x;
	private final double y;
	private final double z;
	
	/**
	 * Constructor of the Vector3
	 * @param x x coordinate of the vector
	 * @param y y coordinate of the vector
	 * @param z z coordinate of the vector
	 */
	public Vector3(double x, double y, double z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * This method returns the norm ("length") of the vector
	 * @return norm of the vector
	 */
	public double norm() {
		return Math.sqrt(x*x + y*y + z*z);
	}
	
	/**
	 * This method returns the normalized vector of this vector
	 * @return normalized vector of this vector
	 */
	public Vector3 normalized() {
		double newX = x / norm();
		double newY = y / norm();
		double newZ = z / norm();
		
		return rounded(newX, newY, newZ);
	}
	
	/**
	 * THis method adds the given vector to this vector and returns the result
	 * @param other vector you wish to add to this vector
	 * @return result of the addition
	 */
	public Vector3 add(Vector3 other) {
		double newX = x + other.getX();
		double newY = y + other.getY();
		double newZ = z + other.getZ();
		
		return rounded(newX, newY, newZ);
	}
	
	/**
	 * This method subtract the given vector from this vector and returns the result 
	 * @param other vector you wish to subtract from this vector
	 * @return result of the subtraction
	 */
	public Vector3 sub(Vector3 other) {
		double newX = x - other.getX();
		double newY = y - other.getY();
		double newZ = z - other.getZ();
		
		return rounded(newX, newY, newZ);
	}
	
	/**
	 * This method calculates and returns the dot product of two vectors
	 * @param other vector 
	 * @return result of the dot operation
	 */
	public double dot(Vector3 other) {
		return x*other.getX() + y*other.getY() + z*other.getZ();
	}
	
	/**
	 * This method calculates and returns the cross product of this vector
	 * and the given vector
	 * @param other vector you wish to calculate the cross product with
	 * @return result of the opration
	 */
	public Vector3 cross(Vector3 other) {
		double newX = (y*other.getZ() - z*other.getY());
		double newY = (z*other.getX() - x*other.getZ());
		double newZ = (x*other.getY() - y*other.getX());
		
		return rounded(newX, newY, newZ);
	}
	
	/**
	 * This method scales this vector with the given factor
	 * @param s factor by which you wish to scale this vector
	 * @return scaled vector
	 */
	public Vector3 scale(double s) {
		return rounded(x*s, y*s, z*s);
	}
	
	/**
	 * This method calculates the cos angle between this vector and the
	 * given vector
	 * @param other vector with who you wish to calculate the cos angle with
	 * @return cos of the angle between two vectors
	 */
	public double cosAngle(Vector3 other) {
		return this.dot(other) / (norm() * other.norm()); 
	}
	
	/**
	 * This method return the rounded version of the vector
	 * @param X x coordinate of the vector
	 * @param Y y coordinate of the vector
	 * @param Z z coordinate of the vector
	 * @return rounded vector
	 */
	private Vector3 rounded(double X, double Y, double Z) {
		double newX = Math.round(X * 1000000.0) / 1000000.0;
		double newY = Math.round(Y * 1000000.0) / 1000000.0;
		double newZ = Math.round(Z * 1000000.0) / 1000000.0;
		return new Vector3(newX, newY, newZ);
	}
	
	/**
	 * Getter for the x coordinate
	 * @return x coordinate of the vector
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * Getter for the y coordinate
	 * @return y coordinate of the vector
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * Getter for the z coordinate
	 * @return z coordinate of the vector
	 */
	public double getZ() {
		return z;
	}
	
	public double[] toArray() {
		double[] array = {x,y,z};
		return array;
	}
	
	@Override
	public String toString() {
		return String.format("(%.6f, %.6f, %.6f)", x, y, z);
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y, z);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Vector3))
			return false;
		Vector3 other = (Vector3) obj;
		return Math.abs(x - other.x) < DECIMAL_ROUNDING
				&& Math.abs(y - other.y) < DECIMAL_ROUNDING
				&& Math.abs(z - other.z) < DECIMAL_ROUNDING;
	}
	
	
}
