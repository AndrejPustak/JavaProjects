package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
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
 * This command takes in a directory name and creates the given directory structure.
 * @author Andrej
 *
 */
public class MkdirShellCommand implements ShellCommand {
	
	public static final String COMMAND_NAME = "mkdir";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments.length() == 0) {
			env.writeln("No directory given");
			return ShellStatus.CONTIUNUE;
		}
		
		
		String pathName = "";
		
		if(arguments.charAt(0) == '\"' ) {
			pathName = StringExtractors.extractPathNameInQuotation(arguments);
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
		
		try {
			Files.createDirectories(path);
		} catch (IOException e) {
			env.writeln("Error while crating the directory");
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
		list.add("Creates a directory if it doesnt exitst.");
		return Collections.unmodifiableList(list);
	}

}
