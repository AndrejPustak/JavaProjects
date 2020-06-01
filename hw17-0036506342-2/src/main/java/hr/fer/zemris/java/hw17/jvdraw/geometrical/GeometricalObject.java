package hr.fer.zemris.java.hw17.jvdraw.geometrical;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw17.jvdraw.listeners.GeometricalObjectListener;

/**
 * Abstract class representing a Geometrical object
 * @author Andrej
 *
 */
public abstract class GeometricalObject {
	
	/**
	 * List of listeners
	 */
	private List<GeometricalObjectListener> listeners = new ArrayList<GeometricalObjectListener>();
	
	/**
	 * Method used to create an editor for the object
	 * @return
	 */
	public abstract GeometricalObjectEditor createGeometricalObjectEditor();
	
	/**
	 * Method used to accept a visitor
	 * @param v
	 */
	public abstract void accept(GeometricalObjectVisitor v);
	
	/**
	 * Method used to add a object listener
	 * @param l
	 */
	public void addGeometricalObjectListener(GeometricalObjectListener l) {
		listeners.add(l);
	}
	
	/**
	 * Method used remove object listener
	 * @param l
	 */
	public void removeGeometricalObjectListener(GeometricalObjectListener l) {
		listeners.remove(l);
	}
	
	/**
	 * Method used to notify all the listeners
	 * @param o
	 */
	public void fire(GeometricalObject o) {
		for(int i = 0; i< listeners.size(); i++) {
			listeners.get(i).geometricalObjectChanged(this);
		}
	}
}
