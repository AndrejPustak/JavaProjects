package hr.fer.zemris.java.gui.calc;

import java.awt.Container;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * THis class is calculator program with functionalities similar to the old windows XP calculator.
 * @author Andrej
 *
 */
public class Calculator extends JFrame{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Calculator model for the calculator
	 */
	CalculatorModel calculator;
	
	/**
	 * Default constructor
	 */
	public Calculator() {
		calculator = new CalculatorModel();
		
		setLocation(500, 500);
		setSize(800, 500);
		setTitle("Java calculator v1.0");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		initGUI();
	}
	
	/**
	 * Method that initialises the GUI of the calculator
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new CalcLayout(5));
		
		JLabel display = new CalcDisplay(calculator);
		cp.add(display, new RCPosition(1,1));
		
		//Number buttons
		cp.add(new NumberButton(calculator, 0), new RCPosition(5, 3));
		cp.add(new NumberButton(calculator, 1), new RCPosition(4, 3));
		cp.add(new NumberButton(calculator, 2), new RCPosition(4, 4));
		cp.add(new NumberButton(calculator, 3), new RCPosition(4, 5));
		cp.add(new NumberButton(calculator, 4), new RCPosition(3, 3));
		cp.add(new NumberButton(calculator, 5), new RCPosition(3, 4));
		cp.add(new NumberButton(calculator, 6), new RCPosition(3, 5));
		cp.add(new NumberButton(calculator, 7), new RCPosition(2, 3));
		cp.add(new NumberButton(calculator, 8), new RCPosition(2, 4));
		cp.add(new NumberButton(calculator, 9), new RCPosition(2, 5));
		
		//Swap sign button
		JButton neg = new JButton("+/-");
		neg.addActionListener(e->{
			JButton source = (JButton) e.getSource();
			try {
				calculator.swapSign();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(source.getParent(), ex.getMessage());
				
			}
		});
		cp.add(neg, new RCPosition(5, 4));
		
		//Point button
		JButton point = new JButton();
		point.addActionListener(e->{
			JButton source = (JButton) e.getSource();
			try {
				calculator.insertDecimalPoint();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(source.getParent(), ex.getMessage());
				
			}
			
		});
		point.setText(".");
		cp.add(point, new RCPosition(5, 5));
		
		//Unary operators
		List<UnaryOperatorButton> buttonList = new ArrayList<UnaryOperatorButton>();
		addUnaryButtons(buttonList, calculator);
		cp.add(buttonList.get(0), new RCPosition(2, 1));
		cp.add(buttonList.get(1), new RCPosition(2, 2));
		cp.add(buttonList.get(2), new RCPosition(3, 1));
		cp.add(buttonList.get(3), new RCPosition(3, 2));
		cp.add(buttonList.get(4), new RCPosition(4, 1));
		cp.add(buttonList.get(5), new RCPosition(4, 2));
		cp.add(buttonList.get(6), new RCPosition(5, 2));
		
		//Binary operators
		BinaryOperatorButton power = new BinaryOperatorButton(calculator, (d1, d2) -> Math.pow(d1, d2), "x^n", (d1,d2)->Math.pow(d1, 1.0/d2), "x^(1/n)");
		cp.add(power, new RCPosition(5, 1));
		cp.add(new BinaryOperatorButton(calculator, (d1,d2) -> (d1+d2), "+"), new RCPosition(5, 6));
		cp.add(new BinaryOperatorButton(calculator, (d1,d2) -> (d1-d2), "-"), new RCPosition(4, 6));
		cp.add(new BinaryOperatorButton(calculator, (d1,d2) -> (d1*d2), "*"), new RCPosition(3, 6));
		cp.add(new BinaryOperatorButton(calculator, (d1,d2) -> (d1/d2), "/"), new RCPosition(2, 6));
		
		//Clear and reset buttons
		JButton clear = new JButton("clr");
		clear.addActionListener(e->{
			calculator.clear();
		});
		cp.add(clear, new RCPosition(1, 7));
		JButton reset = new JButton("reset");
		clear.addActionListener(e->{
			calculator.clearAll();
		});
		cp.add(reset, new RCPosition(2, 7));
		
		//Equals button
		JButton equals = new JButton("=");
		equals.addActionListener(e->{
			JButton source = (JButton) e.getSource();
			try {
				calculator.setValue(calculator.getPendingBinaryOperation().applyAsDouble(calculator.getActiveOperand(), calculator.getValue()));
				calculator.clearActiveOperand();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(source.getParent(), ex.getMessage());
				
			}
		});
		cp.add(equals, new RCPosition(1, 6));
		
		//Push/pop buttons
		Stack<Double> stack = new Stack<Double>();
		JButton push = new JButton("push");
		push.addActionListener(e->{
			stack.push(calculator.getValue());
		});
		cp.add(push, new RCPosition(3, 7));
		JButton pop = new JButton("pop");
		pop.addActionListener(e->{
			JButton source = (JButton) e.getSource();
			try {
				double num = stack.pop();
				calculator.setValue(num);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(source.getParent(), "The stack is empty!");
			}
		});
		cp.add(pop, new RCPosition(4, 7));
		
		//Checkbox 
		JCheckBox cb = new JCheckBox("Inv");
		cb.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				buttonList.stream().forEach(b->b.swapInverse());
				power.swapInverse();
			}
		});
		cp.add(cb, new RCPosition(5, 7));
	}
	
	/**
	 * This method adds unary buttons to the list
	 * @param buttonList list of the buttons
	 * @param calculator2 calculator model
	 */
	private void addUnaryButtons(List<UnaryOperatorButton> buttonList, CalculatorModel calculator2) {
		
		buttonList.add(new UnaryOperatorButton(calculator, (d) -> 1.0 / d, "1/x", (d) -> 1.0 / d, "1/x"));
		buttonList.add(new UnaryOperatorButton(calculator, d->Math.sin(d), "sin", 
				   d->Math.asin(d), "arcsin"));
		buttonList.add(new UnaryOperatorButton(calculator, d->Math.log10(d), "log", 
				   d->Math.pow(10, d), "10^x"));
		buttonList.add(new UnaryOperatorButton(calculator, d->Math.cos(d), "cos", 
				   d->Math.acos(d), "arccos"));
		buttonList.add(new UnaryOperatorButton(calculator, d->Math.log(d), "ln", 
				   d->Math.pow(Math.E, d), "e^x"));
		buttonList.add(new UnaryOperatorButton(calculator, d->Math.tan(d), "tan", 
				   d->Math.atan(d), "arctan"));
		buttonList.add(new UnaryOperatorButton(calculator, d->1.0/Math.tan(d), "ctg", 
				   d->Math.atan(1.0 / d), "arcctg"));
		
	}
	
	/**
	 * THis method starts the program
	 * @param args this program expects no arguments
	 */
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new Calculator();
			frame.pack();
			frame.setVisible(true);
		});
		
	}
}
