package hr.fer.zemris.java.hw06.shell;

import java.util.Collections;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw06.shell.commands.CatShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CharsetsShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CopyShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.ExitShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.HelpShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.HexdumpShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.LsShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.MkdirShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.SymbolShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.TreeShellCommand;

/**
 * Program MyShell represents a simple shell program which supports some simple commands.
 * Full list of commands can be displayed using the command help.
 * @author Andrej
 *
 */
public class MyShell {
	
	/**
	 * Implementation of Environment interface to be used to communicate with MyShell program
	 * @author Andrej
	 *
	 */
	private static class MyEnvironment implements Environment{
		private static final Character DEFAULT_MULTILINE_SYMBOL = '|';
		private static final Character DEFAULT_PROMPT_SYMBOL = '>';
		private static final Character DEFAULT_MORELINES_SYMBOL = '\\';
		
		private Character multilineSymbol;
		private Character promptSymbol;
		private Character morelinesSymbol;
		
		private SortedMap<String, ShellCommand> commandMap;
		
		Scanner sc;
		
		/**
		 * Constructor for the MyEnvironment
		 */
		public MyEnvironment() {
			setMorelinesSymbol(DEFAULT_MORELINES_SYMBOL);
			setMultilineSymbol(DEFAULT_MULTILINE_SYMBOL);
			setPromptSymbol(DEFAULT_PROMPT_SYMBOL);
			
			sc = new Scanner(System.in);
			
			commandMap = new TreeMap<>();
			
			commandMap.put("symbol", new SymbolShellCommand());
			commandMap.put("help", new HelpShellCommand());
			commandMap.put("charsets", new CharsetsShellCommand());
			commandMap.put("cat", new CatShellCommand());
			commandMap.put("ls", new LsShellCommand());
			commandMap.put("tree", new TreeShellCommand());
			commandMap.put("copy", new CopyShellCommand());
			commandMap.put("mkdir", new MkdirShellCommand());
			commandMap.put("hexdump", new HexdumpShellCommand());
			commandMap.put("exit", new ExitShellCommand());
			
			commandMap = Collections.unmodifiableSortedMap(commandMap);
			
		}
		
		@Override
		public String readLine() throws ShellIOException{
			try{
				return sc.nextLine();
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new ShellIOException("Error while reading the line");
			}
		}
		
		@Override
		public void write(String text) throws ShellIOException{
			try {
				System.out.printf("%s", text);
			}
			catch(Exception e){
				throw new ShellIOException("Error while writing");
			}
		}
		
		@Override
		public void writeln(String text) throws ShellIOException{
			try {
				System.out.println(text);
			}
			catch(Exception e){
				throw new ShellIOException("Error while writing line");
			}
		}
		
		@Override
		public SortedMap<String, ShellCommand> commands(){
			return commandMap;
		}
		
		@Override
		public Character getMultilineSymbol() {
			return multilineSymbol;
		}
		
		@Override
		public void setMultilineSymbol(Character symbol) {
			this.multilineSymbol = symbol;
		}
		
		@Override
		public Character getPromptSymbol() {
			return promptSymbol;
		}
		
		@Override
		public void setPromptSymbol(Character symbol) {
			this.promptSymbol = symbol;
		}
		
		@Override
		public Character getMorelinesSymbol() {
			return morelinesSymbol;
		}
		
		@Override
		public void setMorelinesSymbol(Character symbol) {
			this.morelinesSymbol = symbol;
		}
	}
	
	/**
	 * This is the method which starts the program.
	 * @param args 
	 */
	public static void main(String[] args) {
		
		MyEnvironment environment = new MyEnvironment();
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
