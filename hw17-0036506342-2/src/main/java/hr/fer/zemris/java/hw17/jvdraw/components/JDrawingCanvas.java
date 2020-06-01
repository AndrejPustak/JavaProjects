package hr.fer.zemris.java.hw17.jvdraw.components;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Supplier;

import javax.swing.JComponent;

import hr.fer.zemris.java.hw17.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.Tool;
import hr.fer.zemris.java.hw17.jvdraw.geometrical.GeometricalObjectPainter;
import hr.fer.zemris.java.hw17.jvdraw.geometrical.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw17.jvdraw.listeners.DrawingModelListener;

public class JDrawingCanvas extends JComponent{
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unused")
	private Supplier<Tool> toolSuplier;
	
	/**
	 * Drawing model
	 */
	private DrawingModel model;
	
	/**
	 * Constructor for the JDrawingCanvas
	 * @param toolSuplier suplier for the tools
	 * @param model drawing model
	 */
	public JDrawingCanvas(Supplier<Tool> toolSuplier, DrawingModel model) {
		this.toolSuplier = toolSuplier;
		this.model = model;
		
		model.addDrawingModelListener(new DrawingModelListener() {
			
			@Override
			public void objectsRemoved(DrawingModel source, int index0, int index1) {
				repaint();
			}
			
			@Override
			public void objectsChanged(DrawingModel source, int index0, int index1) {
				repaint();
				toolSuplier.get().paint((Graphics2D) getGraphics());
			}
			
			@Override
			public void objectsAdded(DrawingModel source, int index0, int index1) {
				repaint();
			}
		});
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				toolSuplier.get().mouseClicked(e);
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				toolSuplier.get().mousePressed(e);
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				toolSuplier.get().mouseReleased(e);
			}
		});
		
		addMouseMotionListener(new MouseAdapter() {
			
			@Override
			public void mouseDragged(MouseEvent e) {
				toolSuplier.get().mouseDragged(e);
			}
			
			@Override
			public void mouseMoved(MouseEvent e) {
				toolSuplier.get().mouseMoved(e);
			}
		});
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		GeometricalObjectVisitor painter = new GeometricalObjectPainter((Graphics2D) g);
		for(int i = 0; i< model.getSize(); i++) {
			model.getObject(i).accept(painter);
		}
		toolSuplier.get().paint((Graphics2D) g);
	}

}
