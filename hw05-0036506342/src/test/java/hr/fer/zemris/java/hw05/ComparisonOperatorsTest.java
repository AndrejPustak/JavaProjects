package hr.fer.zemris.java.hw05;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw05.db.ComparisonOperators;
import hr.fer.zemris.java.hw05.db.IComparisonOperator;

public class ComparisonOperatorsTest {

	@Test
	void lessTest() {
		IComparisonOperator oper = ComparisonOperators.LESS;
		
		String s1 = "Ana";
		String s2 = "Ivana";
		String s3 = "Ivan";
		
		assertEquals(true, oper.satisfied(s1, s2));
		assertEquals(true, oper.satisfied(s3, s2));
		assertEquals(false, oper.satisfied(s1, s1));
		assertEquals(false, oper.satisfied(s3, s1));
	}
	
	@Test
	void lessEqualsTest() {
		IComparisonOperator oper = ComparisonOperators.LESS_OR_EQUALS;
		
		String s1 = "Ana";
		String s2 = "Ivana";
		String s3 = "Ivan";
		
		assertEquals(true, oper.satisfied(s1, s2));
		assertEquals(true, oper.satisfied(s3, s2));
		assertEquals(true, oper.satisfied(s1, s1));
		assertEquals(false, oper.satisfied(s3, s1));
	}
	
	@Test
	void greaterTest() {
		IComparisonOperator oper = ComparisonOperators.GREATER;
		
		String s1 = "Ana";
		String s2 = "Ivana";
		String s3 = "Ivan";
		
		assertEquals(false, oper.satisfied(s1, s2));
		assertEquals(false, oper.satisfied(s3, s2));
		assertEquals(false, oper.satisfied(s1, s1));
		assertEquals(true, oper.satisfied(s3, s1));
	}
	
	@Test
	void greaterEqualsTest() {
		IComparisonOperator oper = ComparisonOperators.GREATER_OR_EQUALS;
		
		String s1 = "Ana";
		String s2 = "Ivana";
		String s3 = "Ivan";
		
		assertEquals(false, oper.satisfied(s1, s2));
		assertEquals(false, oper.satisfied(s3, s2));
		assertEquals(true, oper.satisfied(s1, s1));
		assertEquals(true, oper.satisfied(s3, s1));
	}
	
	@Test
	void equalsTest() {
		IComparisonOperator oper = ComparisonOperators.EQUALS;
		
		String s1 = "Ana";
		String s2 = "Ivana";
		String s3 = "Ivan";
		
		assertEquals(false, oper.satisfied(s1, s2));
		assertEquals(false, oper.satisfied(s3, s2));
		assertEquals(true, oper.satisfied(s1, s1));
		assertEquals(false, oper.satisfied(s3, s1));
	}
	
	@Test
	void notEqualsTest() {
		IComparisonOperator oper = ComparisonOperators.NOT_EQUALS;
		
		String s1 = "Ana";
		String s2 = "Ivana";
		String s3 = "Ivan";
		
		assertEquals(true, oper.satisfied(s1, s2));
		assertEquals(true, oper.satisfied(s3, s2));
		assertEquals(false, oper.satisfied(s1, s1));
		assertEquals(true, oper.satisfied(s3, s1));
	}
	
	@Test
	void likeTest() {
		IComparisonOperator oper = ComparisonOperators.LIKE;

		assertEquals(false, oper.satisfied("Zagreb", "Aba*"));
		assertEquals(false, oper.satisfied("AAA", "AA*AA"));
		assertEquals(true, oper.satisfied("AAAA", "AA*AA"));
		
		assertEquals(true, oper.satisfied("Zagreb", "Zag*"));
		assertEquals(true, oper.satisfied("Zagreb", "*eb"));
	}
}
