package hr.fer.zemris.java.hw05;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw05.db.IFilter;
import hr.fer.zemris.java.hw05.db.StudentDatabase;
import hr.fer.zemris.java.hw05.db.StudentRecord;

public class StudentDatabaseTest {
	
	private StudentDatabase database;
	
	private IFilter alwaysTrue = (value) -> {return true;};
	private IFilter alwaysFalse = (value) -> {return false;};
	
	@BeforeEach
	void init() {
		List<String> list = new ArrayList<>();
		list.add("0000000001	Akšamović	Marin	2");
		list.add("0000000002	Bakamović	Petra	3");
		list.add("0000000003	Bosnić	Andrea	4");
		list.add("0000000004	Božić	Marin	5");
		list.add("0000000005	Brezović	Jusufadis	2");
		list.add("0000000006	Cvrlje	Ivan	3");
		list.add("0000000007	Čima	Sanjin	4");
		
		database = new StudentDatabase(list);
	}
	
	@Test
	void forJMBAGTest() {
		StudentRecord record = new StudentRecord("0000000003", "Andrea", "Bosnić", 4);
		assertEquals(record, database.forJMBAG("0000000003"));
	}
	
	@Test
	void filterTest() {
		List<StudentRecord> fullList = database.filter(alwaysTrue);
		List<StudentRecord> emptyList = database.filter(alwaysFalse);
		
		assertEquals(7, fullList.size());
		assertEquals(0, emptyList.size());
	}
}
