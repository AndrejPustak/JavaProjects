package hr.fer.zemris.java.hw07.observer1;

/**
 * Implementation of IntegerStorageObserver. This observer writes the current value and the 
 * value squared to the system.out.
 * @author Andrej
 *
 */
public class SquareValue implements IntegerStorageObserver {

	@Override
	public void valueChanged(IntegerStorage istorage) {
		System.out.println("Provided new value: " + istorage.getValue()+", square is " + istorage.getValue()*istorage.getValue());

	}

}
