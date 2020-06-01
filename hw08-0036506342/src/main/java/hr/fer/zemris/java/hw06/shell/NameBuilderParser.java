package hr.fer.zemris.java.hw06.shell;

import java.util.ArrayList;
import java.util.List;

/**
 * NameBuilderParser is a class which takes a pattern in the constructor and then parses the pattern
 * creating a NameBuilder which consists of all NameBuilders crested during parsing.
 * @author Andrej
 *
 */
public class NameBuilderParser {
	
	/**
	 * NameBuilder which consists of all NameBuilders created during parsing.
	 */
	private NameBuilder result;
	
	private int index = 0;
	char[] data;
	
	/**
	 * Constructor for NameBuilderParser
	 * @param expression Expression which you wish to be parsed.
	 */
	public NameBuilderParser(String expression) {
		data = expression.toCharArray();
		result = parse();
	}
	
	/**
	 * This method is called in the constructor and in parses the given expression.
	 * @return
	 */
	private NameBuilder parse() {
		List<NameBuilder> builders = new ArrayList<NameBuilder>();
		
		while(index < data.length) {
			String text;
			if (data[index] == '$' && index+1 < data.length && data[index+1] == '{') {
				index+=2;
				text = extractGroup();
				String[] args = text.split(",");
				if(args.length == 1) {
					int group;
					try {
						group = Integer.parseInt(args[0].strip());
					}
					catch(Exception e) {
						throw new IllegalArgumentException("Not given integer in group");
					}
					builders.add(new NameBuilderGroup(group));
					
				} else if(args.length == 2) {
					int group;
					group = Integer.parseInt(args[0].strip());
					
					if(args[1].length() == 1) {
						int minWidth;
						minWidth = Integer.parseInt(args[1].substring(0, 1));
						builders.add(new NameBuilderGroup(group, ' ', minWidth));
					} else {
						int minWidth;
						minWidth = Integer.parseInt(args[1].substring(1));
						char padding = args[1].charAt(0);
						builders.add(new NameBuilderGroup(group, padding, minWidth));
					}
				} else {
					throw new IllegalArgumentException("Too many arguments for group given");
				}
			}
			else {
				text = extractText();
				builders.add(new NameBuilderText(text));
			}
		}
		
		return new NameBuilderResult(builders);
	}
	
	/**
	 * This command extracts everything in &{ }
	 * @return extracted string
	 */
	private String extractGroup() {
		StringBuilder sb = new StringBuilder();
		for(; index < data.length; index++) {
			if (data[index] == '}') {
				index++;
				break;
			}
			sb.append(data[index]);
		}
		return sb.toString();
	}
	
	/**
	 * This method extracts text until the end or "${"
	 * @return
	 */
	private String extractText() {
		StringBuilder sb = new StringBuilder();
		for(; index < data.length; index++) {
			if (data[index] == '$' && index+1 < data.length && data[index+1] == '{') {
				break;
			}
			sb.append(data[index]);
		}
		return sb.toString();
	}
	
	/**
	 * Implementation of NameBuilder which calls execute on all NameBuilders it got in the constructor.
	 * @author Andrej
	 *
	 */
	public static class NameBuilderResult implements NameBuilder {
		
		private List<NameBuilder> builders;

		public NameBuilderResult(List<NameBuilder> builders) {
			this.builders = builders;
		}

		@Override
		public void execute(FilterResult result, StringBuilder sb) {
			builders.stream().forEach(b->b.execute(result, sb));
		}
		
		
		
	}
	
	/**
	 * Implementation of NameBuilder which adds text to the name.
	 * @author Andrej
	 *
	 */
	public static class NameBuilderText implements NameBuilder {
		
		private String text;
		
		public NameBuilderText(String text) {
			this.text = text;
		}
		
		@Override
		public void execute(FilterResult result, StringBuilder sb) {
			sb.append(text);
		}
	}
	
	/**
	 * Implementation of NameBuilder which adds group with the given number to the name.
	 * @author Andrej
	 *
	 */
	public static class NameBuilderGroup implements NameBuilder {
		
		private int index;
		private char padding = ' ';
		private int minWidth;
		
		public NameBuilderGroup(int index) {
			this.index = index;
		}
		
		public NameBuilderGroup(int index, char padding, int minWidth) {
			this(index);
			this.padding = padding;
			this.minWidth = minWidth;
		} 
		
		@Override
		public void execute(FilterResult result, StringBuilder sb) {
			StringBuilder sb2 = new StringBuilder();
			if(result.group(index).length() <= minWidth) {
				for(int i = 0; i < minWidth - result.group(index).length(); i++) {
					sb2.append(padding);
				}
			}
			sb2.append(result.group(index));
			sb.append(sb2.toString());
		}
	}
	
	/**
	 * THis method returns the result NameBuilder.
	 * @return
	 */
	public NameBuilder getNameBuilder() {
		return result;
	}
	
}
