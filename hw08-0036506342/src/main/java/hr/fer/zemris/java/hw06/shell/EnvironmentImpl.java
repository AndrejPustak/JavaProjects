package hr.fer.zemris.java.hw06.shell;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw06.shell.commands.CatShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CdShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CharsetsShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CopyShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.DropdShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.ExitShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.HelpShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.HexdumpShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.ListdShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.LsShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.MassrenameShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.MkdirShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.PopdShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.PushdShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.PwdShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.SymbolShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.TreeShellCommand;

public class EnvironmentImpl implements Environment {

	private static final Character DEFAULT_MULTILINE_SYMBOL = '|';
	private static final Character DEFAULT_PROMPT_SYMBOL = '>';
	private static final Character DEFAULT_MORELINES_SYMBOL = '\\';
	
	private Character multilineSymbol;
	private Character promptSymbol;
	private Character morelinesSymbol;
	
	private Path currentDir;
	
	private SortedMap<String, ShellCommand> commandMap;
	private Map<String, Object> sharedData;
	
	Scanner sc;
	
	/**
	 * Constructor for the MyEnvironment
	 */
	public EnvironmentImpl() {
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
		
		commandMap.put("pwd", new PwdShellCommand());
		commandMap.put("cd", new CdShellCommand());
		commandMap.put("pushd", new PushdShellCommand());
		commandMap.put("popd", new PopdShellCommand());
		commandMap.put("listd", new ListdShellCommand());
		commandMap.put("dropd", new DropdShellCommand());
		commandMap.put("massrename", new MassrenameShellCommand());
		
		commandMap = Collections.unmodifiableSortedMap(commandMap);
		
		currentDir = Paths.get(".").toAbsolutePath().normalize();
		sharedData = new HashMap<String, Object>();
		
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

	@Override
	public Path getCurrentDirectory() {
		return currentDir;
	}

	@Override
	public void setCurrentDirectory(Path path) {
		if(!Files.exists(path)) {
			throw new ShellIOException("Path does not exist!");
		}
		
		currentDir = path.toAbsolutePath().normalize();
	}

	@Override
	public Object getSharedData(String key) {
		if(!sharedData.containsKey(key)) return null;
		return sharedData.get(key);
	}

	@Override
	public void setSharedData(String key, Object value) {
		sharedData.put(key, value);
		
	}

}
