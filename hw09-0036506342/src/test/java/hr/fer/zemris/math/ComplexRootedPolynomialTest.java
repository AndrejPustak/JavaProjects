package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ComplexRootedPolynomialTest {
	
	@Test
	public void applyTest() {
		Complex com1 = new Complex(2,3);
		Complex com2 = new Complex(3,4);
		Complex com3 = new Complex(4,5);
		Complex com4 = new Complex(5,6);
		
		ComplexRootedPolynomial pol = new ComplexRootedPolynomial(com1, com2, com3, com4);
		
		Complex result = pol.apply(new Complex(2,3));
		
		assertEquals(new Complex(60, 12), result);
	}
	
	@Test
	public void toComplexPolynomialTest() {
		Complex com1 = new Complex(2,3);
		Complex com2 = new Complex(3,4);
		Complex com3 = new Complex(4,5);
		Complex com4 = new Complex(5,6);
		
		ComplexRootedPolynomial pol = new ComplexRootedPolynomial(com1, com2, com3, com4);
		
		ComplexPolynomial result = pol.toComplexPolynom();
		
		assertEquals(new ComplexPolynomial(
				new Complex(773,464),
				new Complex(-408,155),
				new Complex(21,-66),
				new Complex(2,3)
				)
				, result);
	}
	
	@Test
	public void indexOfClosestRootForTest() {
		Complex com1 = new Complex(1,0);
		Complex com2 = new Complex(0,1);
		Complex com3 = new Complex(-1,0);
		Complex com4 = new Complex(0,2);
		
		ComplexRootedPolynomial pol = new ComplexRootedPolynomial(Complex.ONE, com1, com2, com3, com4);
		
		assertEquals(1, pol.indexOfClosestRootFor(new Complex(0, 0.5), 1));
		assertEquals(0, pol.indexOfClosestRootFor(new Complex(0.5, 0), 1));
		assertEquals(3, pol.indexOfClosestRootFor(new Complex(0, 1.6), 1));
		assertEquals(-1, pol.indexOfClosestRootFor(new Complex(0, 0.5), 0.4));
	
	}
	
}
