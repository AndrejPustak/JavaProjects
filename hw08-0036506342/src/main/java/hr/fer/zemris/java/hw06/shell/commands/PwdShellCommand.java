package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This is the command which writes the current working directory in the shell.
 * @author Andrej
 *
 */
public class PwdShellCommand implements ShellCommand {
	
	public static final String COMMAND_NAME = "pwd";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments.length() != 0) {
			env.writeln("Pwd command does not take any arguments.");
			return ShellStatus.CONTIUNUE;
		}
		
		env.writeln(env.getCurrentDirectory().toString());
		return ShellStatus.CONTIUNUE;
	}

	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<String>();
		list.add("This command writes current directory.");
		return Collections.unmodifiableList(list);
	}

}
