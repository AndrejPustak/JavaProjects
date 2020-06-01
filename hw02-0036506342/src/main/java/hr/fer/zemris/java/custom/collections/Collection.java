package hr.fer.zemris.java.custom.collections;

/**
 * Class collection represents a generic collection. It should be used as a template
 * for your own collection class which imports this one.
 * 
 * Most of its methods are are empty and don't do anything of value without overriding.
 * 
 * 
 * @author Andrej
 *
 */

public class Collection {
	
	/**
	 * A default constructor for the class.
	 */
	
	protected Collection() { }
	
	/**
	 * This method checks if the collection is empty.
	 * @return True if the collection is empty and false otherwise.
	 */
	public boolean isEmpty() {
		return size() == 0;
	}
	
	/**
	 * This method returns the size of the collection.
	 * 
	 * It is implemented here to always return 0.
	 * @return Size of the collection.
	 */
	public int size() {
		return 0;
	}
	
	/**
	 * This method adds an object to the collection.
	 * 
	 * It is implemented here as an empty method.
	 * @param value Object which you wish to add to the collection.
	 */
	public void add(Object value) { }
	
	/**
	 * This method checks if the given element is in the collection.
	 * 
	 * It is implemented here to always return false.
	 * @param value Object you wish to check is in the collection.
	 * @return true if the object is in the collection, false otherwise.
	 */
	public boolean contains(Object value) {
		return false;
	}
	
	/**
	 * This method removes given object from the collection.
	 * @param value Object you wish to remove
	 * @return True if the object was successfully removed, false otherwise.
	 */
	public boolean remove(Object value) {
		return false;
	}
	
	/**
	 * This method returns the collection in the form of an array.
	 * 
	 * It is implemented here to always throw UnsupportedOperationException
	 * @return Array of objects.
	 * @throws UnsupportedOperationException
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * This method calls processor.process() for each element in the collection.
	 * 
	 * It is implemented here as an empty method.
	 * @param processor An instance of the class processor with overridden method process.
	 */
	public void forEach(Processor processor) { }
	
	/**
	 * A method which adds all elements of the given collection to this collection.
	 * 
	 * @param other A collection you wish to add to this collection.
	 */
	public void addAll(Collection other) {
		class AddProcessor extends Processor {
			
			@Override
			public void process(Object value) {
				add(value);
			}
		}
		
		Processor processor = new AddProcessor();
		other.forEach(processor);
	}
	
	/**
	 * This method clears the collection.
	 * 
	 * It is implemented here as an empty method.
	 */
	public void clear() { }
}
