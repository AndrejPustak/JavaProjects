package hr.fer.zemris.java.hw17.jvdraw.listeners;

import hr.fer.zemris.java.hw17.jvdraw.geometrical.GeometricalObject;

/**
 * This class represents a listener for the geometrical objects
 * @author Andrej
 *
 */
public interface GeometricalObjectListener {
	
	/**
	 * Method that is called when a object is changed
	 * @param o object
	 */
	public void geometricalObjectChanged(GeometricalObject o);

}
