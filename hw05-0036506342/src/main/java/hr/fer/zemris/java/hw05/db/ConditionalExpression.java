package hr.fer.zemris.java.hw05.db;

/**
 * This class class represents a single conditional expression when querying the database
 * @author Andrej
 *
 */
public class ConditionalExpression {
	
	/**
	 * Field value getter for the attribute you wish to query by
	 */
	private IFieldValueGetter getter;
	/**
	 * String literal by which the query will happen
	 */
	private String literal;
	/**
	 * Comparison operator for querying the database
	 */
	private IComparisonOperator operator;
	
	/**
	 * Constructor for the ConditionalExpression
	 * @param getter field value getter for the attribute you wish to query by
	 * @param literal string literal by which the query will happen
	 * @param operator Comparison operator for querying the database
	 */
	public ConditionalExpression(IFieldValueGetter getter, String literal, IComparisonOperator operator) {
		this.getter = getter;
		this.literal = literal;
		this.operator = operator;
	}
	
	/**
	 * Getter for the FieldValueGetter of the conditional expression
	 * @return field value getter of the conditional expression
	 */
	public IFieldValueGetter getFieldGetter() {
		return getter;
	}
	
	/**
	 * Getter for the string literal
	 * @return string literal of the conditional expression
	 */
	public String getStringLiteral() {
		return literal;
	}
	
	/**
	 * Getter for the comparison operator of the conditional expression
	 * @return comparison operator of the conditional expression
	 */
	public IComparisonOperator getComparisonOperator() {
		return operator;
	}
	
	
	
}
