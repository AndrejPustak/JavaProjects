package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class Vector2DTest {
	
	@Test
	public void constructorTest() {
		Vector2D vec = new Vector2D(5, 10);
		
		assertEquals(5, vec.getX());
		assertEquals(10, vec.getY());
	}
	
	@Test
	public void translateTest() {
		Vector2D vec = new Vector2D(5, 10);
		Vector2D vec2 = new Vector2D(5, 10);
		
		vec.translate(vec2);
		
		assertEquals(10, vec.getX());
		assertEquals(20, vec.getY());
	}
	
	@Test
	public void translatedTest() {
		Vector2D vec = new Vector2D(5, 10);
		Vector2D vec2 = new Vector2D(5, 10);
		
		Vector2D vec3 = vec.translated(vec2);
		
		assertEquals(10, vec3.getX());
		assertEquals(20, vec3.getY());
	}
	
	@Test
	public void rotateTest() {
		Vector2D vec = new Vector2D(1, 0);
		
		vec.rotate(Math.PI / 2.0);
		
		assertEquals(0, vec.getX());
		assertEquals(1, vec.getY());
		
		vec.rotate(-Math.PI);
		
		assertEquals(0, vec.getX());
		assertEquals(-1, vec.getY());
	}
	
	@Test
	public void rotatedTest() {
		Vector2D vec = new Vector2D(1, 0);
		
		Vector2D vec2 = vec.rotated(Math.PI / 2.0);
		
		assertEquals(0, vec2.getX());
		assertEquals(1, vec2.getY());
		
		Vector2D vec3 = vec2.rotated(-Math.PI);
		
		assertEquals(0, vec3.getX());
		assertEquals(-1, vec3.getY());
	}
	
	@Test
	public void scaleTest() {
		Vector2D vec = new Vector2D(5, 10);
		
		vec.scale(2);
		
		assertEquals(10, vec.getX());
		assertEquals(20, vec.getY());
	}
	
	@Test
	public void scaledTest() {
		Vector2D vec = new Vector2D(5, 10);
		
		Vector2D vec2 = vec.scaled(2);
		
		assertEquals(10, vec2.getX());
		assertEquals(20, vec2.getY());
	}
	
	@Test
	public void copyTest() {
		Vector2D vec = new Vector2D(5, 10);
		
		Vector2D vec2 = vec.copy();
		
		assertEquals(5, vec2.getX());
		assertEquals(10, vec2.getY());
	}
}
