package hr.fer.zemris.java.hw06.crypto;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Program Crypto is used to encrypt/decrypt. It can also be used to check if the provided sha-256 digest for
 * the file is valid. If used for encryption or decryption it takes in 3 arguments: command encrypt/decrypt file which you want to encrypt/decrypt
 * and the name of the new file. Otherwise it takes in word checksha and the name of the file.
 * 
 * @author Andrej
 *
 */
public class Crypto {
	
	/**
	 * This method starts the program
	 * @param args Arguments form the command line
	 */
	public static void main(String[] args) {
		if(args[0].toLowerCase().equals("checksha")){
			checksha(args);
		} else if(args[0].toLowerCase().equals("encrypt") || 
				args[0].toLowerCase().equals("decrypt")) {
			crypt(args);
		} else {
			System.out.println("Unknown command: " + args[0]);
		}
	}
	
	/**
	 * This command is used to encrypt/decrypt a file
	 * @param args Array of arguments given through the command line
	 */
	private static void crypt(String[] args) {
		if (args.length != 3) {
			System.out.println("Invalid argument number");
		}
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
		System.out.printf("> ");
		String keyText = sc.nextLine().strip();
		
		System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
		System.out.printf("> ");
		String ivText = sc.nextLine().strip();
		SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
		
		sc.close();
		
		Cipher cipher;
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			System.out.println(e.getMessage());
			return;
		}
		
		try {
			cipher.init(args[0].toLowerCase().equals("encrypt") ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
		} catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
			System.out.println(e.getMessage());
			return;
		} 
		
		Path source = Paths.get(args[1]);
		Path destination = Paths.get(args[2]);
		
		try (InputStream is = Files.newInputStream(source, StandardOpenOption.READ);
				OutputStream os = Files.newOutputStream(destination, StandardOpenOption.CREATE_NEW, StandardOpenOption.TRUNCATE_EXISTING)){
			byte[] buff = new byte[4096];
			
			while(true) {
				int r = is.read(buff);
				if(r < 4096) {
					os.write(cipher.doFinal(buff, 0, r));
					break;
				}
				os.write(cipher.update(buff, 0, r));
			}
			
		}
		catch(IOException | IllegalBlockSizeException | BadPaddingException ex) {
			System.out.println(ex.getMessage());
			return;
		}
		
		if(args[0].toLowerCase().equals("encrypt")) {
			System.out.println("Encryption completed. Generated file " + args[2] + " based on file " + args[1] + ".");
		} else {
			System.out.println("Decryption completed. Generated file " + args[2] + " based on file " + args[1] + ".");
		}
		
		return;
	}
	
	/**
	 * THis command is used to check the file sha-256 digest
	 * @param args Array of arguments from the command line
	 */
	private static void checksha(String[] args) {
		if (args.length != 2) {
			System.out.println("Invalid argument number");
		}
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Please provide expected sha-256 digest for " + args[1]);
		System.out.printf("> ");
		String userSha = sc.nextLine().strip();
		
		sc.close();
		
		MessageDigest sha;
		try {
			sha = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			System.out.println(e.getMessage());
			return;
		}
		
		Path path = Paths.get(args[1]);
		try (InputStream is = Files.newInputStream(path, StandardOpenOption.READ)){
			byte[] buff = new byte[4096];
			
			while(true) {
				int r = is.read(buff);
				if(r<1) break;
				
				sha.update(buff, 0, r);
			}
			
		}
		catch(IOException ex) {
			System.out.println(ex.getMessage());
			return;
		}
		
		String result = Util.bytetohex(sha.digest());
		
		if(result.equals(userSha)) {
			System.out.println("Digesting completed. Digest of " + args[1] + " matches expected digest.");
		} 
		else {
			System.out.println("Digesting completed. Digest of " + args[1] + " does not match the expected digest. Digest");
			System.out.println("was: " + result);
		}
		
		
	}
	
	
}
