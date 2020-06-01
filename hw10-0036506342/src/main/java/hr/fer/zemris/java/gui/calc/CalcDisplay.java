package hr.fer.zemris.java.gui.calc;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;

/**
 * JLabel that represents the display for the calculator
 * @author Andrej
 *
 */
public class CalcDisplay extends JLabel{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Calculator model
	 */
	CalcModel calc;
	
	/**
	 * Listener for the display
	 */
	CalcValueListener listener;
	
	/**
	 * Constructor for the CalcDisplay
	 * @param calc calculator model
	 */
	public CalcDisplay(CalcModel calc) {
		this.calc = calc;
		listener = new DisplayListener(this, calc);
		calc.addCalcValueListener(listener);
		
		initGUI();
	}
	
	/**
	 * MEthod that initialises the GUI for the label
	 */
	private void initGUI() {
		setText(calc.toString());
		setHorizontalAlignment(RIGHT);
		setOpaque(true);
		setBackground(Color.YELLOW);
		setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		setFont(this.getFont().deriveFont(30f));
	}
	
	/**
	 * Implementation of CalcValueListener for the display of the calculator
	 * @author Andrej
	 *
	 */
	public class DisplayListener implements CalcValueListener{
		
		/**
		 * Label to be changed when an action happenes
		 */
		JLabel label;
		
		/**
		 * Calculator model to be listened
		 */
		CalcModel calc;
		
		/**
		 * Constructor
		 * @param label label
		 * @param calc calculator model
		 */
		public DisplayListener(JLabel label, CalcModel calc) {
			this.label = label;
			this.calc = calc;
		}
		
		@Override
		public void valueChanged(CalcModel model) {
			label.setText(calc.toString());
		}
		
	}
	
}
