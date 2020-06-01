package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.StringExtractors;

/**
 * This command displays the given directory tree.
 * @author Andrej
 *
 */
public class TreeShellCommand implements ShellCommand {
	
	public static final String COMMAND_NAME = "tree";
	
	public static class MyFileVisitor implements FileVisitor<Path>{
		
		private int level = 0;
		Environment env;
		
		public MyFileVisitor(Environment env) {
			this.env = env;
		}

		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
			env.writeln(" ".repeat(level*2) + dir.getFileName());
			level++;
			
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			level++;
			env.writeln(" ".repeat(level*2) + file.getFileName());
			level--;
			
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
			return FileVisitResult.TERMINATE;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			level--;
			
			return FileVisitResult.CONTINUE;
		}
		
	}
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments.length() == 0) {
			env.writeln("No arguments were given");
			return ShellStatus.CONTIUNUE;
		}
		
		String pathName = "";
		
		if(arguments.charAt(0) == '\"' ) {
			pathName = StringExtractors.extractPathNameInQuotation(arguments);
			if(pathName == null) {
				env.writeln("Invalid path");
				return ShellStatus.CONTIUNUE;
			}
			
			arguments = arguments.substring(pathName.length() + 2).strip();
		} else {
			pathName = StringExtractors.extractUntilWhitespace(arguments);
			arguments = arguments.substring(pathName.length()).strip();
		}
		
		if(arguments.length() != 0) {
			env.writeln("Multiple arguments given. Was expecting one.");
			return ShellStatus.CONTIUNUE;
		}
		
		Path path = Paths.get(pathName);
		
		try {
			Files.walkFileTree(path, new MyFileVisitor(env));
		} catch (IOException e) {
			throw new ShellIOException("Error while walikng wile tree");
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
		list.add("Prints directory tree.");
		return Collections.unmodifiableList(list);
	}

}
