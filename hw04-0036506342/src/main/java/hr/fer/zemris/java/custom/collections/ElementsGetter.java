package hr.fer.zemris.java.custom.collections;

/**
 * Interface ElementsGetter represents a template for a elements getter of a specific collection
 * Each collection which wishes to use it should implement it's own version
 * @author Andrej
 *
 *@param <E> Type of the values stored in the collection
 */
public interface ElementsGetter<E> {
	
	/**
	 * This method checks if the collection has more elements to get
	 * @return True if there is more elements to get, false otherwise
	 */
	boolean hasNextElement();
	
	/**
	 * This method returns the next element of the collection
	 * @return Next element of the collection
	 */
	E getNextElement();
	
	/**
	 * This method calls Process.process for all remaining elements of the collection
	 * @param p Instance of the interface Processor with overridden method process
	 */
	default void processRemaining(Processor<? super E> p) {
		while (hasNextElement()) {
			p.process(getNextElement());
		}
	}
}
