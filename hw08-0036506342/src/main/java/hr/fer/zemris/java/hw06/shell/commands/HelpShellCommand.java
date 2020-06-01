package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This command is used to display command description of a given command. If no command is given
 * descriptions of all commands is displayed.
 * @author Andrej
 *
 */
public class HelpShellCommand implements ShellCommand{
	
	public static final String COMMAND_NAME = "help";
	public static final int COMMAND_SPACING = 5;

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments.length() == 0) {
			writeAllCommands(env);
		} else {
			if(!env.commands().containsKey(arguments)) {
				env.writeln("No such command extists: " + arguments);
				return ShellStatus.CONTIUNUE;
			}
			
			writeCommmand(arguments, env, arguments.length());
		}
		
		return ShellStatus.CONTIUNUE;
		
	}
	
	/**
	 * This command writes all commands and their description to the shell.
	 * @param env Environment
	 */
	private void writeAllCommands(Environment env){
		int biggest = 0;
		for (String com : env.commands().keySet()) {
			if(com.length() > biggest) {
				biggest = com.length();
			}
		}
		
		for (String com : env.commands().keySet()) {
			writeCommmand(com, env, biggest);
		}
	}
	
	/**
	 * This command writes one command and its description to shell
	 * @param com Command name
	 * @param env environment
	 * @param biggest Size of the biggest command
	 */
	public void writeCommmand(String com, Environment env, int biggest) {
		StringBuilder sb = new StringBuilder("");
		sb.append(com);
		for (int i = com.length(); i < biggest + COMMAND_SPACING; i++) {
			sb.append(" ");
		}
		sb.append(env.commands().get(com).getCommandDescription().get(0));
		env.writeln(sb.toString());
		
		if(env.commands().get(com).getCommandDescription().size() > 1) {
			for(int i = 1; i < env.commands().get(com).getCommandDescription().size(); i++) {
				sb.setLength(0);
				
				for (int j = 0; j < biggest + COMMAND_SPACING; j++) {
					sb.append(" ");
				}
				
				sb.append(env.commands().get(com).getCommandDescription().get(i));
				env.writeln(sb.toString());
			}
		}
	}

	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<String>();
		list.add("This command prints all avaliable commans.");
		return Collections.unmodifiableList(list);
	}
	
}
