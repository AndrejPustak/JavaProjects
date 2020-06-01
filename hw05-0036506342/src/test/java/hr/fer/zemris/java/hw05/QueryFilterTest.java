package hr.fer.zemris.java.hw05;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw05.db.QueryFilter;
import hr.fer.zemris.java.hw05.db.QueryParser;
import hr.fer.zemris.java.hw05.db.StudentRecord;

public class QueryFilterTest {
	
	@Test
	void test1() {
		QueryParser qp = new QueryParser("lastName = \"Blažić\"");
		
		StudentRecord r1 = new StudentRecord("0000000002", "Andrea", "Blažić", 5);
		StudentRecord r2 = new StudentRecord("0000000003", "Andrea", "Bakamović", 5);
		
		QueryFilter qf = new QueryFilter(qp.getQuery());
		assertEquals(true, qf.accepts(r1));
		assertEquals(false, qf.accepts(r2));
	}
	
	@Test
	void test2() {
		QueryParser qp = new QueryParser("firstName>\"A\" and firstName<\"C\" and lastName LIKE \"B*ć\" and jmbag>\"0000000002\"");
		
		StudentRecord r1 = new StudentRecord("0000000002", "Andrea", "Blažić", 5);
		StudentRecord r2 = new StudentRecord("0000000003", "Andrea", "Bakamović", 5);
		StudentRecord r3 = new StudentRecord("0000000003", "Cndrea", "Bakamović", 5);
		
		QueryFilter qf = new QueryFilter(qp.getQuery());
		assertEquals(false, qf.accepts(r1));
		assertEquals(true, qf.accepts(r2));
		assertEquals(false, qf.accepts(r3));
	}

}
