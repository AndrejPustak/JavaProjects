package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.StringExtractors;

/**
 * This command is used to copy a file to a location. It takes in two arguments. First one should be path to
 * a file. The second one should be path to a new file and if the second argument is a directory, a new file
 * is created in the given directory with original file name.
 * @author Andrej
 *
 */
public class CopyShellCommand implements ShellCommand {
	
	public static final String COMMAND_NAME = "tree";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments.length() == 0) {
			env.writeln("No arguments were given");
			return ShellStatus.CONTIUNUE;
		}
		
		String sourcePath = "";
		String destinationPath = "";
		
		if(arguments.charAt(0) == '\"' ) {
			sourcePath = StringExtractors.extractInQuotation(arguments);
			if(sourcePath == null) {
				env.writeln("Invalid path");
				return ShellStatus.CONTIUNUE;
			}
			
			arguments = arguments.substring(sourcePath.length() + 2).strip();
		} else {
			sourcePath = StringExtractors.extractUntilWhitespace(arguments);
			arguments = arguments.substring(sourcePath.length()).strip();
		}
		
		if(arguments.length() == 0) {
			env.writeln("One argument given. Was expecting two.");
			return ShellStatus.CONTIUNUE;
		}
		
		if(arguments.charAt(0) == '\"' ) {
			destinationPath = StringExtractors.extractInQuotation(arguments);
			if(destinationPath == null) {
				env.writeln("Invalid path");
				return ShellStatus.CONTIUNUE;
			}
			
			arguments = arguments.substring(destinationPath.length() + 2).strip();
		} else {
			destinationPath = StringExtractors.extractUntilWhitespace(arguments);
			arguments = arguments.substring(destinationPath.length()).strip();
		}
		
		if(arguments.length() != 0) {
			env.writeln("More than two arguments given.");
			return ShellStatus.CONTIUNUE;
		}
		
		Path source = Paths.get(sourcePath);
		if(!source.isAbsolute()) {
			source = env.getCurrentDirectory().resolve(source).normalize();
		}
		
		Path destination = Paths.get(destinationPath);
		if(!destination.isAbsolute()) {
			destination = env.getCurrentDirectory().resolve(destination).normalize();
		}
		
		if(Files.isDirectory(source)) {
			env.writeln("First argument is a directory.");
			return ShellStatus.CONTIUNUE;
		}
		
		if(Files.isDirectory(destination)) {
			destination = destination.resolve(source.getFileName());
		}
		
		if(Files.exists(destination)) {
			env.writeln("File already exists. Overwrite (yes/no):");
			env.write(String.format("%c ", env.getPromptSymbol()));
			String answer = env.readLine().strip();
			
			if(answer.equals("no")) {
				env.writeln("Stopping command.");
				return ShellStatus.CONTIUNUE;
			}
			
			env.writeln("Copying file.");
		}
		
		fileCopy(source, destination);
		
		return ShellStatus.CONTIUNUE;
	}
	
	/**
	 * This method is used to copy a file from source to destination.
	 * @param source path to source file
	 * @param destination destination path
	 */
	private void fileCopy(Path source, Path destination) {
		
		try (InputStream is = Files.newInputStream(source, StandardOpenOption.READ);
				OutputStream os = Files.newOutputStream(destination, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)){
			byte[] buff = new byte[4096];
			
			while(true) {
				int r = is.read(buff);
				if(r < 1) {
					break;
				}
				os.write(buff, 0, r);
			}
			
		}
		catch(IOException ex) {
			throw new ShellIOException("Error while copying file");
		}
		
	}

	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<String>();
		list.add("Copies one file to selected destination");
		return Collections.unmodifiableList(list);
	}

}
