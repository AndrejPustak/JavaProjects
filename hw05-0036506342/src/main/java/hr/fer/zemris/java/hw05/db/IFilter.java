package hr.fer.zemris.java.hw05.db;

/**
 * This functional interface is used to check if the given record satisfies
 * a condition
 * @author Andrej
 *
 */
public interface IFilter {
	
	/**
	 * This method checks if the given student record satisfies a condition
	 * @param record Student record you wish to check satisfies a condition
	 * @return True if the record satisfies the condition, false otherwise
	 */
	public boolean accepts(StudentRecord record);
	
}
