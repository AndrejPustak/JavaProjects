package hr.fer.zemris.java.hw06.shell;

import java.nio.file.Path;
import java.util.SortedMap;

/**
 * Interface Environment represents an environment through which the commands 
 * communicate with the shell.
 * @author Andrej
 *
 */
public interface Environment {
	
	/**
	 * This commands reads one line from the shell.
	 * @return Text which command read from the shell.
	 * @throws ShellIOException If the reading failed.
	 */
	String readLine() throws ShellIOException;
	
	/**
	 * This command writes the given text to the shell.
	 * @param text Text you wish to write
	 * @throws ShellIOException If the writing failed
	 */
	void write(String text) throws ShellIOException;
	
	/**
	 * This command writes one line into the shell.
	 * @param text Text you wish to write.
	 * @throws ShellIOException If the writing failed.
	 */
	void writeln(String text) throws ShellIOException;
	
	/**
	 * This command returns all available commands as an unmodifiable map.
	 * @return Map of all the available commands
	 */
	SortedMap<String, ShellCommand> commands();
	
	/**
	 * Getter for the multiline symbol
	 * @return current multiline symbol
	 */
	Character getMultilineSymbol();
	
	/**
	 * Setter for the multiline symbol
	 * @param symbol new multiline symbol
	 */
	void setMultilineSymbol(Character symbol);
	
	/**
	 * Getter for the prompt symbol
	 * @return Current prompt symbol
	 */
	Character getPromptSymbol();
	
	/**
	 * Setter for the prompt symbol
	 * @param symbol new prompt symbol
	 */
	void setPromptSymbol(Character symbol);
	
	/**
	 * Getter for the morelines symbol
	 * @return Current morelines symbol
	 */
	Character getMorelinesSymbol();
	
	/**
	 * Setter for the morelines symbol
	 * @param symbol new morelines symbol
	 */
	void setMorelinesSymbol(Character symbol);
	
	/**
	 * This method returns the current working directory.
	 * @return Path to the current working directory
	 */
	Path getCurrentDirectory();
	
	/**
	 * This method sets the current working directory to the given one.
	 * @param path Path to the new working directory
	 */
	void setCurrentDirectory(Path path);
	
	/**
	 * This method returns the shared data stored with the given key.
	 * @param key Key of the shared data you want to get
	 * @return Shared data stored with the given key
	 */
	Object getSharedData(String key);
	void setSharedData(String key, Object value);

}
