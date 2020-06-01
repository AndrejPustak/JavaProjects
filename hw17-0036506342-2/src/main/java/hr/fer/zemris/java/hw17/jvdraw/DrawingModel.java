package hr.fer.zemris.java.hw17.jvdraw;

import hr.fer.zemris.java.hw17.jvdraw.geometrical.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.listeners.DrawingModelListener;

/**
 * This class represents a drawing model. It holds the collection of all the GeometricalObjects
 * in the drawing.
 * @author Andrej
 *
 */
public interface DrawingModel {
	
	/**
	 * This method returns the number of the GeometricalObjects in the drawing.
	 * @return
	 */
	public int getSize();
	
	/**
	 * THis method is used to get the object with the specific index
	 * @param index index of the object
	 * @return GeometricalObject with the given index
	 */
	public GeometricalObject getObject(int index);
	
	/**
	 * This method is used to add a new GeometricalObject
	 * @param object GeometricalObject you wish to add to the model
	 */
	public void add(GeometricalObject object);
	
	/**
	 * This method is used to remove the GeometricalObject from the model
	 * @param object GeometricalObject you wish to remove
	 */
	public void remove(GeometricalObject object);
	
	/** 
	 * THis method is used to change the order of the GeometricalObject
	 * @param object GeometricalObject you wish to change the order of
	 * @param offset offset by how much wish to change the order of the object
	 */
	public void changeOrder(GeometricalObject object, int offset);
	
	/**
	 * This method is used to return the index of the GeometricalObject
	 * @param object GeometricalObject you wish to get the index of
	 * @return index of the GeometricalObject
	 */
	public int indexOf(GeometricalObject object);
	
	/**
	 * This method is used to clear the model
	 */
	public void clear();
	
	/**
	 * This method is used to clear the modified flag
	 */
	public void clearModifiedFlag();
	
	/**
	 * This method is used to get the modified flag
	 * @return true if the model has been modified, false otherwise
	 */
	public boolean isModified();
	
	/**
	 * This method is used to add a DrawingModelListener to the model
	 * @param l listener you wish to add to the model
	 */
	public void addDrawingModelListener(DrawingModelListener l);
	
	/**
	 * THis method is used to remove a DrawingModelListener from the model
	 * @param l listener you wish to remove
	 */
	public void removeDrawingModelListener(DrawingModelListener l);
}
