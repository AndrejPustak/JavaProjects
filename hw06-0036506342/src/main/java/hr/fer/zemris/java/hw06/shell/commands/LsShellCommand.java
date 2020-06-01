package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.StringExtractors;

/**
 * This command writes directory listing.
 * @author Andrej
 *
 */
public class LsShellCommand implements ShellCommand{
	
	public static final String COMMAND_NAME = "ls";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments.length() == 0) {
			env.writeln("No directory given");
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
		
		if(!path.toFile().isDirectory()) {
			env.writeln("Argument is not a directory");
			return ShellStatus.CONTIUNUE;
		}
		
		for(String file : path.toFile().list()) {
			Path temp = Paths.get(file);
			StringBuilder sb = new StringBuilder();
			
			if(temp.toFile().isDirectory()) sb.append("d");
			else sb.append("-");
			
			if(temp.toFile().canRead()) sb.append("r");
			else sb.append("-");
			
			if(temp.toFile().canWrite()) sb.append("w");
			else sb.append("-");
			
			if(temp.toFile().canExecute()) sb.append("x");
			else sb.append("-");
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			BasicFileAttributeView faView = Files.getFileAttributeView(
			temp, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS
			);
			BasicFileAttributes attributes = null;
			try {
				attributes = faView.readAttributes();
			} catch (IOException e) {
			}
			FileTime fileTime = attributes.creationTime();
			String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
			
			String line = String.format("%s   %10d %s %s", sb.toString(), temp.toFile().length(), formattedDateTime, file);
			env.writeln(line);
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
		list.add("Writes a directory listing");
		return Collections.unmodifiableList(list);
	}
	
	
}
