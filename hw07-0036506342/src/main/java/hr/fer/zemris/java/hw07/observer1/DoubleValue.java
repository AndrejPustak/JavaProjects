package hr.fer.zemris.java.hw07.observer1;

/**
 * Implementation of IntegerStorageObserver. This observer writes the current value and
 * current value * 2 to the system.out. This w
 * @author Andrej
 *
 */
public class DoubleValue implements IntegerStorageObserver {
	
	private int counter = 0;
	private int stop;
	
	/**
	 * Constructor for the DoubleValue
	 * @param stop Value after which the class will stop writing to the system.out
	 */
	public DoubleValue(int stop) {
		this.stop = stop;
	}

	@Override
	public void valueChanged(IntegerStorage istorage) {
		if(counter++ < stop) {
			System.out.println("Double value: " + istorage.getValue() * 2);
		}
		else {
			istorage.removeObserver(this);
		}

	}

}
