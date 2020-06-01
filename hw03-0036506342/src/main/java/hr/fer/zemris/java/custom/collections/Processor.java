package hr.fer.zemris.java.custom.collections;

/**
 * Class Processor represents a generic interface which is used to perform specific task on 
 * the passed object using the method process.
 * 
 * Any class using the interface Processor will have to make its own implementation which implements 
 * this interface.
 * 
 * @author Andrej Pustak
 *
 */
public interface Processor {
	
	/**
	 * The only method in the class.
	 * On its own it does nothing so every class implementing the interface Processor will have
	 * to override the method to do what they wish it to do.
	 * 
	 * @param value Object on which the specific task will be performed.
	 */
	void process(Object value);

}
