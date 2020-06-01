package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This command takes in one or two argument. The fist one should be a symbol name. If the second one is not given
 * the command simply displays the symbol with the given name. If the second one is given the symbol with that name is 
 * changed to the new symbol.
 * @author Andrej
 *
 */
public class SymbolShellCommand implements ShellCommand{
	
	public static final String COMMAND_NAME = "symbol";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments.strip().length() == 0) {
			env.writeln("No directory given");
			return ShellStatus.CONTIUNUE;
		}
		
		String[] args = arguments.split("\\s+");
		
		if(args.length != 2 && args.length != 1) {
			env.writeln("Invalid argument number");
			return ShellStatus.CONTIUNUE;
		}
		
		if(args.length == 1) {
			if(getSymbol(env, args[0])) {
				return ShellStatus.CONTIUNUE;
			}else {
				env.writeln("Unrecognized symbol name");
				return ShellStatus.CONTIUNUE;
			}
		}
		
		if(args.length == 2) {
			if(setSymbol(env, args[0], args[1])) {
				return ShellStatus.CONTIUNUE;
			} else {
				if(args[1].length() != 1) {
					env.writeln("New symbols' name length is not 1");
				}else env.writeln("Unrecognized symbol name");
				return ShellStatus.CONTIUNUE;
			}
		}
		return null;
		
	}
	
	/**
	 * This command is used to set the symbol to the new symbol
	 * @param env environment
	 * @param name name of the symbol
	 * @param value value of the new symbol
	 * @return true if the symbol change was successful, false otherwise.
	 */
	private boolean setSymbol(Environment env, String name, String value) {
		if(value.length() != 1) {
			return false;
		}
		
		Character symbol = value.charAt(0);
		
		switch(name) {
		case "PROMPT":
			env.write(String.format("Symbol for PROMPT changed from \'%c\' to \'%c\'%n", env.getPromptSymbol(), symbol));
			env.setPromptSymbol(symbol);
			return true;
		case "MULTILINE":
			env.write(String.format("Symbol for MULTILINE changed from \'%c\' to \'%c\'%n", env.getMultilineSymbol(), symbol));
			env.setMultilineSymbol(symbol);
			return true;
		case "MORELINES":
			env.write(String.format("Symbol for MORELINES changed from \'%c\' to \'%c\'%n", env.getMorelinesSymbol(), symbol));
			env.setMorelinesSymbol(symbol);
			return true;
		default:
			return false;
		}
	}
	
	/**
	 * This command writes the current value of the given symbol
	 * @param env environment
	 * @param name name of the symbol
	 * @return true if it was successful, false otherwise
	 */
	private boolean getSymbol(Environment env, String name) {
		switch(name) {
		case "PROMPT":
			env.write(String.format("Symbol for PROMPT is \'%c\'%n", env.getPromptSymbol()));
			return true;
		case "MULTILINE":
			env.write(String.format("Symbol for MULTILINE is \'%c\'%n", env.getMultilineSymbol()));
			return true;
		case "MORELINES":
			env.write(String.format("Symbol for MORELINES is \'%c\'%n", env.getMorelinesSymbol()));
			return true;
		default:
			return false;
		}
	}

	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<String>();
		list.add("Changes the symbols for PROMPT, MORELINE, MULTILINE");
		return Collections.unmodifiableList(list);
	}
	
}
