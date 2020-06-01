package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ObjectMultistackTest {
	
	@Test
	void pushEmptyTest() {
		ObjectMultistack stack = new ObjectMultistack();
		
		assertTrue(stack.isEmpty("jedan"));
		stack.push("jedan", new ValueWrapper(Integer.valueOf(1)));
		assertTrue(!stack.isEmpty("jedan"));
	}
	
	@Test
	void pushPopTest() {
		ObjectMultistack stack = new ObjectMultistack();
		
		assertTrue(stack.isEmpty("jedan"));
		stack.push("jedan", new ValueWrapper(Integer.valueOf(1)));
		assertEquals(1, stack.pop("jedan").getValue());
		assertTrue(stack.isEmpty("jedan"));
	}
	
	@Test
	void pushPeekTest() {
		ObjectMultistack stack = new ObjectMultistack();
		
		assertTrue(stack.isEmpty("jedan"));
		stack.push("jedan", new ValueWrapper(Integer.valueOf(1)));
		assertEquals(1, stack.peek("jedan").getValue());
		assertTrue(!stack.isEmpty("jedan"));
	}
}
