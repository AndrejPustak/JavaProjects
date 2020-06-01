package hr.fer.zemris.java.hw17.jvdraw.geometrical;

import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw17.jvdraw.components.JColorArea;

/**
 * This class represents an editor for filled circles
 * @author Andrej
 *
 */
public class FilledCircleEditor extends GeometricalObjectEditor{
	
	private static final long serialVersionUID = 1L;
	
	JTextField centerX;
	JTextField centerY;
	JTextField radius;
	JColorArea fgChooser;
	JColorArea bgChooser;
	
	/**
	 * Circle
	 */
	private FilledCircle circle;
	
	/**
	 * FilledCircle
	 * @param circle circle
	 */
	public FilledCircleEditor(FilledCircle circle) {
		super();
		this.circle = circle;
		
		setSize(400, 400);
		
		initGUI();
		
	}
	
	/**
	 * Method used to initialise the GUI
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
		
		add(new JLabel("fgColor:"));
		fgChooser = new JColorArea(circle.getFgColor());
		add(fgChooser);
		
		add(new JLabel("fgColor:"));
		bgChooser = new JColorArea(circle.getBgColor());
		add(bgChooser);
		
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
			circle.setFgColor(fgChooser.getCurrentColor());
			circle.setBgColor(bgChooser.getCurrentColor());
			
		} catch (NumberFormatException e){
			throw new RuntimeException("One of the point coordinates is not integer");
		}
	}
}
