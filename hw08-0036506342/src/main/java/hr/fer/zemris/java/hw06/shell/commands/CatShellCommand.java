package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
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
 * This command prints file content into the shell. It takes in two arguments, first one is mandatory : a path to the file.
 * The second one is optional : charset to be used when interpreting the file. If not provided default charset is used.
 * @author Andrej
 *
 */
public class CatShellCommand implements ShellCommand {
	
	public static final String COMMAND_NAME = "cat";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments.length() == 0) {
			env.writeln("No arguments were given");
			return ShellStatus.CONTIUNUE;
		}
		
		String pathName = "";
		String charsetName = "";
		
		
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
		
		if(arguments.length() > 0) {
			charsetName = StringExtractors.extractUntilWhitespace(arguments);
		}
		
		Charset charset;
		if(charsetName.length() > 1) {
			if(!Charset.availableCharsets().containsKey(charsetName)) {
				env.writeln("Unknown charset name");
				return ShellStatus.CONTIUNUE;
			}
			charset = Charset.forName(charsetName);
		} else {
			charset = Charset.defaultCharset();
		}
		
		Path path = Paths.get(pathName);
		if(!path.isAbsolute()) {
			path = env.getCurrentDirectory().resolve(path).normalize();
		}
		
		try {
			for(String line : Files.readAllLines(path, charset)) {
				env.writeln(line);
			}
		}
		catch (IOException | UncheckedIOException e) {
			env.writeln("Error while reading the file");
			return ShellStatus.CONTIUNUE;
		}
		
		return ShellStatus.CONTIUNUE;
	}

	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<String>();
		list.add("This command opens given file and writes its content to console.");
		list.add("to console.");
		return Collections.unmodifiableList(list);
	}

}
