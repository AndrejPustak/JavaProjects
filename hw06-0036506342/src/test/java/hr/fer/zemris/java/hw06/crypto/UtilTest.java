package hr.fer.zemris.java.hw06.crypto;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class UtilTest {
	
	@Test
	void hexToByteOddTest() {
		String s = "abc";
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte(s));
	}
	
	@Test
	void hexToByteInvCharTest() {
		String s = "abch";
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte(s));
	}
	
	@Test
	void hexToByteZeroTest() {
		String s = "";
		assertEquals(0, Util.hextobyte(s).length);
	}
	
	@Test
	void hexToByteTest() {
		String s = "01aE22";
		byte[] bytes = new byte[] {1, -82, 34};
		assertEquals(bytes[0], Util.hextobyte(s)[0]);
		assertEquals(bytes[1], Util.hextobyte(s)[1]);
		assertEquals(bytes[2], Util.hextobyte(s)[2]);
	}
	
	@Test
	void byteToHexTest() {
		String s = "01ae22";
		byte[] bytes = new byte[] {1, -82, 34};
		assertEquals(s, Util.bytetohex(bytes));
	}
	
}

