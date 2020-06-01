package hr.fer.zemris.java.hw02;

import java.util.Objects;

/**
 * Class ComplexNumber is an implementation of complex numbers in java. 
 * @author Andrej
 *
 */
public class ComplexNumber {
	
	private final static double DECIMAL_ROUNDING = 1e-6;
	
	private double real;
	private double imaginary;
	
	/**
	 * The only constructor for the class.
	 * 
	 * @param real Real value of the complex number.
	 * @param imaginary Imaginary value of the complex number.
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}
	
	/**
	 * This is a static method which creates and returns a new complex number from just it's
	 * real value. The imaginary value is set to 0.0.
	 * @param real Real value of the complex number.
	 * @return The new complex number created in the method with just real value. 
	 */
	public static ComplexNumber fromReal(double real) {
		ComplexNumber num = new ComplexNumber(real, 0.0);
		return num;
	}
	
	/**
	 * This is a static method which creates and returns a new complex number from just it's
	 * imaginary value. The real value is set to 0.0.
	 * @param imaginary Real value of the complex number.
	 * @return The new complex number created in the method with just imaginary value. 
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		ComplexNumber num = new ComplexNumber(0.0, imaginary);
		return num;
	}
	
	/**
	 * This is a static method which creates and returns a new complex number form it's
	 * magnitude and angle.
	 * 
	 * @param magnitude The magnitude of the complex number.
	 * @param angle The angle of the complex number.
	 * @return New complex number with given magnitude and angle.
 	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		double r = magnitude * Math.cos(angle);
		double i = magnitude * Math.sin(angle);
		r = Math.round(r * 1000000.0) / 1000000.0;
		i = Math.round(i * 1000000.0) / 1000000.0;
		ComplexNumber num = new ComplexNumber(r, i);
		return num;
	}
	
	/**
	 * This method is used to parse a new complex number from a string.
	 * The method does not support multiple signs in a row and 'i' before the number.
	 * 
	 * Example of the supported strings:  "351", "-3.17", "3.51i", "-2.71-3.15i".
	 * @param s String you wish to parse to complex number.
	 * @return New complex number created from the parsed string.
	 */
	public static ComplexNumber parse(String s) {
		Objects.requireNonNull(s);
		
		double real = 0.0;
		double imaginary = 0.0;
		char[] array = s.trim().toCharArray();
		boolean isImaginary = false;
		
		String num = "";
		int i = 0;
		if(array[0] == '-'  || array[0] == '+') {
			num += array[0];
			i++;
		}
		
		while (i < array.length && array[i] != '-' && array[i] != '+' && array[i] != 'i') {
			num += array[i];
			i++;
		}
		
		if (i != array.length && array[i] == 'i') {
			if(num.equals("") || num.equals("+") || num.equals("-")) {
				num += '1';
			}
			
			imaginary = Double.parseDouble(num);
			isImaginary = true;
		}
		
		if(isImaginary && i + 1 < array.length) {
			throw new NumberFormatException();
		}
		
		if(!isImaginary && i != array.length) {
			real = Double.parseDouble(num);
			
			if(array[array.length-1] != 'i') {
				throw new NumberFormatException();
			}
			
			num = "";
			num += array[i];
			i++;
			
			while (i < array.length && array[i] != '-' && array[i] != '+' && array[i] != 'i') {
				num += array[i];
				i++;
			}
			
			if(num.equals("-") || num.equals("+")) {
				num += 1;
			}
			
			imaginary = Double.parseDouble(num);
		} else if (i == array.length) {
			real = Double.parseDouble(num);
		}
		
		return new ComplexNumber(real, imaginary);
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
	 * This is the getter for he magnitude of the complex number.
	 * @return Magnitude of the complex number.
	 */
	public double getMagnitude() {
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
	public ComplexNumber add(ComplexNumber c) {
		ComplexNumber num = new ComplexNumber(this.real + c.getReal(), this.imaginary + c.getImaginary());
		return num;
	}
	
	/**
	 * This method subtracts the given complex number from this number and
	 * returns the result.
	 * @param c The number you wish to subtract from this number.
	 * @return The result of the subtraction.
	 */
	public ComplexNumber sub(ComplexNumber c) {
		return new ComplexNumber(this.real - c.getReal(), this.imaginary - c.getImaginary());
	}
	
	/**
	 * This method is multiplies this complex number and the given complex number and
	 * returns the result.
	 * @param c The complex number you wish to multiply with this number.
	 * @return The product of multiplication.
	 */
	public ComplexNumber mul(ComplexNumber c) {
		return new ComplexNumber(this.real * c.getReal() - this.imaginary * c.getImaginary(), 
											  this.real * c.getImaginary() + this.imaginary * c.getReal());
	}
	
	/**
	 * This method divides this complex number with the given number.
	 * @param c Complex number with which you wish to divide this complex number.
	 * @return The result of the division.
	 */
	public ComplexNumber div(ComplexNumber c) {
		double r = (this.real * c.getReal() + this.imaginary * c.getImaginary())/(c.getReal()*c.getReal()+c.getImaginary()*c.getImaginary());
		double i = (this.imaginary * c.getReal() -  this.real * c.getImaginary())/(c.getReal()*c.getReal()+c.getImaginary()*c.getImaginary());
		return new ComplexNumber(r, i);
	}
	
	/**
	 * This method calculates this complex number to the power of the given number
	 * @param n Integer
	 * @return The result of calculating this complex number to the power of n.
	 */
	public ComplexNumber power(int n) {
		if(n<0) {
			throw new IllegalArgumentException("n is less than 0");
		}
		return fromMagnitudeAndAngle(Math.pow(getMagnitude(), n), n * getAngle());
	}
	
	/**
	 * This method calculates nth root of a complex number.
	 * @param n Integer
	 * @return The nth roots of this complex number in the form of an array.
	 */
	public ComplexNumber[] root(int n) {
		if(n<=0) {
			throw new IllegalArgumentException("n is less or equal to 0");
		}
		
		ComplexNumber[] array = new ComplexNumber[n];
		
		for (int i = 0; i < n; i++) {
			array[i] = fromMagnitudeAndAngle(Math.pow(getMagnitude(), 1.0/n), (getAngle() + 2*i*Math.PI)/n);
		}
		
		return array;
	}
	
	

	@Override
	public String toString() {
		String num = "";
		if(Math.abs(real - 0) > 1e-6) {
			num += real;
		}
		if(Math.abs(imaginary - 0) > 1e-6) {
			if (imaginary > 0) {
				num += "+" + imaginary +"i";
			}
			else {
				num += imaginary +"i";
			}
		}
		if(Math.abs(real - 0) < DECIMAL_ROUNDING && Math.abs(imaginary - 0) < DECIMAL_ROUNDING) {
			num += "0.0";
		}
		
		return num;
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
		if (!(obj instanceof ComplexNumber))
			return false;
		ComplexNumber other = (ComplexNumber) obj;
		return Math.abs(real - other.getReal()) < DECIMAL_ROUNDING && Math.abs(imaginary - other.getImaginary()) < DECIMAL_ROUNDING;
	}
	
}
