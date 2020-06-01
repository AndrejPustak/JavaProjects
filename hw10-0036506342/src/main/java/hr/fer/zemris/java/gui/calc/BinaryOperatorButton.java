package hr.fer.zemris.java.gui.calc;

import java.util.function.DoubleBinaryOperator;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.gui.calc.model.CalcModel;

/**
 * This class represents a JButton for the calculators binary operations.
 * @author Andrej
 *
 */
@SuppressWarnings("unused")
public class BinaryOperatorButton extends JButton{
	
	private static final long serialVersionUID = 1L;

	/**
	 * Calculator model of the button
	 */
	private CalcModel calc;
	
	/**
	 * Operator of the button
	 */
	private DoubleBinaryOperator operator;
	/**
	 * Text for the buttons operator
	 */
	private String text;
	
	/**
	 * Boolean that keeps track if the button is in inverse mode
	 */
	private boolean inverse;
	
	/**
	 * Inverse operator of the button
	 */
	private DoubleBinaryOperator iOperator;
	/**
	 * Text for the inverse operator
	 */
	private String iText;
	
	/**
	 * Constructor for the BinaryOperatorButton
	 * @param calc calculator model 
	 * @param operator operator of the button
	 * @param text text for the operator
	 * @param iOperator inverse operator of the button
	 * @param iText text for the inverse operator
	 */
	public BinaryOperatorButton(CalcModel calc, DoubleBinaryOperator operator, String text,
			DoubleBinaryOperator iOperator, String iText) {
		super();
		this.calc = calc;
		this.operator = operator;
		this.text = text;
		this.iOperator = iOperator;
		this.iText = iText;
		
		setText(text);
		
		addActionListener(e -> {
			JButton source = (JButton) e.getSource();
			try {
				if(iOperator != null && inverse) {
					if(calc.isActiveOperandSet()) {
						calc.setActiveOperand(calc.getPendingBinaryOperation().applyAsDouble(calc.getActiveOperand(), calc.getValue()));
						calc.setPendingBinaryOperation(iOperator);
						calc.clear();
					} else {
						calc.setActiveOperand(calc.getValue());
						calc.setPendingBinaryOperation(iOperator);
						calc.clear();
					}
				} else {
					if(calc.isActiveOperandSet()) {
						calc.setActiveOperand(calc.getPendingBinaryOperation().applyAsDouble(calc.getActiveOperand(), calc.getValue()));
						calc.setPendingBinaryOperation(operator);
						calc.clear();
					} else {
						calc.setActiveOperand(calc.getValue());
						calc.setPendingBinaryOperation(operator);
						calc.clear();
					}
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(source.getParent(), ex.getMessage());
			}
		});
	}
	
	/**
	 * Constructor for the BinaryOperatorButton that doesn't have a inverse operator
	 * @param calc calculator model
	 * @param operator operator of the button
	 * @param text text for the operator
	 */
	public BinaryOperatorButton(CalcModel calc, DoubleBinaryOperator operator, String text) {
		this(calc, operator, text, null, null);
	}
	
	/**
	 * This method swaps buttons mode 
	 */
	public void swapInverse() {
		if(inverse) {
			inverse = false;
			setText(text);
		}
		else{
			if(iText != null) setText(iText);
			inverse = true;
		}
	}
}
