package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class DictionaryTest {
	
	@Test
	public void constructorTest() {
		Dictionary<Integer, String> dic= new Dictionary<>();
	}
	
	@Test
	public void putAndGetTest() {
		Dictionary<Integer, String> dic= new Dictionary<>();
		
		dic.put(123456, "Ivan");
		assertEquals("Ivan", dic.get(123456));
		dic.put(1234561, "Pero");
		assertEquals("Pero", dic.get(1234561));
	}
	
	@Test
	public void sizeTest() {
		Dictionary<Integer, String> dic= new Dictionary<>();
		
		assertEquals(0, dic.size());
		dic.put(123456, "Ivan");
		assertEquals(1, dic.size());
		dic.put(1234561, "Pero");
		assertEquals(2, dic.size());
	}
	
	@Test
	public void clearTest() {
		Dictionary<Integer, String> dic= new Dictionary<>();
		
		assertEquals(0, dic.size());
		dic.put(123456, "Ivan");
		assertEquals(1, dic.size());
		dic.put(1234561, "Pero");
		assertEquals(2, dic.size());
		dic.clear();
		assertEquals(0, dic.size());
	}
	
	
}
