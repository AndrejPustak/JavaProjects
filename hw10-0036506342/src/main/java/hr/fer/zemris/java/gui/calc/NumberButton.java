package hr.fer.zemris.java.gui.calc;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.gui.calc.model.CalcModel;

/**
 * JButton for the calculator that represents a number
 * @author Andrej
 *
 */
public class NumberButton extends JButton{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Calculator model
	 */
	CalcModel calc;
	
	/**
	 * Value of the button
	 */
	private int value;
	
	/**
	 * Constructor for the NumberButton
	 * @param calc calculator model
	 * @param value number value of the button
	 */
	public NumberButton(CalcModel calc, int value) {
		this.calc = calc;
		this.value = value;
		
		setText(Integer.toString(value));
		setFont(this.getFont().deriveFont(30f));
		
		addActionListener(e ->{
			NumberButton button = (NumberButton) e.getSource();
			button.getCalc().insertDigit(button.getValue());

			try {
				button.getCalc().insertDigit(button.getValue());
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(button.getParent(), ex.getMessage());
			}
		});
	}
	
	/**
	 * Getter for the value of the button
	 * @return value of the button
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * Getter for the calculator model
	 * @return calculator model of the button
	 */
	public CalcModel getCalc() {
		return calc;
	}
	
}
