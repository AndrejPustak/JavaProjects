package hr.fer.zemris.java.hw06.shell;

import java.util.List;

/**
 * This interface represents a shell command.
 * @author Andrej
 *
 */
public interface ShellCommand {
	
	/**
	 * This command executes the command
	 * @param env Environment through which the command communicates with the shell
	 * @param arguments text after the command name
	 * @return ShellStatus after the command.
	 */
	ShellStatus executeCommand(Environment env, String arguments);
	
	/**
	 * This command returns the command name.
	 * @return Command name
	 */
	String getCommandName();
	
	/**
	 * This command returns the command description as an unmodifiable list.
	 * @return Command description.
	 */
	List<String> getCommandDescription();
}
