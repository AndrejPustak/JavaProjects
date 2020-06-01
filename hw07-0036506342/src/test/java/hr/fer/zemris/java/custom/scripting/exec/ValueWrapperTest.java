package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ValueWrapperTest {
	
	@Test
	void addIntegerTest() {
		Integer v1 = Integer.valueOf(2);
		Integer v2 = Integer.valueOf(3);
		
		ValueWrapper wrapper = new ValueWrapper(v1);
		assertEquals(Integer.valueOf(2), wrapper.getValue());
		wrapper.add(v2);
		
		assertEquals(true, wrapper.getValue() instanceof Integer);
		assertEquals(Integer.valueOf(5), wrapper.getValue());
	}
	
	@Test
	void addDoubleTest() {
		Double v1 = Double.valueOf(2);
		Double v2 = Double.valueOf(3.5);
		
		ValueWrapper wrapper = new ValueWrapper(v1);
		assertEquals(Double.valueOf(2), wrapper.getValue());
		wrapper.add(v2);
		
		assertEquals(true, wrapper.getValue() instanceof Double);
		assertEquals(Double.valueOf(5.5), wrapper.getValue());
	}
	
	@Test
	void addIntegerDoubleTest() {
		Integer v1 = Integer.valueOf(2);
		Double v2 = Double.valueOf(3.5);
		
		ValueWrapper wrapper = new ValueWrapper(v1);
		assertEquals(Integer.valueOf(2), wrapper.getValue());
		wrapper.add(v2);
		
		assertEquals(true, wrapper.getValue() instanceof Double);
		assertEquals(Double.valueOf(5.5), wrapper.getValue());
	}
	
	@Test
	void addStringTest() {
		String v1 = "2";
		String v2 = "3.5";
		
		ValueWrapper wrapper = new ValueWrapper(v1);
		wrapper.add(v2);
		
		assertEquals(true, wrapper.getValue() instanceof Double);
		assertEquals(Double.valueOf(5.5), wrapper.getValue());
	}
	
	@Test
	void subIntegerTest() {
		Integer v1 = Integer.valueOf(3);
		Integer v2 = Integer.valueOf(2);
		
		ValueWrapper wrapper = new ValueWrapper(v1);
		assertEquals(Integer.valueOf(3), wrapper.getValue());
		wrapper.subtract(v2);
		
		assertEquals(true, wrapper.getValue() instanceof Integer);
		assertEquals(Integer.valueOf(1), wrapper.getValue());
	}
	
	@Test
	void subDoubleTest() {
		Double v1 = Double.valueOf(3.5);
		Double v2 = Double.valueOf(2);
		
		ValueWrapper wrapper = new ValueWrapper(v1);
		assertEquals(Double.valueOf(3.5), wrapper.getValue());
		wrapper.subtract(v2);
		
		assertEquals(true, wrapper.getValue() instanceof Double);
		assertEquals(Double.valueOf(1.5), wrapper.getValue());
	}
	
	@Test
	void subIntegerDoubleTest() {
		Integer v1 = Integer.valueOf(3);
		Double v2 = Double.valueOf(2.5);
		
		ValueWrapper wrapper = new ValueWrapper(v1);
		assertEquals(Integer.valueOf(3), wrapper.getValue());
		wrapper.subtract(v2);
		
		assertEquals(true, wrapper.getValue() instanceof Double);
		assertEquals(Double.valueOf(0.5), wrapper.getValue());
	}
	
	@Test
	void subStringTest() {
		String v1 = "2";
		String v2 = "3.5";
		
		ValueWrapper wrapper = new ValueWrapper(v1);
		wrapper.subtract(v2);
		
		assertEquals(true, wrapper.getValue() instanceof Double);
		assertEquals(Double.valueOf(-1.5), wrapper.getValue());
	}
	
	@Test
	void mulIntegerTest() {
		Integer v1 = Integer.valueOf(2);
		Integer v2 = Integer.valueOf(3);
		
		ValueWrapper wrapper = new ValueWrapper(v1);
		assertEquals(Integer.valueOf(2), wrapper.getValue());
		wrapper.multiply(v2);
		
		assertEquals(true, wrapper.getValue() instanceof Integer);
		assertEquals(Integer.valueOf(6), wrapper.getValue());
	}
	
	@Test
	void mulDoubleTest() {
		Double v1 = Double.valueOf(2);
		Double v2 = Double.valueOf(3.5);
		
		ValueWrapper wrapper = new ValueWrapper(v1);
		assertEquals(Double.valueOf(2), wrapper.getValue());
		wrapper.multiply(v2);
		
		assertEquals(true, wrapper.getValue() instanceof Double);
		assertEquals(Double.valueOf(7.0), wrapper.getValue());
	}
	
	@Test
	void mulIntegerDoubleTest() {
		Integer v1 = Integer.valueOf(2);
		Double v2 = Double.valueOf(3.5);
		
		ValueWrapper wrapper = new ValueWrapper(v1);
		assertEquals(Integer.valueOf(2), wrapper.getValue());
		wrapper.multiply(v2);
		
		assertEquals(true, wrapper.getValue() instanceof Double);
		assertEquals(Double.valueOf(7.0), wrapper.getValue());
	}
	
	@Test
	void mulStringTest() {
		String v1 = "2";
		String v2 = "3.5";
		
		ValueWrapper wrapper = new ValueWrapper(v1);
		wrapper.multiply(v2);
		
		assertEquals(true, wrapper.getValue() instanceof Double);
		assertEquals(Double.valueOf(7.0), wrapper.getValue());
	}
	
	@Test
	void divIntegerTest() {
		Integer v1 = Integer.valueOf(3);
		Integer v2 = Integer.valueOf(2);
		
		ValueWrapper wrapper = new ValueWrapper(v1);
		assertEquals(Integer.valueOf(3), wrapper.getValue());
		wrapper.divide(v2);
		
		assertEquals(true, wrapper.getValue() instanceof Integer);
		assertEquals(Integer.valueOf(1), wrapper.getValue());
	}
	
	@Test
	void divDoubleTest() {
		Double v1 = Double.valueOf(7);
		Double v2 = Double.valueOf(3.5);
		
		ValueWrapper wrapper = new ValueWrapper(v1);
		assertEquals(Double.valueOf(7), wrapper.getValue());
		wrapper.divide(v2);
		
		assertEquals(true, wrapper.getValue() instanceof Double);
		assertEquals(Double.valueOf(2.0), wrapper.getValue());
	}
	
	@Test
	void divIntegerDoubleTest() {
		Integer v1 = Integer.valueOf(7);
		Double v2 = Double.valueOf(3.5);
		
		ValueWrapper wrapper = new ValueWrapper(v1);
		assertEquals(Integer.valueOf(7), wrapper.getValue());
		wrapper.divide(v2);
		
		assertEquals(true, wrapper.getValue() instanceof Double);
		assertEquals(Double.valueOf(2.0), wrapper.getValue());
	}
	
	@Test
	void divStringTest() {
		String v1 = "7";
		String v2 = "3.5";
		
		ValueWrapper wrapper = new ValueWrapper(v1);
		wrapper.divide(v2);
		
		assertEquals(true, wrapper.getValue() instanceof Double);
		assertEquals(Double.valueOf(2.0), wrapper.getValue());
	}
	
	@Test
	void compareTest() {
		Integer v1 = Integer.valueOf(3);
		Integer v2 = Integer.valueOf(2);
		ValueWrapper wrapper = new ValueWrapper(v1);
		assertEquals(1, wrapper.numCompare(v2));
		
		Double v3 = Double.valueOf(3.2);
		assertEquals(-1, wrapper.numCompare(v3));
		
	}
	
	@Test
	void demoTest() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		v1.add(v2.getValue());
		assertEquals(0, v1.getValue());
		assertEquals(null, v2.getValue());
		
		ValueWrapper v3 = new ValueWrapper("1.2E1");
		ValueWrapper v4 = new ValueWrapper(Integer.valueOf(1));
		v3.add(v4.getValue());
		assertEquals(Double.valueOf(13), v3.getValue());
		assertEquals(Integer.valueOf(1), v4.getValue());
		
		ValueWrapper v5 = new ValueWrapper("12");
		ValueWrapper v6 = new ValueWrapper(Integer.valueOf(1));
		v5.add(v6.getValue());
		assertEquals(Integer.valueOf(13), v5.getValue());
		assertEquals(Integer.valueOf(1), v6.getValue());
		
		ValueWrapper v7 = new ValueWrapper("Ankica");
		ValueWrapper v8 = new ValueWrapper(Integer.valueOf(1));
		
		assertThrows(RuntimeException.class, () -> v7.add(v8.getValue()));
	}
}
