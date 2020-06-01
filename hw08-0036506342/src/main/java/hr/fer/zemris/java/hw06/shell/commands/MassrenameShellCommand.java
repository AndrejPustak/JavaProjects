package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.FilterResult;
import hr.fer.zemris.java.hw06.shell.NameBuilder;
import hr.fer.zemris.java.hw06.shell.NameBuilderParser;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.StringExtractors;

/**
 * Command massrename is a command which takes in 4 or 5 arguments:
 * massrename DIR1 DIR2 CMD MASK [rest]
 * CMD can be one of the following 4: filter, groups, show, execute.
 * If CMD is filter the command filters all of the files in the DIR1 who match the pattern
 * given in as MASK and writes their names to the chat.
 * If CMD is groups the command filters all of the files in the DIR! who match the pattern
 * given in as MASK and writes their names plus all of the groups for the names.
 * If CMD is show the the command filters all of the files in the DIR1 who match the pattern
 * given in as MASK and renames them using the pattern given in the [rest] and writes the 
 * old and new names in the shell.
 * If CMD is execute the the command filters all of the files in the DIR1 who match the pattern
 * given in as MASK and renames them using the pattern given in the [rest] and moves the 
 * files from DIR1 to DIR2 using the new names.
 * 
 * @author Andrej
 *
 */
