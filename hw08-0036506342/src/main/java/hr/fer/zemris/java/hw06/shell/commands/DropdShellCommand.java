package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class DropdShellCommand implements ShellCommand {
	
	public static final String COMMAND_NAME = "dropd";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments.length() != 0) {
			env.writeln("This command takes no arguments.");
			return ShellStatus.CONTIUNUE;
		}
		
		if(env.getSharedData("cdstack") == null) {
			env.writeln("Stack does not exist.");
			return ShellStatus.CONTIUNUE;
		}
		
		@SuppressWarnings("unchecked")
		Stack<Path> stack = (Stack<Path>) env.getSharedData("cdstack");
		
		Path path;
		try {
			path = stack.pop();
		}
		catch (EmptyStackException e) {
			env.writeln("The stack is empty.");
			return ShellStatus.CONTIUNUE;
		}
		
		if(!Files.exists(path)) {
			env.writeln("Directory does not longer exist.");
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
		list.add("This command removes the top directory from the stack");
		return Collections.unmodifiableList(list);
	}

}
