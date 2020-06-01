package hr.fer.zemris.java.hw05;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw05.db.ComparisonOperators;
import hr.fer.zemris.java.hw05.db.ConditionalExpression;
import hr.fer.zemris.java.hw05.db.FieldValueGetters;
import hr.fer.zemris.java.hw05.db.StudentRecord;


public class ConditionalExpressionTest {
	
	@Test
	void expressionTest() {
		ConditionalExpression expr = new ConditionalExpression(
				 FieldValueGetters.LAST_NAME,
				 "Bos*",
				 ComparisonOperators.LIKE
				);
		
		StudentRecord record = new StudentRecord("0123456789", "Andrea", "Bosnić", 4);
		boolean recordSatisfies = expr.getComparisonOperator().satisfied(
		 expr.getFieldGetter().get(record), // returns lastName from given record
		 expr.getStringLiteral() // returns "Bos*"
		);
		
		assertTrue(recordSatisfies);

	}
	
	

}
