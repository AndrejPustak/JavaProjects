package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * A utility class to be used by servlets.
 * @author Andrej
 *
 */
public class ServletUtil {
	
	/**
	 * THis method calculates password hash based on SHA-1
	 * @param password password
	 * @return password hash
	 */
	public static String getPasswordHash(String password) {
		MessageDigest digest = null;
		
		try {
			digest = MessageDigest.getInstance("SHA-1");
		} catch(NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		}
		
		byte[] bytes = digest.digest(password.getBytes());
		return byteToHex(bytes);
	}
	
	/**
	 * This method is used to get a hex string from byte array
	 * @param bytes byte array
	 * @return string in hex from
	 */
	public static String byteToHex(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		
		for (byte b : bytes) {
			sb.append(String.format("%02x", b));
		}
		
		return sb.toString();
	}
}
