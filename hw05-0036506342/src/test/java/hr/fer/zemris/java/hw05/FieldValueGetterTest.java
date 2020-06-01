package hr.fer.zemris.java.hw05;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw05.db.FieldValueGetters;
import hr.fer.zemris.java.hw05.db.StudentRecord;

public class FieldValueGetterTest {
	
	@Test
	void fistNameTest() {
		StudentRecord record = new StudentRecord("0123456789", "Andrea", "Bosnić", 4);
		
		assertEquals("Andrea", FieldValueGetters.FIRST_NAME.get(record));
	}
	
	@Test
	void lastNameTest() {
		StudentRecord record = new StudentRecord("0123456789", "Andrea", "Bosnić", 4);
		
		assertEquals("Bosnić", FieldValueGetters.LAST_NAME.get(record));
	}
	
	@Test
	void jmbagTest() {
		StudentRecord record = new StudentRecord("0123456789", "Andrea", "Bosnić", 4);
		
		assertEquals("0123456789", FieldValueGetters.JMBAG.get(record));
	}
}
