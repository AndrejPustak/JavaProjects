package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This command writes all the directories currently stored on the stack.
 * @author Andrej
 *
 */
public class ListdShellCommand implements ShellCommand {
	
	public static final String COMMAND_NAME = "listd";

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
		if(stack.isEmpty()) {
			env.writeln("There is no stored directories.");
			return ShellStatus.CONTIUNUE;
		}
		
		stack.forEach((path) -> env.writeln(path.toString()));
		
		return ShellStatus.CONTIUNUE;
	}

	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<String>();
		list.add("This command writes all paths currently on stack.");
		return Collections.unmodifiableList(list);
	}

}
