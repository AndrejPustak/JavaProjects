package hr.fer.zemris.java.hw06.shell;

/**
 * Class with some extractor methods for commands to use.
 * @author Andrej
 *
 */
public class StringExtractors {
	
	/**
	 * This method extracts a path in quotation.
	 * @param string string from which you wish to extract
	 * @return the extracted path
	 */
	public static String extractPathNameInQuotation(String string) {
		char[] data = string.substring(1).toCharArray();
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < data.length; i++) {
			if(data[i] == '\\') {
				if(i+1 < data.length && data[i+1] == '\"') {
					sb.append(data[++i]);
				}
				if(i+1 < data.length && data[i+1] == '\\') {
					sb.append(data[++i]);
				}
			}
			
			if (data[i]=='\"') {
				return sb.toString();
			}
			
			sb.append(data[i]);
			
		}
		
		return null;
	}
	
	/**
	 * This method extracts from string until whitespace character
	 * @param string string from which you wish to extract
	 * @return the extracted string
	 */
	public static String extractUntilWhitespace(String string) {
		StringBuilder sb = new StringBuilder();
		
		for (char c : string.toCharArray()) {
			if (Character.isWhitespace(c)) {
				break;
			}
			
			sb.append(c);
		}
		
		return sb.toString();
	}
}
