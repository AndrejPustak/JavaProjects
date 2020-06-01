package hr.fer.zemris.java.hw05.db;

/**
 * This interface has only one method for checking if two values satisfy a condition
 * @author Andrej
 *
 */
@FunctionalInterface
public interface IComparisonOperator {
	
	/**
	 * This method checks if the given values satisfy a condition
	 * @param value1 first value
	 * @param value2 second value
	 * @return True if values satisfy a condition, false otherwise
	 */
	public boolean satisfied(String value1, String value2);
	
}
