package hr.fer.zemris.java.custom.collections;

/**
 * Class Processor represents a generic class which is used to perform specific task on 
 * the passed object using the method process.
 * 
 * Any class using the class Processor will have to make its own implementation which extends 
 * this class.
 * 
 * @author Andrej Pustak
 *
 */
public class Processor {
	
	/**
	 * The only method in the class.
	 * On its own it does nothing so every class implementing class Processor will have
	 * to override the method to do what they wish it to do.
	 * 
	 * @param value Object on which the specific task will be performed.
	 */
	public void process(Object value) {
	}

}
