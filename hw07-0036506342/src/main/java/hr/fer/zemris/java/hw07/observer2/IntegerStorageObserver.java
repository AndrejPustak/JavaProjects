package hr.fer.zemris.java.hw07.observer2;

/**
 * A functional interface for observers of IntegerStorage class.
 * @author Andrej
 *
 */
public interface IntegerStorageObserver {
	
	/**
	 * This method is called when a change happened on the observed object.
	 * @param istorage instance of IntegerStorageChange class created after the change
	 */
	public void valueChanged(IntegerStorageChange istorage);
}
