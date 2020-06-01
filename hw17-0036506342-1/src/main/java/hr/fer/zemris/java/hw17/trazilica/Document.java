package hr.fer.zemris.java.hw17.trazilica;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class representing a single document
 * @author Andrej
 *
 */
public class Document {
	/**
	 * List of stopwords
	 */
	public static List<String> stopWords;
	
	static {
		stopWords = new ArrayList<String>();
		
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream is = classloader.getResourceAsStream("hrvatski_stoprijeci.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		try {
			for(String line; (line = br.readLine()) != null;) {
				stopWords.add(line.strip());
			}
		} catch (IOException e) {
			System.out.println("Unbale to read the stopwords");
		}
	}
	
	/**
	 * List of words in the document
	 */
	private List<String> words = new ArrayList<String>();
	
	/**
	 * Path to the document
	 */
	private Path path;
	
	/**
	 * Map containing word counts of all the words in the document
	 */
	private Map<String, Integer> wordCount = new HashMap<String, Integer>();
	
	/**
	 * Map containing tfidf values of all the words
	 */
	private Map<String, Double> tfidf = new HashMap<String, Double>();
	
	/**
	 * Constructor for the Document class
	 * @param path path to the document
	 */
	public Document(Path path) {
		this.path = path;
		
		String document = null;
		try {
			document = Files.readString(path);
		} catch (IOException e) {
			System.out.println("Unable to read the document");
		}
		
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < document.length(); i++) {
			if(Character.isAlphabetic(document.charAt(i))) {
				sb.append(Character.toLowerCase(document.charAt(i)));
			} else {
				if(sb.length() == 0) continue;
				if(!stopWords.contains(sb.toString())) {
					if(!words.contains(sb.toString())) {
						words.add(sb.toString());
						wordCount.put(sb.toString(), 1);
					} else {
						wordCount.put(sb.toString(), wordCount.get(sb.toString()) + 1);
					}
				}
				sb.setLength(0);
			}
		}
		
	}
	
	/**
	 * This method is used to check if the document contains the given word
	 * @param word word you wish to check is in the document
	 * @return true if it contains the word, false otherwise
	 */
	public boolean containsWord(String word) {
		return words.contains(word);
	}
	
	/**
	 * This method is used to return the word count of a specific word
	 * @param word word
	 * @return word count
	 */
	public int wordCount(String word) {
		if(wordCount.containsKey(word)) {
			return wordCount.get(word);
		}
		return 0;
	}
	
	/**
	 * This method is used to get the tfidf of a specific word
	 * @param word word 
	 * @return word's tfidf
	 */
	public double getTFIDF(String word) {
		if(tfidf != null) {
			if(tfidf.get(word) == null) {
				return 0;
			}
			return tfidf.get(word);
		}
		return 0;
	}

	/**
	 * Getter for the list of words
	 * @return list of words
	 */
	public List<String> getWords() {
		return words;
	}

	/**
	 * Getter for the path of the document
	 * @return path of the document
	 */
	public Path getPath() {
		return path;
	}

	/**
	 * Getter for the map of all the tfidf's of all the words
	 * @return map of all the tfidf's
	 */
	public Map<String, Double> getTfidf() {
		return tfidf;
	}

	/**
	 * Setter for the tfidf map
	 * @param tfidf tfidf map
	 */
	public void setTfidf(Map<String, Double> tfidf) {
		this.tfidf = tfidf;
	}
	
}
