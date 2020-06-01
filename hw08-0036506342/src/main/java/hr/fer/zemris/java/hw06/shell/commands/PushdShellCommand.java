package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.StringExtractors;

/**
 * This command pushes the current working directory and changes also changes it 
 * to the given directory.
 * It takes in one argument which is the new working directory.
 * @author Andrej
 *
 */
public class PushdShellCommand implements ShellCommand {
	
	public static final String COMMAND_NAME = "pushd";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments.length() == 0) {
			env.writeln("No arguments were given");
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
		
		Path path = Paths.get(pathName);
		if(!path.isAbsolute()) {
			path = env.getCurrentDirectory().resolve(path).normalize();
		}
		
		if(!Files.exists(path)) {
			env.writeln("Directory does not exist.");
			return ShellStatus.CONTIUNUE;
		}
		
		if(env.getSharedData("cdstack") == null) {
			env.setSharedData("cdstack", new Stack<Path>());
		}
		
		@SuppressWarnings("unchecked")
		Stack<Path> stack = (Stack<Path>) env.getSharedData("cdstack");
		stack.push(env.getCurrentDirectory());
		
		env.setCurrentDirectory(env.getCurrentDirectory().resolve(path));
		return ShellStatus.CONTIUNUE;
	}

	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<String>();
		list.add("This command pushes the current directory to the stack and");
		list.add("then puts the given directory as the working directory.");
		return Collections.unmodifiableList(list);
	}

}
