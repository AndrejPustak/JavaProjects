package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ComplexPolynomialTest {
	
	@Test
	public void orderTest() {
		Complex com1 = new Complex(2,3);
		Complex com2 = new Complex(3,4);
		Complex com3 = new Complex(4,5);
		
		ComplexPolynomial pol = new ComplexPolynomial(com1, com2, com3);
		
		assertEquals(2, pol.order());
	}
	
	@Test
	public void multiplyTest() {
		Complex com1 = new Complex(2,3);
		Complex com2 = new Complex(3,4);
		Complex com3 = new Complex(4,5);
		
		ComplexPolynomial pol1 = new ComplexPolynomial(com1, com2, com3);
		ComplexPolynomial pol2 = new ComplexPolynomial(com3, com2, com1);
		
		ComplexPolynomial result = pol1.multiply(pol2);
		
		assertEquals(new ComplexPolynomial(
				new Complex(-7, 22),
				new Complex(-14, 48),
				new Complex(-21, 76),
				new Complex(-14, 48),
				new Complex(-7,22)
				) , result);
	}
	
	@Test
	public void deriveTest() {
		Complex com1 = new Complex(2,3);
		Complex com2 = new Complex(3,4);
		Complex com3 = new Complex(4,5);
		
		ComplexPolynomial pol = new ComplexPolynomial(com1, com2, com3);
		
		ComplexPolynomial result = pol.derive();
		
		assertEquals(new ComplexPolynomial(
				new Complex(3, 4),
				new Complex(8, 10)
				) , result);
	}
	
	@Test
	public void applyTest() {
		Complex com1 = new Complex(2,3);
		Complex com2 = new Complex(3,4);
		Complex com3 = new Complex(4,5);
		
		ComplexPolynomial pol = new ComplexPolynomial(com1, com2, com3);
		
		Complex result = pol.apply(new Complex(2,3));
		
		assertEquals(new Complex(-84, 43) , result);
	}
	
}
