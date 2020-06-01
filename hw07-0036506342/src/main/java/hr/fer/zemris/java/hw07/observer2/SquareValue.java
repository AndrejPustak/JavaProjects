package hr.fer.zemris.java.hw07.observer2;

/**
 * Implementation of IntegerStorageObserver. This observer writes the current value and the 
 * value squared to the system.out.
 * @author Andrej
 *
 */
public class SquareValue implements IntegerStorageObserver {

	@Override
	public void valueChanged(IntegerStorageChange istorage) {
		System.out.println("Provided new value: " + istorage.getCurrent()+", square is " + istorage.getCurrent()*istorage.getCurrent());

	}

}
