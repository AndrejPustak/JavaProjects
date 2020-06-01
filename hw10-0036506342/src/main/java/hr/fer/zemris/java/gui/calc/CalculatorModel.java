package hr.fer.zemris.java.gui.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.DoubleBinaryOperator;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

/**
 * Calculator model that implements CalcModel
 * @author Andrej
 *
 */
public class CalculatorModel implements CalcModel{
	
	/**
	 * Boolean that keeps track if the calculator is editable
	 */
	private boolean editable;
	
	/**
	 * Boolean that keeps track if the number is negative
	 */
	private boolean negative;
	
	/**
	 * Variable that keeps the number as a string
	 */
	private String stringNumber;
	
	/**
	 * Variable that keeps the number as a double
	 */
	private double doubleNumber;
	
	/**
	 * Variable that keeps the active operand of the current operation
	 */
	private Double activeOperand;
	
	/**
	 * Pending operatio of the calculator
	 */
	private DoubleBinaryOperator pendingOperation;
	
	/**
	 * List of listeners to this calc model
	 */
	private List<CalcValueListener> listeners;
	
	/**
	 * Constructor for the CalculatorModel
	 */
	public CalculatorModel() {
		editable = true;
		negative = false;
		
		stringNumber = "";
		doubleNumber = 0.0;
		activeOperand = null;
		pendingOperation = null;
		
		listeners = new ArrayList<CalcValueListener>();
	}
	
	@Override
	public void addCalcValueListener(CalcValueListener l) {
		listeners.add(Objects.requireNonNull(l));
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		listeners.remove(l);
	}

	@Override
	public double getValue() {
		if(negative)
			return doubleNumber*(-1);
		else return doubleNumber;
	}

	@Override
	public void setValue(double value) {
		doubleNumber = Math.abs(value);
		stringNumber = Double.toString(doubleNumber);
		if(value<0)
			negative = true;
		editable = false;
		
		notifyListeners();
		
	}

	@Override
	public boolean isEditable() {
		return editable;
	}

	@Override
	public void clear() {
		stringNumber = "";
		doubleNumber = 0.0;
		negative = false;
		
		editable = true;
		
		notifyListeners();
		
	}

	@Override
	public void clearAll() {
		stringNumber = "";
		doubleNumber = 0.0;
		negative = false;
		
		editable = true;
		
		activeOperand = null;
		pendingOperation = null;
		
		notifyListeners();
		
	}

	@Override
	public void swapSign() throws CalculatorInputException {
		if(!editable)
			throw new CalculatorInputException("Calculator in not editable");
		
		if(negative)
			negative = false;
		else negative = true;
		
		notifyListeners();
		
	}

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if(!editable)
			throw new CalculatorInputException("Calculator in not editable");
		if(stringNumber.length() == 0)
			throw new CalculatorInputException("Can't add decimal point when no numbers havent been entered");
		if(stringNumber.contains("."))
			throw new CalculatorInputException("Number already contains a decimal point");
		
		stringNumber += ".";
		
		notifyListeners();
		
	}

	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if(!editable)
			throw new CalculatorInputException("Calculator in not editable");
		
		if(digit < 0 || digit > 9)
			throw new IllegalArgumentException("Digit is not a number from 0 to 9");
		
		if(stringNumber.equals("0") && digit == 0)
			return;
		
		if(stringNumber.equals("0"))
			stringNumber = "" + digit;
		else 
			stringNumber += digit;
		
		double num = Double.parseDouble(stringNumber);
		
		if (num == Double.POSITIVE_INFINITY) {
			stringNumber = stringNumber.substring(0, stringNumber.length()-1);
			throw new CalculatorInputException("Number is too big to show");
		}
			
		doubleNumber = num;
		notifyListeners();
		
	}

	@Override
	public boolean isActiveOperandSet() {
		return activeOperand != null;
	}

	@Override
	public double getActiveOperand() throws IllegalStateException {
		if(!isActiveOperandSet())
			throw new IllegalStateException("Active operand is not set");
		return activeOperand.doubleValue();
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = Double.valueOf(activeOperand);
	}

	@Override
	public void clearActiveOperand() {
		activeOperand = null;
		pendingOperation = null;
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		if(pendingOperation == null)
			return null;
		
		return pendingOperation;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		pendingOperation = op;
		
	}
	
	@Override
	public String toString() {
		if(stringNumber.length() == 0) {
			if(negative)
				return "-0";
			else return "0";
		}
		
		if(negative)
			return "-" + stringNumber;
		else return stringNumber;
	}
	
	/**
	 * This method notifies all listeners that a value change has happened
	 */
	private void notifyListeners() {
		listeners.stream().forEach(l->l.valueChanged(this));
	}

}
