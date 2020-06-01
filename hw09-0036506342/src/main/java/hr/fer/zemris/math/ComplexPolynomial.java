package hr.fer.zemris.math;

import java.util.Arrays;

/**
 * This class represents a polynomial with complex factors
 * @author Andrej
 *
 */
public class ComplexPolynomial {
	
	private final Complex[] factors;
	
	/**
	 * Constructor for the ComplexPolynomial
	 * @param factors factors of the polynomial (index 0 if factor of z^0 and so on...)
	 */
	public ComplexPolynomial(Complex ... factors) {
		super();
		this.factors = factors;
	}
	
	/**
	 * This method returns the order of the polynomial
	 * @return order of the polynomial
	 */
	public short order() {
		return (short) (factors.length - 1);
	}
	
	/**
	 * This method multiplies this polynomial with the given polynomial
	 * and returns the result
	 * @param p polynomial with which you wish to multiply this polynomial
	 * @return polynomial which is the result of the multiplication
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		Complex[] factorsNew = new Complex[factors.length + p.getFactors().length - 1];
		for(int i = 0; i<factorsNew.length; i++) factorsNew[i] = Complex.ZERO;
		
		for(int i = 0; i < factors.length; i++) {
			for(int j = 0; j < p.getFactors().length; j++) {
				factorsNew[i+j] = factorsNew[i+j].add(factors[i].multiply(p.getFactors()[j]));
			}
		}
		
		return new ComplexPolynomial(factorsNew);
	}
	
	/**
	 * This method derives this polynomial and return the result
	 * @return derived polynomial
	 */
	public ComplexPolynomial derive() {
		Complex[] factorsNew = new Complex[factors.length - 1];
		for(int i = 0; i < factors.length - 1; i++) {
			factorsNew[i] = factors[i+1].multiply(new Complex(i+1, 0));
		}
		
		return new ComplexPolynomial(factorsNew);
	}
	
	/**
	 * THis method calculates the value of the polynomial for the given value of z
	 * @param z value for which you wish to calculate the value of the polynomial for
	 * @return value of the polynomial for the given value of z
	 */
	public Complex apply(Complex z) {
		Complex result = factors[0];
		
		for(int i = 1; i< factors.length; i++) {
			result = result.add(factors[i].multiply(z.power(i)));
		}
		
		return result;
		
	}
	
	/**
	 * This method return the factors of the polynomial
	 * @return factors of the polynomial
	 */
	public Complex[] getFactors() {
		return factors;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		int i = factors.length - 1;
		sb.append("("+factors[i--].toString()+")*z^"+(i+1));
		for(; i > 0; i--) {
			sb.append("+("+factors[i].toString()+")*z^"+i);
		}
		sb.append("+("+factors[i].toString()+")");
		
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(factors);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ComplexPolynomial))
			return false;
		ComplexPolynomial other = (ComplexPolynomial) obj;
		return Arrays.equals(factors, other.factors);
	}
	
	

	
}
