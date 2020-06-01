package hr.fer.zemris.java.hw17.jvdraw.components;

import javax.swing.AbstractListModel;

import hr.fer.zemris.java.hw17.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.geometrical.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.listeners.DrawingModelListener;

/**
 * This class represents a list model that shows the current GeometricalObjects on the drawing
 * @author Andrej
 *
 */
public class DrawingObjectListModel extends AbstractListModel<GeometricalObject>{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Drawing model
	 */
	private DrawingModel model;

	/**
	 * Constructor for the DrawingObjectListModel
	 * @param model DrawingModel
	 */
	public DrawingObjectListModel(DrawingModel model) {
		this.model = model;
		model.addDrawingModelListener(new DrawingModelListener() {
			
			@Override
			public void objectsRemoved(DrawingModel source, int index0, int index1) {
				fireIntervalRemoved(source, index0, index1);
			}
			
			@Override
			public void objectsChanged(DrawingModel source, int index0, int index1) {
				fireContentsChanged(source, index0, index1);
			}
			
			@Override
			public void objectsAdded(DrawingModel source, int index0, int index1) {
				fireIntervalAdded(source, index0, index1);
			}
		});
	}

	@Override
	public int getSize() {
		return model.getSize();
	}

	@Override
	public GeometricalObject getElementAt(int index) {
		return model.getObject(index);
	}
	
}
