package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class ComplexNumber is an implementation of complex numbers in java. 
 * @author Andrej
 *
 */
public class Complex {
	
	private final static double DECIMAL_ROUNDING = 1e-9;
	
	private double real;
	private double imaginary;
	
	public static final Complex ZERO = new Complex(0,0);
	public static final Complex ONE = new Complex(1,0);
	public static final Complex ONE_NEG = new Complex(-1,0);
	public static final Complex IM = new Complex(0,1);
	public static final Complex IM_NEG = new Complex(0,-1);
	
	/**
	 * The only constructor for the class.
	 * 
	 * @param real Real value of the complex number.
	 * @param imaginary Imaginary value of the complex number.
	 */
	public Complex(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}
	
	/**
	 * This is a static method which creates and returns a new complex number form it's
	 * magnitude and angle.
	 * 
	 * @param magnitude The magnitude of the complex number.
	 * @param angle The angle of the complex number.
	 * @return New complex number with given magnitude and angle.
 	 */
	public static Complex fromMagnitudeAndAngle(double magnitude, double angle) {
		double r = magnitude * Math.cos(angle);
		double i = magnitude * Math.sin(angle);
		Complex num = new Complex(r, i);
		return num;
	}
	
	/**
	 * This is the getter for the real value.
	 * @return Real value of the complex number.
	 */
	public double getReal() {
		return real;
	}
	
	/**
	 * This is the getter for the imaginary value.
	 * @return Imaginary value of the complex number.
	 */
	public double getImaginary() {
		return imaginary;
	}
	
	/**
	 * This is the getter for the module of the complex number.
	 * @return module of the complex number.
	 */
	public double getModule() {
		return Math.sqrt(real*real + imaginary*imaginary);
	}
	
	/**
	 * This is the getter for the angle for the  angle of the complex number.
	 * It return the angle in the range of 0 to 2 Pi.
	 * @return Angle of the complex number in the range of 0 to 2 Pi.
	 */
	public double getAngle() {
		double ang = Math.atan2(imaginary, real);
		return ang >= 0 ? ang : 2*Math.PI + ang;
	}
	
	/**
	 * This method adds two complex numbers and returns the result.
	 * @param c Complex number you wish to add to this number.
	 * @return The result of the addition.
	 */
	public Complex add(Complex c) {
		Complex num = new Complex(this.real + c.getReal(), this.imaginary + c.getImaginary());
		return num;
	}
	
	/**
	 * This method subtracts the given complex number from this number and
	 * returns the result.
	 * @param c The number you wish to subtract from this number.
	 * @return The result of the subtraction.
	 */
	public Complex sub(Complex c) {
		return new Complex(this.real - c.getReal(), this.imaginary - c.getImaginary());
	}
	
	/**
	 * This method is multiplies this complex number and the given complex number and
	 * returns the result.
	 * @param c The complex number you wish to multiply with this number.
	 * @return The product of multiplication.
	 */
	public Complex multiply(Complex c) {
		return new Complex(this.real * c.getReal() - this.imaginary * c.getImaginary(), 
											  this.real * c.getImaginary() + this.imaginary * c.getReal());
	}
	
	/**
	 * This method divides this complex number with the given number.
	 * @param c Complex number with which you wish to divide this complex number.
	 * @return The result of the division.
	 */
	public Complex divide(Complex c) {
		double r = (this.real * c.getReal() + this.imaginary * c.getImaginary())/(c.getReal()*c.getReal()+c.getImaginary()*c.getImaginary());
		double i = (this.imaginary * c.getReal() -  this.real * c.getImaginary())/(c.getReal()*c.getReal()+c.getImaginary()*c.getImaginary());
		return new Complex(r, i);
	}
	
	/**
	 * This method calculates this complex number to the power of the given number
	 * @param n Integer
	 * @return The result of calculating this complex number to the power of n.
	 */
	public Complex power(int n) {
		if(n<0) {
			throw new IllegalArgumentException("n is less than 0");
		}
		return fromMagnitudeAndAngle(Math.pow(getModule(), n), n * getAngle());
	}
	
	/**
	 * This method calculates nth root of a complex number.
	 * @param n Integer
	 * @return The nth roots of this complex number in the form of an array.
	 */
	public List<Complex> root(int n) {
		if(n<=0) {
			throw new IllegalArgumentException("n is less or equal to 0");
		}
		
		List<Complex> list = new ArrayList<>();
		
		for (int i = 0; i < n; i++) {
			list.add(fromMagnitudeAndAngle(Math.pow(getModule(), 1.0/n), (getAngle() + 2*i*Math.PI)/n));
		}
		
		return list;
	}
	
	/**
	 * This method returns the copy of a complex number
	 * @return New complex number which is the copy of this complex number
	 */
	public Complex copy() {
		return new Complex(real, imaginary);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(real);
		if (imaginary < 0) sb.append("-i");
		else sb.append("+i");
		sb.append(Math.abs(imaginary));
		return sb.toString();
	}

	@Override
	public int hashCode() {
		return Objects.hash(imaginary, real);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Complex))
			return false;
		Complex other = (Complex) obj;
		return Math.abs(real - other.getReal()) < DECIMAL_ROUNDING && Math.abs(imaginary - other.getImaginary()) < DECIMAL_ROUNDING;
	}
	
}