public class MassrenameShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments.length() == 0) {
			env.writeln("No arguments were given");
			return ShellStatus.CONTIUNUE;
		}
		
		String sourcePath = "";
		String destinationPath = "";
		
		//DIR1
		if(arguments.charAt(0) == '\"' ) {
			sourcePath = StringExtractors.extractInQuotation(arguments);
			if(sourcePath == null) {
				env.writeln("Invalid path");
				return ShellStatus.CONTIUNUE;
			}
			
			arguments = arguments.substring(sourcePath.length() + 2).strip();
		} else {
			sourcePath = StringExtractors.extractUntilWhitespace(arguments);
			arguments = arguments.substring(sourcePath.length()).strip();
		}
		
		if(arguments.length() == 0) {
			env.writeln("Only one argument given");
			return ShellStatus.CONTIUNUE;
		}
		
		//DIR2
		if(arguments.charAt(0) == '\"' ) {
			destinationPath = StringExtractors.extractInQuotation(arguments);
			if(destinationPath == null) {
				env.writeln("Invalid path");
				return ShellStatus.CONTIUNUE;
			}
			
			arguments = arguments.substring(destinationPath.length() + 2).strip();
		} else {
			destinationPath = StringExtractors.extractUntilWhitespace(arguments);
			arguments = arguments.substring(destinationPath.length()).strip();
		}
		
		if(arguments.length() == 0) {
			env.writeln("Only two arguments given");
			return ShellStatus.CONTIUNUE;
		}
		
		//CMD
		String command = StringExtractors.extractUntilWhitespace(arguments);
		if(!isValidCommand(command)) {
			env.writeln("Invalid commmand in massrename");
			return ShellStatus.CONTIUNUE;
		}
		arguments = arguments.substring(command.length()).strip();
		
		if(arguments.length() == 0) {
			env.writeln("Only 3 arguments given");
			return ShellStatus.CONTIUNUE;
		}
		
		//MASK
		String mask = "";
		if(arguments.charAt(0) == '\"' ) {
			mask = StringExtractors.extractInQuotation(arguments);
			if(mask == null) {
				env.writeln("Invalid mask");
				return ShellStatus.CONTIUNUE;
			}
			arguments = arguments.substring(mask.length() + 2).strip();
		} else {
			mask = StringExtractors.extractUntilWhitespace(arguments);
			arguments = arguments.substring(mask.length()).strip();
		}
		
		//EXPRESSION
		String expression = "";
		if(arguments.length() != 0 && arguments.charAt(0) == '\"' ) {
			mask = StringExtractors.extractInQuotation(arguments);
			arguments = arguments.substring(expression.length() + 1).strip();
		} else if(arguments.length() != 0){
			expression = StringExtractors.extractUntilWhitespace(arguments);
			arguments = arguments.substring(expression.length()).strip();
		}
		
		Path source = Paths.get(sourcePath);
		if(!source.isAbsolute()) {
			source = env.getCurrentDirectory().resolve(source).normalize();
		}
		
		Path destination = Paths.get(destinationPath);
		if(!destination.isAbsolute()) {
			destination = env.getCurrentDirectory().resolve(destination).normalize();
		}
		
		if(!Files.isDirectory(source) || !Files.isDirectory(destination)) {
			env.writeln("One of the given directories is a file.");
			return ShellStatus.CONTIUNUE;
		}
		
		List<FilterResult> list;
		try {
			list = filter(source, mask);
		} catch (IOException e) {
			env.writeln("Exception when filtering files");
			return ShellStatus.CONTIUNUE;
		}
		
		
		try {
			switch(command.toLowerCase()) {
			case "filter":
				if(!(expression != null && expression.length() == 0)) {
					env.writeln("Unexpected additional arguments");
					return ShellStatus.CONTIUNUE;
				}
				filterCommand(env, list, source, destination);
				break;
				
			case "groups":
				if(!(expression != null && expression.length() == 0)) {
					env.writeln("Unexpected additional arguments");
					return ShellStatus.CONTIUNUE;
				}
				groupsCommand(env, list, source, destination);
				break;
				
			case "show":
				showCommand(env, list, source, destination, expression);
				break;
			
			case "execute":
				executeCommand(env, list, source, destination, expression);
				break;
			}
		}
		catch(Exception e) {
			throw new ShellIOException(e.getMessage());
		}
		
		
		return ShellStatus.CONTIUNUE;
	}
	
	private void executeCommand(Environment env, List<FilterResult> list, Path source, Path destination,
			String expression) {

		NameBuilderParser parser = new NameBuilderParser(expression);
		NameBuilder builder = parser.getNameBuilder();
		
		for(FilterResult file :  list) {
			StringBuilder sb = new StringBuilder();
			builder.execute(file, sb);
			String newName = sb.toString();
			
			env.write(source.getFileName().toString() +  "/" + file.toString());
			env.write(" => ");
			env.writeln(destination.getFileName().toString() +  "/" + newName);
			
			try {
				Files.move(source.resolve(file.toString()).normalize(), destination.resolve(newName).normalize());
			} catch (IOException e) {
				env.writeln("Exception while moving files");
			}
		}
		
	}

	private void showCommand(Environment env, List<FilterResult> list, Path source, Path destination,
			String expression) {
		
		NameBuilderParser parser = new NameBuilderParser(expression);
		NameBuilder builder = parser.getNameBuilder();
		
		for(FilterResult file :  list) {
			StringBuilder sb = new StringBuilder();
			builder.execute(file, sb);
			String newName = sb.toString();
			
			env.write(source.getFileName().toString() +  "/" + file.toString());
			env.write(" => ");
			env.writeln(destination.getFileName().toString() +  "/" + newName);
		}
		
	}

	private void filterCommand(Environment env, List<FilterResult> list, Path source, Path destination) {
		for(FilterResult result : list) {
			env.writeln(result.toString());
		}
	}
	
	private void groupsCommand(Environment env, List<FilterResult> list, Path source, Path destination) {
		for(FilterResult result : list) {
			StringBuilder sb = new StringBuilder();
			sb.append(result.toString());
			for(int i = 0; i <= result.numberOfGroups(); i++) {
				sb.append(String.format(" %d: %s", i, result.group(i)));
			}
			env.writeln(sb.toString());
		}
	}
	
	private static List<FilterResult> filter(Path dir, String pattern) throws IOException {
		List<FilterResult> list = new ArrayList<FilterResult>();
		List<Path> fileList = Files.list(dir).filter(f->!Files.isDirectory(f)).collect(Collectors.toList());
		
		for(Path file : fileList) {
			Pattern p = Pattern.compile(pattern, Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
			Matcher matcher = p.matcher(file.getFileName().toString());
			if(matcher.matches()) list.add(new FilterResult(file, matcher));
		}
		
		return list;
	}

	private boolean isValidCommand(String command) {
		return command.equals("filter") || command.equals("groups") || command.equals("show") || command.equals("execute");
	}

	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<String>();
		list.add("This command is used for mass renaming of files in a directory.");
		return Collections.unmodifiableList(list);
	}

}
