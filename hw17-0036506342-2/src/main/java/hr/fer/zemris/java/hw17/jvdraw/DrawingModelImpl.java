package hr.fer.zemris.java.hw17.jvdraw;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw17.jvdraw.geometrical.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.listeners.DrawingModelListener;
import hr.fer.zemris.java.hw17.jvdraw.listeners.GeometricalObjectListener;

/**
 * This class represents an implementation of the DrawingModel
 * @author Andrej
 *
 */
public class DrawingModelImpl implements DrawingModel{
	
	/**
	 * Modified flag
	 */
	boolean modified;
	
	/**
	 * List of GeometricalObjects
	 */
	private List<GeometricalObject> objects;
	
	/**
	 * List of listeners
	 */
	private List<DrawingModelListener> listeners;
	
	/**
	 * Constructor for the class
	 */
	public DrawingModelImpl() {
		objects = new ArrayList<GeometricalObject>();
		listeners = new ArrayList<DrawingModelListener>();
		modified = false;
	}

	@Override
	public int getSize() {
		return objects.size();
	}

	@Override
	public GeometricalObject getObject(int index) {
		return objects.get(index);
	}

	@Override
	public void add(GeometricalObject object) {
		objects.add(object);
		object.addGeometricalObjectListener(new GeometricalObjectListener() {
			
			@Override
			public void geometricalObjectChanged(GeometricalObject o) {
				int i = indexOf(o);
				fireObjectChanged(i, i);
				modified = true;
			}
		});
		
		fireObjectAdded(getSize()-1, getSize()-1);
		modified = true;
	}

	@Override
	public void remove(GeometricalObject object) {
		int i = indexOf(object);
		if(objects.remove(object)) {
			fireObjectRemoved(i, i);
		}
		modified = true;
	}

	@Override
	public void changeOrder(GeometricalObject object, int offset) {
		int curIndex = objects.indexOf(object);
		if(curIndex == -1) return;
		
		int newIndex = curIndex + offset;
		
		if(newIndex < curIndex && newIndex>=0) {
			Collections.rotate(objects.subList(newIndex, curIndex+1), offset);
			fireObjectChanged(newIndex, curIndex);
		} else if (newIndex > curIndex && newIndex<objects.size()) {
			Collections.rotate(objects.subList(curIndex, newIndex+1), offset);
			fireObjectChanged(curIndex, newIndex);
		}
		
		modified = true;
	}

	@Override
	public int indexOf(GeometricalObject object) {
		return objects.indexOf(object);
	}

	@Override
	public void clear() {
		objects.clear();
	}

	@Override
	public void clearModifiedFlag() {
		modified = false;
	}

	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public void addDrawingModelListener(DrawingModelListener l) {
		listeners.add(l);
	}

	@Override
	public void removeDrawingModelListener(DrawingModelListener l) {
		listeners.remove(l);
	}
	
	/**
	 * This method is used to notifiy all the listeners that an object
	 * has been changed
	 * @param index0 index of the first element affected
	 * @param index1 index of the last element affected
	 */
	public void fireObjectChanged(int index0, int index1) {
		for(int i = 0; i< listeners.size(); i++) {
			listeners.get(i).objectsChanged(this, index0, index1);
		}
	}
	
	/**
	 * This method is used to notify all the listener that an object
	 * has been added
	 * @param index0 index of the first element affected
	 * @param index1 index of the last element affected
	 */
	public void fireObjectAdded(int index0, int index1) {
		for(int i = 0; i< listeners.size(); i++) {
			listeners.get(i).objectsAdded(this, index0, index1);
		}
	}
	
	/**
	 * This method is used to notify all the listener that an object
	 * has been removed
	 * @param index0 index of the first element affected
	 * @param index1 index of the last element affected
	 */
	public void fireObjectRemoved(int index0, int index1) {
		for(int i = 0; i< listeners.size(); i++) {
			listeners.get(i).objectsRemoved(this, index0, index1);
		}
	}

}
