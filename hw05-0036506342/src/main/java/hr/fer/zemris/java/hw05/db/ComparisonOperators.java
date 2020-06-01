package hr.fer.zemris.java.hw05.db;

/**
 * This class is a collection of comparison operators used to compare 2 strings
 * @author Andrej
 *
 */
public class ComparisonOperators {
	
	/**
	 * This operator checks if the v1 is less than v2 and returns true if it is, false otherwise
	 */
	public static final IComparisonOperator LESS = (v1, v2) -> v1.compareTo(v2) < 0;
	/**
	 * This operator checks if the v1 is less or equal to v2 and returns true if it is, false otherwise
	 */
	public static final IComparisonOperator LESS_OR_EQUALS = (v1, v2) -> v1.compareTo(v2) <= 0;
	/**
	 * This operator checks if the v1 is greater than v2 and returns true if it is, false otherwise
	 */
	public static final IComparisonOperator GREATER = (v1, v2) -> v1.compareTo(v2) > 0;
	/**
	 * This operator checks if the v1 is greater or equal to v2 and returns true if it is, false otherwise
	 */
	public static final IComparisonOperator GREATER_OR_EQUALS = (v1, v2) -> v1.compareTo(v2) >= 0;
	/**
	 * This operator checks if the v1 is equal to v2 and returns true if it is, false otherwise
	 */
	public static final IComparisonOperator EQUALS = (v1, v2) -> v1.compareTo(v2) == 0;
	/**
	 * This operator checks if the v1 is not equal to v2 and returns true if it is, false otherwise
	 */
	public static final IComparisonOperator NOT_EQUALS = (v1, v2) -> v1.compareTo(v2) != 0;
	/**
	 * This operator checks if the v1 is like v2 and returns true if it is, false otherwise
	 */
	public static final IComparisonOperator LIKE = (v1, v2) -> {
		int index = v2.indexOf('*');
		String begining = v2.substring(0, index);
		String end = v2.substring(index+1);
		
		if (v1.length() < begining.length() + end.length()) {
			return false;
		}
		
		return v1.startsWith(begining) && v1.endsWith(end);
	};
}
