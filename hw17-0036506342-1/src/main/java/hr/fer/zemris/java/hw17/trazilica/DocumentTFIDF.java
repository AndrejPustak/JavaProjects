package hr.fer.zemris.java.hw17.trazilica;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * File that represents a collection of documents and is used for calculation their
 * TFIDFs and similarities.
 * @author Andrej
 *
 */
public class DocumentTFIDF {

	/**
	 * List of documents
	 */
	List<Document> documents = new ArrayList<Document>();
	
	/**
	 * Document vocabulary
	 */
	List<String> docWords = new ArrayList<String>();
	
	/**
	 * Map containing words and the number of documents that word appearers in.
	 */
	Map<String, Integer> wordCount = new HashMap<String, Integer>();
	
	public DocumentTFIDF() {
	}
	
	/**
	 * Method used to add a new document
	 * @param doc new document
	 */
	public void addDocument(Document doc) {
		documents.add(doc);
		List<String> words = doc.getWords();
		
		for(String word : words) {
			if(!docWords.contains(word)) {
				docWords.add(word);
				wordCount.put(word, 1);
			} else {
				wordCount.put(word , wordCount.get(word) + 1);
			}
		}
	} 
	
	/**
	 * MEthos used to generate TFIDFs for all the documents
	 */
	@SuppressWarnings("unused")
	public void generateTFIDF() {
		
		for(Document doc : documents) {
			Map<String, Double> tfidf = new HashMap<String, Double>();
			for(String word : docWords) {
				int tf = doc.wordCount(word);
				double idf = geIDF(word);
				
				tfidf.put(word, (double) tf*idf);
			}
			
			doc.setTfidf(tfidf);
		}
		
	}

	/**
	 * Getter for the idf of a specific word
	 * @param word word you wish to get the idf for
	 * @return idf of the word
	 */
	private double geIDF(String word) {
		int num = wordCount.get(word);
		
		if(num == 0) {
			return 0.0;
		}
		
		return Math.log((double)documents.size()/num);
	}
	
	/**
	 * Method used to calculate the similarity between all documents and the given list of words.
	 * @param words list of words
	 * @return map whose key is a document and the value is it's similarity to the given list of words
	 */
	public Map<Document, Double> calculateSimilarity(List<String> words){
		Map<Document, Double> map = new HashMap<Document, Double>();
		Map<String, Integer> tfMap = new HashMap<String, Integer>();
		Map<String, Double> tfidf = new HashMap<String, Double>();
		
		for(String word : words) {
			if(tfMap.containsKey(word)) {
				tfMap.put(word, tfMap.get(word) + 1);
			} else tfMap.put(word, 1);
		}
		
		for(String word : docWords) {
			if(words.contains(word)) {
				int tf = tfMap.get(word);
				double idf = geIDF(word);
				
				tfidf.put(word, tf*idf);
			} else {
				tfidf.put(word, 0.0);
			}
		}
		
		for(Document doc : documents) {
			double sp = 0;
			double norm1 = 0;
			double norm2 = 0;
			
			for(String word : docWords) {
				sp += doc.getTFIDF(word) * tfidf.get(word);
				norm1 += doc.getTFIDF(word) * doc.getTFIDF(word);
				norm2 += tfidf.get(word) * tfidf.get(word);
			}
			
			norm1 = Math.sqrt(norm1);
			norm2 = Math.sqrt(norm2);
			
			if(sp != 0 && norm1 != 0 && norm2 != 0) {
				map.put(doc, sp / (norm1 * norm2));
			}
		}
		
		return map;
	}
	
	/**
	 * This method returns the vocabulary size
	 * @return vocabulary size
	 */
	public int getVocabularySize() {
		return docWords.size();
	}
	
}
