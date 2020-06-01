package hr.fer.zemris.java.hw06.shell;

import java.nio.file.Path;
import java.util.regex.Matcher;

public class FilterResult {
	
	private Path filePath;
	private Matcher matcher;
	
	public FilterResult(Path filePath, Matcher matcher) {
		this.filePath = filePath;
		this.matcher = matcher;
		matcher.find();
	}
	
	@Override
	public String toString() {
		return filePath.getFileName().toString();
	}
	
	public int numberOfGroups() {
		return matcher.groupCount();
	}
	
	public String group(int index) {
		if(index > numberOfGroups() || index < 0)
			throw new IndexOutOfBoundsException("Index for group is out of bounds.");
		if(matcher.matches()) return matcher.group(index);
		else return null;
	}
	
}
