package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.StringExtractors;

/**
 * THis command dumps hex values of the file to shell. All characters whose byte value is less than 32
 * or greater than 127 is replaced by '.'
 * @author Andrej
 *
 */
public class HexdumpShellCommand implements ShellCommand {
	
	public static final String COMMAND_NAME = "hexdump";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments.length() == 0) {
			env.writeln("No file given.");
			return ShellStatus.CONTIUNUE;
		}
		
		
		String pathName = "";
		
		if(arguments.charAt(0) == '\"' ) {
			pathName = StringExtractors.extractInQuotation(arguments);
			if(pathName == null) {
				env.writeln("Invalid path");
				return ShellStatus.CONTIUNUE;
			}
			
			arguments = arguments.substring(pathName.length() + 2).strip();
		} else {
			pathName = StringExtractors.extractUntilWhitespace(arguments);
			arguments = arguments.substring(pathName.length()).strip();
		}
		
		if(arguments.length() != 0) {
			env.writeln("Multiple arguments given. Was expecting one.");
			return ShellStatus.CONTIUNUE;
		}
		
		Path path = Paths.get(pathName);
		if(!path.isAbsolute()) {
			path = env.getCurrentDirectory().resolve(path).normalize();
		}
		
		if(Files.isDirectory(path)) {
			env.writeln("Was given a direcotry, not a file.");
			return ShellStatus.CONTIUNUE;
		}
		
		try(InputStream is = Files.newInputStream(path)){
			byte[] buff = new byte[4096];
			
			while(true) {
				int r = is.read(buff);
				if(r < 1) {
					break;
				}
				
				StringBuilder sb = new StringBuilder();
				int i;
				
				for(i = 0; i < r/16; i++) {
					sb.setLength(0);
					sb.append(intToHex(i*16));
					sb.append(": ");
					for(int j = 0; j<16; j++) {
						if(j==7) {
							String hex = String.format("%2x|", buff[i*16+j]);
							if(hex.charAt(0) == ' ') {
								hex = "0"+hex.substring(1);
							}
							sb.append(hex);
						}
						else {
							String hex = String.format("%2x ", buff[i*16+j]);
							if(hex.charAt(0) == ' ') {
								hex = "0"+hex.substring(1);
							}
							sb.append(hex);
						}
					}
					sb.append("| ");
					
					byte[] bytes = new byte[16];
					for(int j = 0; j<16; j++) {
						if(buff[i*16+j] < 32 || buff[i*16+j] > 127) {
							bytes[j] = 46;
						} else bytes[j] = buff[i*16+j];
					}
					
					sb.append(new String(bytes, "UTF-8"));
					env.writeln(sb.toString());
				}
				if(r % 16 > 0) {
					sb.setLength(0);
					sb.append(intToHex(i*16));
					sb.append(": ");
					
					for(int j = 0; j<16; j++) {
						if(j < r % 16) {
							String hex = String.format("%2x", buff[i*16+j]);
							if(hex.charAt(0) == ' ') {
								hex = "0"+hex.substring(1);
							}
							sb.append(hex);
						}
						else sb.append("  ");
					
						if(j == 7) sb.append("|");
						else sb.append(" ");
					}
					sb.append("| ");
					
					byte[] bytes = new byte[r % 16];
					for(int j = 0; j<16; j++) {
						if(j < r % 16) {
							if(buff[i*16+j] < 32 || buff[i*16+j] > 127) {
								bytes[j] = 46;
							} else bytes[j] = buff[i*16+j];
						}
						
					}
					sb.append(new String(bytes, "UTF-8"));
					env.writeln(sb.toString());
				}
			}
			
			return ShellStatus.CONTIUNUE;
			
		}
		catch(IOException e) {
			env.writeln("Error while rading the direcory");
			return ShellStatus.CONTIUNUE;
		}
	}
	
	/**
	 * This method takes in a integer and turns it into hex value.
	 * @param num Number
	 * @return hex value
	 */
	private String intToHex(int num) {
		StringBuilder sb = new StringBuilder();
		String hex = Integer.toHexString(num);
		
		for(int i = 0; i < 8 - hex.length(); i++) {
			sb.append("0");
		}
		
		sb.append(hex);
		return sb.toString();
		
	}

	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<String>();
		list.add("This command produces hex-output of a file.");
		return Collections.unmodifiableList(list);
	}

}
