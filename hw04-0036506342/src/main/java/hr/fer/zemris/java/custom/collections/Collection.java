package hr.fer.zemris.java.custom.collections;

/**
 * Interface collection represents a generic template for a collection. It should be used as a template
 * for your own collection class which implements this one.
 * 
 * @author Andrej
 *
 *	@param <E> Type of the values stored in the collection
 */

public interface Collection<E> {
	
	/**
	 * This method checks if the collection is empty.
	 * @return True if the collection is empty, false otherwise.
	 * 
	 * @param <E> Type of the values in the collection
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
	void add(E value);
	
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
	default void forEach(Processor<? super E> processor) {
		ElementsGetter<E> getter = this.createElementsGetter();
		getter.processRemaining(processor);
	}
	
	/**
	 * A method which adds all elements of the given collection to this collection.
	 * 
	 * @param other A collection you wish to add to this collection.
	 */
	default void addAll(Collection<? extends E> other) {
		Processor<E> AddProcessor = (value) -> add((E) value);
		
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
	ElementsGetter<E> createElementsGetter();
	
	/**
	 * Default method for the collection
	 * It adds all elements to the collection which satisfy the condition specified in the method Tester.test
	 * @param col Collection whose elements you wish to add to this collection
	 * @param tester An instance of the class Tester with overridden method test to test our specific condition
	 */
	@SuppressWarnings("unchecked")
	default void addAllSatisfying(Collection<? extends E> col, Tester<? super E> tester) {
		ElementsGetter<E> getter = (ElementsGetter<E>) col.createElementsGetter();
		
		getter.processRemaining((obj) -> {
			if (tester.test((E)obj)) {
				add((E) obj);
			}
		});
		
	}
	
}
