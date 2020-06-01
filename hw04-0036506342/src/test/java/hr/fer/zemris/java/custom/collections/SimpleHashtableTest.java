package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import java.util.EnumMap;

import org.junit.jupiter.api.Test;

public class SimpleHashtableTest {
	
	@Test
	public void constructorTest() {
		SimpleHashtable<Integer, String> table = new SimpleHashtable<>();
		SimpleHashtable<Integer, String> table2 = new SimpleHashtable<>(51);
	}
	
	@Test
	public void putTest() {
		SimpleHashtable<Integer, String> table = new SimpleHashtable<>();
		
		table.put(123456, "Ivan");
		
		assertEquals("Ivan", table.get(123456));
	}
	
	@Test
	public void putMultipleTest() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		
		assertEquals(2, examMarks.get("Ivana"));
		assertEquals(2, examMarks.get("Ante"));
		assertEquals(2, examMarks.get("Jasna"));
		assertEquals(5, examMarks.get("Kristina"));
	}
	
	@Test
	public void overwriteTest() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		examMarks.put("Ivana", 2);
		assertEquals(2, examMarks.get("Ivana"));
		examMarks.put("Ivana", 5);
		assertEquals(5, examMarks.get("Ivana"));
	}
	
	@Test
	public void containsKeyTest() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		examMarks.put("Ivana", 2);
		assertEquals(true, examMarks.containsKey("Ivana"));
		assertEquals(false, examMarks.containsKey("Ante"));
		examMarks.put("Ante", 2);
		assertEquals(true, examMarks.containsKey("Ante"));
	}
	
	@Test
	public void containsValueTest() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		examMarks.put("Ivana", 2);
		assertEquals(true, examMarks.containsValue(2));
		assertEquals(false, examMarks.containsValue(10));
		examMarks.put("Ante", 10);
		assertEquals(true, examMarks.containsValue(10));
	}
	
	@Test
	public void removeTest() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 10);
		
		//remove first
		assertEquals(true, examMarks.containsValue(10));
		examMarks.remove("Ivana");
		assertEquals(false, examMarks.containsValue(10));
		
		//remove last
		examMarks.put("Ivana", 10);
		assertEquals(true, examMarks.containsValue(10));
		examMarks.remove("Ivana");
		assertEquals(false, examMarks.containsValue(10));
		
		//remove middle
		assertEquals(true, examMarks.containsValue(5));
		examMarks.remove("Kristina");
		assertEquals(false, examMarks.containsValue(5));
	}
	
	@Test
	public void isEmptyTest() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		assertEquals(true, examMarks.isEmpty());
		examMarks.put("Ivana", 2);
		assertEquals(false, examMarks.isEmpty());
		examMarks.remove("Ivana");
		assertEquals(true, examMarks.isEmpty());
	}
	
	@Test
	public void toStringTest() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);

		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);
		
		assertEquals("[Ante=2, Ivana=5, Jasna=2, Kristina=5]", examMarks.toString());
	}
	
	
}
