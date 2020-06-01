package hr.fer.zemris.java.gui.prim;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PrimListModelTest {
	
	@Test
	public void primTest() {
		
		PrimListModel model = new PrimListModel();
		
		assertEquals(1, model.getSize());
		model.next();
		
		assertEquals(2, model.getElementAt(1));
		
		model.next();
		model.next();
		model.next();
		
		assertEquals(5, model.getSize());
		assertEquals(5, model.getElementAt(3));
		
	}
	
}
