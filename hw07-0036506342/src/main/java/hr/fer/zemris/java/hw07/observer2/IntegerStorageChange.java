package hr.fer.zemris.java.hw07.observer2;

/**
 * Class that encapsulates current value, previous value and a reference to IntegerStorage class.
 * A new instance of this class is created and passed to all the observers every time the value
 * of IntegerStorage changes.
 * @author Andrej
 *
 */
public class IntegerStorageChange {
	
	private final IntegerStorage istorage;
	private final int current;
	private final int previous;
	
	/**
	 * Constructor for the IntegerStorageChange
	 * @param istorage reference to IntegerStorage
	 * @param current current value of IntegerStorage
	 * @param previous previous value of IntegerStorage
	 */
	public IntegerStorageChange(IntegerStorage istorage, int current, int previous) {
		this.istorage = istorage;
		this.current = current;
		this.previous = previous;
	}
	
	/**
	 * Getter for the reference to the IntegerStorage
	 * @return reference to the IntegerStorage
	 */
	public IntegerStorage getIstorage() {
		return istorage;
	}
	
	/**
	 * Getter for the current value of IntegerStorage
	 * @return current value
	 */
	public int getCurrent() {
		return current;
	}
	
	/**
	 * Getter for the previous value of IntegerStorage
	 * @return previou storage
	 */
	public int getPrevious() {
		return previous;
	}

}
