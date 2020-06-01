package hr.fer.zemris.java.hw06.crypto;

/**
 * Class Util is a class which has some public utility methods.
 * @author Andrej
 *
 */
public class Util {
	
	/**
	 * This method takes in a string and turns it into byte array.
	 * @param keyText Text you wish to turn into byte array
	 * @return Byte array
	 */
	public static byte[] hextobyte(String keyText) {
		if(keyText.length() % 2 == 1) {
			throw new IllegalArgumentException("KeyText has odd number of characters");
		}
		if(keyText.length() == 0) {
			return new byte[0];
		}
		
		char[] chars = keyText.toCharArray();
		byte[] bytes = new byte[keyText.length() / 2];
		int b = 0;
		
		
		for(int i = 0; i < keyText.length(); i+=2) {
			String byteS = "";
			byteS += getBits(Character.toLowerCase(chars[i])) + 
					 getBits(Character.toLowerCase(chars[i+1]));
			byte byteN = 0;
			for(int j = 7; j>=0; j--) {
				if (byteS.charAt(j)=='1') {
					byteN += Math.pow(2, 7 - j);
				}
			}
			
			bytes[b++] = byteN;
		}
		
		return bytes;
	}
	
	/**
	 * This method takes in a byte array and turns into string.
	 * @param bytes Array of bytes you wish to turn into string.
	 * @return String which is the result of transforming the byte array
	 */
	public static String bytetohex(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < bytes.length; i++) {
			StringBuilder bits = new StringBuilder();
			int byteI;
			if (bytes[i] < 0) byteI = bytes[i] + 256;
			else byteI = bytes[i];
			
			while(byteI > 0) {
				if(byteI % 2 == 1) {
					bits.append("1");
				}
				else bits.append("0");
				byteI /= 2;
			}
			
			for (int j = bits.length(); j < 8 ; j++) {
				bits.append("0");
			}
			bits.reverse();
			
			sb.append(getHex(bits.substring(0, 4)));
			sb.append(getHex(bits.substring(4)));
		}
		
		return sb.toString();
	}
	
	/**
	 * THis method takes in a string of 4 bits and returns a hex sign
	 * @param s A string of bits
	 * @return Hex sign the 4 bits represents
	 */
	private static String getHex(String s) {
		switch(s) {
		case "0000":
			return "0";
		case "0001":
			return "1";
		case "0010":
			return "2";
		case "0011":
			return "3";
		case "0100":
			return "4";
		case "0101":
			return "5";
		case "0110":
			return "6";
		case "0111":
			return "7";
		case "1000":
			return "8";
		case "1001":
			return "9";
		case "1010":
			return "a";
		case "1011":
			return "b";
		case "1100":
			return "c";
		case "1101":
			return "d";
		case "1110":
			return "e";
		case "1111":
			return "f";
		default:
			throw new IllegalArgumentException("Not valid");
		}
	}
	
	/**
	 * This method takes in a hex sign and returns the corresponding 4 bit string
	 * @param s Character in the hex system 
	 * @return String of 4 bits 
	 */
	private static String getBits(char s) {
		switch(s) {
		case '0':
			return "0000";
		case '1':
			return "0001";
		case '2':
			return "0010";
		case '3':
			return "0011";
		case '4':
			return "0100";
		case '5':
			return "0101";
		case '6':
			return "0110";
		case '7':
			return "0111";
		case '8':
			return "1000";
		case '9':
			return "1001";
		case 'a':
			return "1010";
		case 'b':
			return "1011";
		case 'c':
			return "1100";
		case 'd':
			return "1101";
		case 'e':
			return "1110";
		case 'f':
			return "1111";
		default:
			throw new IllegalArgumentException("Not valid character");
		}
		
	}
}
