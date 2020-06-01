package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ArrayIndexedCollectionTest {
	@Test
	public void constructorTest() {
		ArrayIndexedCollection col1 = new ArrayIndexedCollection(2);
		ArrayIndexedCollection col2 = new ArrayIndexedCollection();
		
		col1.add(5);
		col1.add("Test");
		assertEquals(true, col1.contains(5));
		assertEquals(true, col1.contains("Test"));
		
		col2.add(5);
		col2.add("Test");
		assertEquals(true, col2.contains(5));
		assertEquals(true, col2.contains("Test"));
		
		ArrayIndexedCollection col3 = new ArrayIndexedCollection(col1, 1);
		assertEquals(true, col3.contains(5));
		assertEquals(true, col3.contains("Test"));
		
		ArrayIndexedCollection col4 = new ArrayIndexedCollection(col2);
		assertEquals(true, col4.contains(5));
		assertEquals(true, col4.contains("Test"));
	}
	
	@Test
	public void addTest() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(2);
		col.add(5);
		col.add("Test");
		assertEquals(true, col.contains(5));
		assertEquals(true, col.contains("Test"));
		col.add(1);
		assertEquals(true, col.contains(1));
		assertThrows(NullPointerException.class, () -> col.add(null));
	}
	
	@Test
	public void getTest() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add(5);
		col.add("Test");
		assertEquals(5, col.get(0));
		assertEquals("Test", col.get(1));
		assertThrows(IndexOutOfBoundsException.class, () -> col.get(-1));
	}
	
	@Test
	public void clearTest() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add(5);
		col.add("Test");
		col.clear();
		assertEquals(false, col.contains(5));
		assertEquals(false, col.contains("Test"));
	}
	
	@Test
	public void insertTest() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(2);
		col.add(5);
		col.add("Test");
		col.insert(2, 1);
		assertEquals(5, col.get(0));
		assertEquals(2, col.get(1));
		assertEquals("Test", col.get(2));
		assertThrows(IndexOutOfBoundsException.class, () -> col.insert (9,-1));
	}
	
	@Test
	public void indexOfTest() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(2);
		col.add(5);
		col.add("Test");
		col.insert(2, 1);
		assertEquals(0, col.indexOf(5));
		assertEquals(1, col.indexOf(2));
		assertEquals(2, col.indexOf("Test"));
		assertEquals(-1, col.indexOf("Tst"));
		assertEquals(-1, col.indexOf(null));
	}
	
	@Test
	public void removeTest() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(2);
		col.add(5);
		col.add("Test");
		assertEquals(true, col.contains(5));
		col.remove(0);
		assertEquals(false, col.contains(5));
		assertThrows(IndexOutOfBoundsException.class, () -> col.insert (9,2));
	}
}
