package hr.fer.zemris.java.hw07.observer2;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.hw07.observer2.IntegerStorageObserver;

/**
 * This class represents a simple class that stores one integer. It is used as a subject
 * in our simulation of change observers.
 * @author Andrej
 *
 */
public class IntegerStorage {
	private int value;
	private List<IntegerStorageObserver> observers; // use ArrayList here!!!
	
	/**
	 * Constructor for the IntegerStorage
	 * @param initialValue Initial value of the integer
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
		observers = new ArrayList<IntegerStorageObserver>();
	}
	/**
	 * THis method adds one observer to the list of observers
	 * @param observer observer you wish to add
	 */
	public void addObserver(IntegerStorageObserver observer) {
		Objects.requireNonNull(observer);
		observers.add(observer);
	}
	
	/**
	 * THis method removed the given observer from observer list if it exists.
	 * @param observer observer you wish to remove
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		observers.remove(observer);
	}
	
	/**
	 * This method removes all observers
	 */
	public void clearObservers() {
		observers.clear();
	}
	
	/**
	 * Getter for the value of the integer
	 * @return integer
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * Setter for the value of the integer.
	 * It also notifies all observers of the change
	 * @param value value you wish to change the integer to
	 */
	public void setValue(int value) {
		IntegerStorageObserver[] array = observers.toArray(new IntegerStorageObserver[observers.size()]);
		
		// Only if new value is different than the current value:
		if(this.value!=value) {
			
			int old = value;
			// Update current value
			this.value = value;
			
			IntegerStorageChange ichange = new IntegerStorageChange(this, value, old);
			
			// Notify all registered observers
			if(observers!=null) {
				for(IntegerStorageObserver observer : array) {
					observer.valueChanged(ichange);
				}
			}
		}
	}
}

