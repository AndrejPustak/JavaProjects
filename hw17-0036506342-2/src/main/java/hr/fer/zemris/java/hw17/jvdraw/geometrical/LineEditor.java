package hr.fer.zemris.java.hw17.jvdraw.geometrical;

import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw17.jvdraw.components.JColorArea;

/**
 * This class represents an editor for the Lines
 * @author Andrej
 *
 */
public class LineEditor extends GeometricalObjectEditor {
	
	private static final long serialVersionUID = 1L;
	
	JTextField startX;
	JTextField startY;
	JTextField endX;
	JTextField endY;
	JColorArea chooser;
	
	/**
	 * Line
	 */
	private Line line;
	
	/**
	 * Constructor for the line editor
	 * @param line line
	 */
	public LineEditor(Line line) {
		super();
		this.line = line;
		
		setSize(400, 400);
		
		initGUI();
		
	}
	
	/**
	 * Methos used to initialise the GUI
	 */
	private void initGUI() {
		setLayout(new GridLayout(0, 2));
		
		add(new JLabel("startX"));
		startX = new JTextField(String.valueOf(line.getStartPoint().x));
		add(startX);
		
		add(new JLabel("startY"));
		startY = new JTextField(String.valueOf(line.getStartPoint().y));
		add(startY);
		
		add(new JLabel("endX"));
		endX = new JTextField(String.valueOf(line.getEndPoint().x));
		add(endX);
		
		add(new JLabel("endY"));
		endY = new JTextField(String.valueOf(line.getEndPoint().y));
		add(endY);
		
		add(new JLabel("color:"));
		chooser = new JColorArea(line.getColor());
		add(chooser);
		
	}



	@Override
	public void checkEditing() {
		try {
			Integer.parseInt(startX.getText());
			Integer.parseInt(startY.getText());
			Integer.parseInt(endX.getText());
			Integer.parseInt(endY.getText());
		} catch (NumberFormatException e){
			throw new RuntimeException("One of the point coordinates is not integer");
		}
	}

	@Override
	public void acceptEditing() {
		try {
			int x = Integer.parseInt(startX.getText());
			int y = Integer.parseInt(startY.getText());
			int X = Integer.parseInt(endX.getText());
			int Y = Integer.parseInt(endY.getText());
			
			line.setStartPoint(new Point(x, y));
			line.setEndPoint(new Point(X, Y));
			line.setColor(chooser.getCurrentColor());
			
		} catch (NumberFormatException e){
			throw new RuntimeException("One of the point coordinates is not integer");
		}
	}

}
