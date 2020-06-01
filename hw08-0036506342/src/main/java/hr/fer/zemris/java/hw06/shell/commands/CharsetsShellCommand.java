package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This command is used to display all available charsets.
 * @author Andrej
 *
 */
public class CharsetsShellCommand implements ShellCommand{
	
	public static final String COMMAND_NAME = "charsets";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		for(String charset : Charset.availableCharsets().keySet()) {
			env.writeln(charset);
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
		list.add("This command prints all avaliable charsets.");
		return Collections.unmodifiableList(list);
	}

}
