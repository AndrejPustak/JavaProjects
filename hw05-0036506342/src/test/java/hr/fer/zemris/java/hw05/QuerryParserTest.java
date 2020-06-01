package hr.fer.zemris.java.hw05;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw05.db.ComparisonOperators;
import hr.fer.zemris.java.hw05.db.FieldValueGetters;
import hr.fer.zemris.java.hw05.db.QueryParser;

public class QuerryParserTest {
	
	@Test
	void test1() {
		QueryParser qp = new QueryParser("jmbag=\"0000000003\"");
		
		assertEquals(1, qp.getQuery().size());
		assertEquals(true, qp.isDirectQuery());
		assertEquals("0000000003", qp.getQueriedJMBAG());
	}
	
	@Test
	void test2() {
		QueryParser qp = new QueryParser("lastName = \"Blažić\"");
		
		assertEquals(1, qp.getQuery().size());
		assertEquals(false, qp.isDirectQuery());
		assertThrows(IllegalStateException.class, () -> qp.getQueriedJMBAG());
		
		assertEquals(ComparisonOperators.EQUALS, qp.getQuery().get(0).getComparisonOperator());
		assertEquals(FieldValueGetters.LAST_NAME, qp.getQuery().get(0).getFieldGetter());
		assertEquals("Blažić", qp.getQuery().get(0).getStringLiteral());
	}
	
	@Test
	void test3() {
		QueryParser qp = new QueryParser("firstName>\"A\" and lastName LIKE \"B*ć\"");
		
		assertEquals(2, qp.getQuery().size());
		assertEquals(false, qp.isDirectQuery());
		assertThrows(IllegalStateException.class, () -> qp.getQueriedJMBAG());
		
		assertEquals(ComparisonOperators.GREATER, qp.getQuery().get(0).getComparisonOperator());
		assertEquals(FieldValueGetters.FIRST_NAME, qp.getQuery().get(0).getFieldGetter());
		assertEquals("A", qp.getQuery().get(0).getStringLiteral());
		
		assertEquals(ComparisonOperators.LIKE, qp.getQuery().get(1).getComparisonOperator());
		assertEquals(FieldValueGetters.LAST_NAME, qp.getQuery().get(1).getFieldGetter());
		assertEquals("B*ć", qp.getQuery().get(1).getStringLiteral());
	}
	
	@Test
	void test4() {
		QueryParser qp = new QueryParser("firstName>\"A\" and firstName<\"C\" and lastName LIKE \"B*ć\" and jmbag>\"0000000002\"");
		
		assertEquals(4, qp.getQuery().size());
		assertEquals(false, qp.isDirectQuery());
		assertThrows(IllegalStateException.class, () -> qp.getQueriedJMBAG());
		
		assertEquals(ComparisonOperators.GREATER, qp.getQuery().get(0).getComparisonOperator());
		assertEquals(FieldValueGetters.FIRST_NAME, qp.getQuery().get(0).getFieldGetter());
		assertEquals("A", qp.getQuery().get(0).getStringLiteral());
		
		assertEquals(ComparisonOperators.LESS, qp.getQuery().get(1).getComparisonOperator());
		assertEquals(FieldValueGetters.FIRST_NAME, qp.getQuery().get(1).getFieldGetter());
		assertEquals("C", qp.getQuery().get(1).getStringLiteral());
		
		assertEquals(ComparisonOperators.LIKE, qp.getQuery().get(2).getComparisonOperator());
		assertEquals(FieldValueGetters.LAST_NAME, qp.getQuery().get(2).getFieldGetter());
		assertEquals("B*ć", qp.getQuery().get(2).getStringLiteral());
		
		assertEquals(ComparisonOperators.GREATER, qp.getQuery().get(3).getComparisonOperator());
		assertEquals(FieldValueGetters.JMBAG, qp.getQuery().get(3).getFieldGetter());
		assertEquals("0000000002", qp.getQuery().get(3).getStringLiteral());
		
	}
	
}
