package hr.fer.zemris.java.hw06.shell;

import java.util.SortedMap;

/**
 * Program MyShell represents a simple shell program which supports some simple commands.
 * Full list of commands can be displayed using the command help.
 * @author Andrej
 *
 */
public class MyShell {
	
	/**
	 * This is the method which starts the program.
	 * @param args 
	 */
	public static void main(String[] args) {
		
		EnvironmentImpl environment = new EnvironmentImpl();
		SortedMap<String, ShellCommand> commands = environment.commands();
		ShellStatus status = ShellStatus.CONTIUNUE;
		
		environment.writeln("Wellcome to MyShell v 1.0");
		
		while(status != ShellStatus.TERMINATE) {
			try {
			environment.write(String.format("%c ", environment.getPromptSymbol()));
			
			StringBuilder sb = new StringBuilder();
			sb.append(environment.readLine());
			
			while(sb.charAt(sb.length() - 1) == environment.getMorelinesSymbol()) {
				sb.setLength(sb.length() - 1);
				environment.write(String.format("%c ", environment.getMultilineSymbol()));
				sb.append(environment.readLine());
			}
			
			String input = sb.toString().strip();
			String commandName = extractName(input);
			
			if(!commands.containsKey(commandName)) {
				environment.writeln("No such command");
				continue;
			}

			String arguments = input.substring(commandName.length()).strip();
			status = commands.get(commandName).executeCommand(environment, arguments);
			}
			catch(ShellIOException ex) {
				environment.writeln(ex.getMessage());
			}
			
		}
		
	}
	
	/**
	 * This method is used to extract the name of the command from user input
	 * @param input User input
	 * @return Name of the command
	 */
	private static String extractName(String input) {
		char[] data = input.toCharArray();
		int index = 0;
		for(;index < data.length; index++) {
			if(!Character.isWhitespace(data[index])) break;
		}
		
		StringBuilder sb = new StringBuilder();
		
		for(;index < data.length; index++) {
			if(Character.isWhitespace(data[index])) break;
			sb.append(data[index]);
		}
		
		return sb.toString();
	}
	
	
}
