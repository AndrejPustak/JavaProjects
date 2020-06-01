package hr.fer.zemris.lsystems.impl;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl.MyLSystem;

public class LSystemBuilderImplTest {
	
	 @Test
	 public void generateTest() {
		 LSystemBuilderImpl builder = new LSystemBuilderImpl();
		 MyLSystem system = new MyLSystem(builder);
		 builder.registerProduction('F', "F+F--F+F");
		 builder.setAxiom("F");
		 
		 assertEquals("F", system.generate(0));
		 assertEquals("F+F--F+F", system.generate(1));
		 assertEquals("F+F--F+F+F+F--F+F--F+F--F+F+F+F--F+F", system.generate(2));
	 }
}
