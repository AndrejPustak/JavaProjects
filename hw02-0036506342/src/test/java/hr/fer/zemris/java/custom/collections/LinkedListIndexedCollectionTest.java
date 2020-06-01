package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class LinkedListIndexedCollectionTest {
	@Test
	public void constructorTest() {
		LinkedListIndexedCollection col1 = new LinkedListIndexedCollection();
		
		col1.add(5);
		col1.add("Test");
		assertEquals(true, col1.contains(5));
		assertEquals(true, col1.contains("Test"));
		
		ArrayIndexedCollection col2 = new ArrayIndexedCollection(col1);
		assertEquals(true, col2.contains(5));
		assertEquals(true, col2.contains("Test"));
		
	}
	
	@Test
	public void addTest() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add(5);
		col.add("Test");
		assertEquals(true, col.contains(5));
		assertEquals(true, col.contains("Test"));
	}
	
	@Test
	public void getTest() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add(5);
		col.add("Test");
		col.add(8);
		col.add(7);
		assertEquals(5, col.get(0));
		assertEquals("Test", col.get(1));
		assertEquals(7, col.get(3));
		assertThrows(IndexOutOfBoundsException.class, () -> col.get(-1));
	}
	
	@Test
	public void clearTest() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add(5);
		col.add("Test");
		col.clear();
		assertEquals(false, col.contains(5));
		assertEquals(false, col.contains("Test"));
	}
	
	@Test
	public void insertTest() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.insert(5, 0);
		col.insert("Test", 1);
		col.insert(2, 1);
		assertEquals(5, col.get(0));
		assertEquals(2, col.get(1));
		assertEquals("Test", col.get(2));
		assertThrows(IndexOutOfBoundsException.class, () -> col.insert (9,-1));
	}
	
	@Test
	public void indexOfTest() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
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
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add(5);
		col.add("Test");
		assertEquals(true, col.contains(5));
		col.remove(Integer.valueOf(5));
		col.remove(0);
		assertEquals(false, col.contains(5));
		assertThrows(IndexOutOfBoundsException.class, () -> col.insert (9,2));
	}
	
}
