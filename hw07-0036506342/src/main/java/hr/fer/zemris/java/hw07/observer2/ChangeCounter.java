package hr.fer.zemris.java.hw07.observer2;

/**
 * Implementation of IntegerStorageObserver. THis observer counts how mand times the value has changed and
 * writes it in system.out
 * @author Andrej
 *
 */
public class ChangeCounter implements IntegerStorageObserver {
	
	private int counter = 0;

	@Override
	public void valueChanged(IntegerStorageChange istorage) {
		System.out.println("Number of value changes since tracking: " + (++counter));

	}

}
