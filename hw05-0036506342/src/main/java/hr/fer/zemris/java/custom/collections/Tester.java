package hr.fer.zemris.java.custom.collections;

/**
 * Interface Tester is an interface with only one method test and should be used to test a specific condition on
 * an object
 * @author Andrej
 * 
 * @param <E> Type of the values to be tested
 *
 */
public interface Tester<E> {
	
	/**
	 * This method checks if the object satisfies a specific condition
	 * @param obj Object you wish to test
	 * @return True if object satisfies the condition, false otherwise
	 */
	boolean test(E obj);
	
}
