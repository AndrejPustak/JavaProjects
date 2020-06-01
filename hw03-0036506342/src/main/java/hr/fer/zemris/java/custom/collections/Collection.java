package hr.fer.zemris.java.custom.collections;

/**
 * Interface collection represents a generic template for a collection. It should be used as a template
 * for your own collection class which implements this one.
 * 
 * @author Andrej
 *
 */

public interface Collection {
	
	/**
	 * This method checks if the collection is empty.
	 * @return True if the collection is empty, false otherwise.
	 */
	default boolean isEmpty() {
		return size() == 0;
	}
	
	/**
	 * This method returns the size of the collection.
	 * 
	 * @return Size of the collection.
	 */
	int size();
	
	/**
	 * This method adds an object to the collection.
	 * 
	 * @param value Object which you wish to add to the collection.
	 */
	void add(Object value);
	
	/**
	 * This method checks if the given element is in the collection.
	 * 
	 * @param value Object you wish to check is in the collection.
	 * @return true if the object is in the collection, false otherwise.
	 */
	boolean contains(Object value);
	
	/**
	 * This method removes the given object from the collection.
	 * @param value Object you wish to remove
	 * @return True if the object was successfully removed, false otherwise.
	 */
	boolean remove(Object value);
	
	/**
	 * This method returns the collection in the form of an array.
	 * 
	 * @return Array of objects.
	 */
	public Object[] toArray();
	
	/**
	 * This method calls processor.process() for each element in the collection.
	 * 
	 * @param processor An instance of the interface Processor with overridden method process.
	 */
	default void forEach(Processor processor) {
		ElementsGetter getter = this.createElementsGetter();
		getter.processRemaining(processor);
	}
	
	/**
	 * A method which adds all elements of the given collection to this collection.
	 * 
	 * @param other A collection you wish to add to this collection.
	 */
	default void addAll(Collection other) {
		Processor AddProcessor = (value) -> add(value);
		
		other.forEach(AddProcessor);
	}
	
	/**
	 * This method clears the collection of all elements
	 */
	void clear();
	
	/**
	 * This method creates an ElementGetter for this collection
	 * Each ElementGetter works separately and you can create multiple instances of them
	 * @return ElementGetter for this collection
	 */
	ElementsGetter createElementsGetter();
	
	/**
	 * Default method for the collection
	 * It adds all elements to the collection which satisfy the condition specified in the method Tester.test
	 * @param col Collection whose elements you wish to add to this collection
	 * @param tester An instance of the class Tester with overridden method test to test our specific condition
	 */
	default void addAllSatisfying(Collection col, Tester tester) {
		ElementsGetter getter = col.createElementsGetter();
		
		getter.processRemaining((obj) -> {
			if (tester.test(obj)) {
				add(obj);
			}
		});
		
	}
	
}
