package hr.fer.zemris.java.hw05.db;

/**
 * This class is a collection of filed value getters
 * @author Andrej
 *
 */
public class FieldValueGetters {
	
	/**
	 * Field value getter for the first name of the record
	 */
	public static final IFieldValueGetter FIRST_NAME = (r) -> r.getFirstName();
	/**
	 * Field value getter for the last name of the record
	 */
	public static final IFieldValueGetter LAST_NAME = (r) -> r.getLastName();
	/**
	 * Field value getter for the JMBAG of the record
	 */
	public static final IFieldValueGetter JMBAG = (r) -> r.getJmbag();
	
}
