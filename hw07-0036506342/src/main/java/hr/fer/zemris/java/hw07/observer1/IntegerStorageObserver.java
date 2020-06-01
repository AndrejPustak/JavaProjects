package hr.fer.zemris.java.hw07.observer1;

/**
 * A functional interface for observers of IntegerStorage class.
 * @author Andrej
 *
 */
public interface IntegerStorageObserver {
	
	/**
	 * This method is called when a change happened on the observed object.
	 * @param istorage reference to the observed class
	 */
	public void valueChanged(IntegerStorage istorage);
}
