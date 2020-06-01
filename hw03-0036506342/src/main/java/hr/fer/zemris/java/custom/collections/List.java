package hr.fer.zemris.java.custom.collections;

/**
 * Interface list is a template for all collections which store 
 * objects in the form of a list
 * It extends the interface Collection
 * @author Andrej
 *
 */
public interface List extends Collection{
	
	/**
	 * This method returns the object of a list with the given index
	 * @param index Index of an object you wish to get
	 * @return Object with the specified index
	 */
	Object get(int index);
	
	/**
	 * This method inserts an object at a given position in the list
	 * The rest of the elements are moves one space to the right
	 * @param value Value of the object you wish to insert
	 * @param position Position you wish to insert the object to
	 */
	void insert(Object value, int position);
	
	/**
	 * This method finds the given object in the list and returns it's index
	 * @param value Value of the object whose index you wish to get
	 * @return Index of the object you wish to get, -1 if the object is not in the list
	 */
	int indexOf(Object value);
	
	/**
	 * This method removes an object with the specified index
	 * @param index Index of an object you wish to remove
	 */
	void remove(int index);
	
}
