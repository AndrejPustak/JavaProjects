package hr.fer.zemris.java.gui.layout;

import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.swing.JButton;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.CalcLayoutException;
import hr.fer.zemris.java.gui.layouts.RCPosition;

public class CalcLayoutTests {
	
	@Test
	public void addLayoutComponentTest() {
		CalcLayout layout = new CalcLayout();
		JButton comp1 = new JButton("one");
		JButton comp2 = new JButton("two");
		
		assertThrows(CalcLayoutException.class, ()->layout.addLayoutComponent(comp1, new RCPosition(0, 1)));
		assertThrows(CalcLayoutException.class, ()->layout.addLayoutComponent(comp1, new RCPosition(6, 1)));
		
		assertThrows(CalcLayoutException.class, ()->layout.addLayoutComponent(comp1, new RCPosition(4, 0)));
		assertThrows(CalcLayoutException.class, ()->layout.addLayoutComponent(comp1, new RCPosition(4, 8)));
		
		assertThrows(CalcLayoutException.class, ()->layout.addLayoutComponent(comp1, new RCPosition(1, 0)));
		assertThrows(CalcLayoutException.class, ()->layout.addLayoutComponent(comp1, new RCPosition(1, 2)));
		assertThrows(CalcLayoutException.class, ()->layout.addLayoutComponent(comp1, new RCPosition(1, 5)));
		assertThrows(CalcLayoutException.class, ()->layout.addLayoutComponent(comp1, new RCPosition(1, 8)));
		
		assertThrows(CalcLayoutException.class, ()->layout.addLayoutComponent(comp1, new RCPosition(1, 0)));
		
		layout.addLayoutComponent(comp1, new RCPosition(1, 1));
		assertThrows(CalcLayoutException.class, ()->layout.addLayoutComponent(comp2, new RCPosition(1, 1)));
	}
}
