package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Vector3Test {
	
	@Test
	public void normTest() {
		Vector3 vec = new Vector3(1, 2, -3);
		
		assertEquals(Math.sqrt(14), vec.norm());
	}
	
	@Test
	public void normalizedTest() {
		Vector3 vec = new Vector3(1, 2, -3);
		
		assertEquals(new Vector3(Math.sqrt(14)/14, Math.sqrt((2.0/7)),-3 / Math.sqrt(14)), vec.normalized());
	}
	
	@Test
	public void dotTest() {
		Vector3 vec1 = new Vector3(1, 2, -3);
		Vector3 vec2 = new Vector3(2, -2, 4);
		
		assertEquals(-14, vec1.dot(vec2));
	}
	
	@Test
	public void cosAngleTest() {
		Vector3 vec1 = new Vector3(1, 2, -3);
		Vector3 vec2 = new Vector3(2, -2, 4);
		
		assertEquals(-Math.sqrt(7.0/3)/2, vec1.cosAngle(vec2));
	}
	
	@Test
	public void crossTest() {
		Vector3 vec1 = new Vector3(1, 2, -3);
		Vector3 vec2 = new Vector3(2, -2, 4);
		
		assertEquals(new Vector3(2, -10, -6), vec1.cross(vec2));
	}
}
