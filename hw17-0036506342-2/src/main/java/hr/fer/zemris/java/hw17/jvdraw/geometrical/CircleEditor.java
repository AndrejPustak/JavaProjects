package hr.fer.zemris.java.hw17.jvdraw.geometrical;

import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw17.jvdraw.components.JColorArea;

/**
 * This class represents an editor for the circle
 * @author Andrej
 *
 */
public class CircleEditor extends GeometricalObjectEditor{
	
	private static final long serialVersionUID = 1L;
	
	JTextField centerX;
	JTextField centerY;
	JTextField radius;
	JColorArea chooser;
	
	/**
	 * CIrcle
	 */
	private Circle circle;
	
	/**
	 * Constructor for the class
	 * @param circle reference to the circle
	 */
	public CircleEditor(Circle circle) {
		super();
		this.circle = circle;
		
		setSize(400, 400);
		
		initGUI();
		
	}
	

	/**
	 * Method used to initialize the GUI
	 */
	private void initGUI() {
		setLayout(new GridLayout(0, 2));
		
		add(new JLabel("centerX"));
		centerX = new JTextField(String.valueOf(circle.getCenter().x));
		add(centerX);
		
		add(new JLabel("centerY"));
		centerY = new JTextField(String.valueOf(circle.getCenter().y));
		add(centerY);
		
		add(new JLabel("Radius"));
		radius = new JTextField(String.valueOf(circle.getRadius()));
		add(radius);
		
		add(new JLabel("color:"));
		chooser = new JColorArea(circle.getColor());
		add(chooser);
		
	}



	@Override
	public void checkEditing() {
		try {
			Integer.parseInt(centerX.getText());
			Integer.parseInt(centerY.getText());
			Integer.parseInt(radius.getText());
		} catch (NumberFormatException e){
			throw new RuntimeException("One of the point coordinates is not integer");
		}
	}

	@Override
	public void acceptEditing() {
		try {
			int x = Integer.parseInt(centerX.getText());
			int y = Integer.parseInt(centerY.getText());
			int r = Integer.parseInt(radius.getText());
			
			circle.setCenter(new Point(x,y));
			circle.setRadius(r);
			circle.setColor(chooser.getCurrentColor());
			
		} catch (NumberFormatException e){
			throw new RuntimeException("One of the point coordinates is not integer");
		}
	}
}
