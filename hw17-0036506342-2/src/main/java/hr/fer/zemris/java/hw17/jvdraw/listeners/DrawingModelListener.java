package hr.fer.zemris.java.hw17.jvdraw.listeners;

import hr.fer.zemris.java.hw17.jvdraw.DrawingModel;

/**
 * Interface that represents a listener for the drawing model
 * @author Andrej
 *
 */
public interface DrawingModelListener {
	
	/**
	 * Method that is called when an object is added
	 * @param source source
	 * @param index0 index of the first element affected 
	 * @param index1 index of the last element affected
	 */
	public void objectsAdded(DrawingModel source, int index0, int index1);
	
	/**
	 * Method that is called when an object is removed
	 * @param source source
	 * @param index0 index of the first element affected 
	 * @param index1 index of the last element affected
	 */
	public void objectsRemoved(DrawingModel source, int index0, int index1);
	
	/**
	 * Method that is called when an object is changed
	 * @param source source
	 * @param index0 index of the first element affected 
	 * @param index1 index of the last element affected
	 */
	public void objectsChanged(DrawingModel source, int index0, int index1);
	
}