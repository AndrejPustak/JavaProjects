package hr.fer.zemris.java.hw05.db;

/**
 * This interface is used to get one specific attribute from the student record
 * @author Andrej
 *
 */
public interface IFieldValueGetter {
	
	/**
	 * This method gets one attribute from the given student record
	 * @param record student record you wish to get the attribute from
	 * @return Value of the attribute from student record
	 */
	public String get(StudentRecord record);
}
