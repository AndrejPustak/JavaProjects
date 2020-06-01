package hr.fer.zemris.java.hw07.observer2;

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
	public void valueChanged(IntegerStorageChange istorage) {
		if(counter++ < stop) {
			System.out.println("Double value: " + istorage.getCurrent() * 2);
		}
		else {
			istorage.getIstorage().removeObserver(this);
		}

	}

}
