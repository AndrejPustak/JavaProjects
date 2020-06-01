package hr.fer.zemris.java.hw02;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ComplexNumberTest {
	
	@Test
	public void constructorTest() {
		ComplexNumber num = new ComplexNumber(-2.71, -3.15);
		assertEquals(-2.71, num.getReal());
		assertEquals(-3.15, num.getImaginary());
	}
	
	@Test
	public void fromRealTest() {
		ComplexNumber num = ComplexNumber.fromReal(2.1);
		assertEquals(2.1, num.getReal());
		assertEquals(0, num.getImaginary());
	}
	
	@Test
	public void fromImaginaryTest() {
		ComplexNumber num = ComplexNumber.fromImaginary(2.1);
		assertEquals(0, num.getReal());
		assertEquals(2.1, num.getImaginary());
	}
	
	@Test
	public void formMagnitudeAndAngleTest() {
		ComplexNumber num = ComplexNumber.fromMagnitudeAndAngle(1, Math.PI/2);
		assertEquals(0, num.getReal());
		assertEquals(1, num.getImaginary());
		ComplexNumber num2 = ComplexNumber.fromMagnitudeAndAngle(1, Math.PI/3);
		ComplexNumber num3 = new ComplexNumber(1.0/2, Math.sqrt(3)/2);
		assertEquals(true, num2.equals(num3));
		num2 = ComplexNumber.fromMagnitudeAndAngle(1, 4 * Math.PI/3);
		num3 = new ComplexNumber(-1.0/2, -Math.sqrt(3)/2);
		assertEquals(true, num2.equals(num3));
	}
	@Test
	public void parseTest() {
		ComplexNumber num1 = ComplexNumber.parse("3.51");
		assertEquals(true, num1.equals(new ComplexNumber(3.51, 0)));
		ComplexNumber num2 = ComplexNumber.parse("-3.51");
		assertEquals(true, num2.equals(new ComplexNumber(-3.51, 0)));
		ComplexNumber num3 = ComplexNumber.parse("3.51i");
		assertEquals(true, num3.equals(new ComplexNumber(0, 3.51)));
		ComplexNumber num4 = ComplexNumber.parse("-3.51i");
		assertEquals(true, num4.equals(new ComplexNumber(0, -3.51)));
		ComplexNumber num5 = ComplexNumber.parse("-2.71-3.15i");
		assertEquals(true, num5.equals(new ComplexNumber(-2.71, -3.15)));
		ComplexNumber num6 = ComplexNumber.parse("i");
		assertEquals(true, num6.equals(new ComplexNumber(0, 1)));
		
		ComplexNumber num7 = ComplexNumber.parse("-1-i");
		assertEquals(true, num7.equals(new ComplexNumber(-1, -1)));
		ComplexNumber num8 = ComplexNumber.parse("1+i");
		assertEquals(true, num8.equals(new ComplexNumber(1, 1)));
		
	}
	
	@Test
	public void addTest() {
		ComplexNumber num1 = new ComplexNumber(-2.71, 3.15);
		ComplexNumber num2 = new ComplexNumber(2.71, -3.15);
		ComplexNumber num3 = num1.add(num2);
		assertEquals(0, num3.getReal());
		assertEquals(0, num3.getImaginary());
	}
	
	@Test
	public void subTest() {
		ComplexNumber num1 = new ComplexNumber(-2.71, -3.15);
		ComplexNumber num2 = new ComplexNumber(-2.71, -3.15);
		ComplexNumber num3 = num1.sub(num2);
		assertEquals(0, num3.getReal());
		assertEquals(0, num3.getImaginary());
	}
	
	@Test
	public void mulTest() {
		ComplexNumber num1 = new ComplexNumber(2, 3);
		ComplexNumber num2 = new ComplexNumber(-3, 5);
		ComplexNumber num3 = num1.mul(num2);
		assertEquals(-21, num3.getReal());
		assertEquals(1, num3.getImaginary());
	}
	
	@Test
	public void divTest() {
		ComplexNumber num1 = new ComplexNumber(2, 3);
		ComplexNumber num2 = new ComplexNumber(-3, 5);
		ComplexNumber num3 = num1.div(num2);
		assertEquals(9.0/34, num3.getReal());
		assertEquals(-19.0/34, num3.getImaginary());
	}
	
	@Test
	public void powerTest() {
		ComplexNumber num1 = new ComplexNumber(2, 3);
		ComplexNumber num2 = num1.power(3);
		assertEquals(-46, num2.getReal());
		assertEquals(9, num2.getImaginary());
	}
	
	@Test
	public void rootTest() {
		ComplexNumber num1 = new ComplexNumber(2, 3);
		ComplexNumber[] num2 = num1.root(3);
		assertEquals(true, num2[0].equals(new ComplexNumber(1.4518566, 0.4934035)));
		assertEquals(true, num2[2].equals(new ComplexNumber(-0.298628, -1.5040465)));
	}
}
