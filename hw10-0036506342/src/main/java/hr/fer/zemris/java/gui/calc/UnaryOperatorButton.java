package hr.fer.zemris.java.gui.calc;

import java.util.function.UnaryOperator;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.gui.calc.model.CalcModel;

/**
 * This class represents a JButton for the calculators unary operations.
 * @author Andrej
 *
 */
@SuppressWarnings("unused")
public class UnaryOperatorButton extends JButton{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Calculator model
	 */
	private CalcModel calc;
	
	/**
	 * Operator of the button
	 */
	private UnaryOperator<Double> operator;
	
	/**
	 * Inverse operator of the button
	 */
	private UnaryOperator<Double> iOperator;
	
	/**
	 * Text for the button when in normal state
	 */
	private String text;
	
	/**
	 * Text for the button when in inverse state
	 */
	private String iText;
	
	/**
	 * Boolean that keeps track of the button state
	 */
	private boolean inverse;
	
	/**
	 * Constructor for the UnaryOperatorButton
	 * @param calc calculator model
	 * @param operator operator for the button
	 * @param text text for the button when in normal state
	 * @param iOperator inverse operator of the button
	 * @param iText text for the button when in inverse state
	 */
	public UnaryOperatorButton(CalcModel calc, UnaryOperator<Double> operator, String text, UnaryOperator<Double> iOperator, String iText) {
		super();
		this.calc = calc;
		this.operator = operator;
		this.text = text;
		this.iText = iText;
		this.iOperator = iOperator;
		
		inverse = false;
		
		setText(text);
		
		addActionListener(e->{
			
			JButton source = (JButton) e.getSource();
			try {
				if(inverse)
					calc.setValue(iOperator.apply(calc.getValue()));
				else calc.setValue(operator.apply(calc.getValue()));
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(source.getParent(), ex.getMessage());
			}
		});
	}
	
	/**
	 * Constructor for the UnaryOperatorButton when inverse state doesn't exist
	 * @param calc calculator model
	 * @param operator operator of the button
	 * @param text text for the button 
	 */
	public UnaryOperatorButton(CalcModel calc, UnaryOperator<Double> operator, String text) {
		this(calc, operator, text, null, null);
	}
	
	/**
	 * Method that swaps the state of the button
	 */
	public void swapInverse() {
		if(inverse) {
			inverse = false;
			setText(text);
		}
			
		else{
			inverse = true;
			setText(iText);
		}
	}
	
	/**
	 * MEthod that checks if the button is in inverse state
	 * @return true if the button is inverse state, false otherwise
	 */
	public boolean isInverse() {
		return inverse;
	}
	
	
}
