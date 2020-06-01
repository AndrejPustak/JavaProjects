package hr.fer.zemris.java.hw17.trazilica.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import hr.fer.zemris.java.hw17.trazilica.Command;
import hr.fer.zemris.java.hw17.trazilica.Document;
import hr.fer.zemris.java.hw17.trazilica.Konzola;

/**
 * This command is used to query through the documents and calculate the similarities of
 * all the documents to the given list of words. IT prints the top 10 results
 * @author Andrej
 *
 */
public class QueryCommand implements Command{
	Comparator<Entry<Document, Double>> comp = (e1, e2) -> Double.compare(e1.getValue(), e2.getValue());

	@Override
	public void execute(String params, Konzola console) {
		if(params.length() == 0) {
			System.out.println("No parameters given for query command");
			return;
		}
		
		String[] wordsA = params.split("\\s+");
		List<String> words = Arrays.asList(wordsA);
		
		Map<Document, Double> results = console.getDocTFIDF().calculateSimilarity(words);
		
		List<Entry<Document, Double>> entries = new ArrayList<Map.Entry<Document,Double>>(results.entrySet());
		
 		entries.sort(comp.reversed());
		
		console.setQueryResult(entries);
		
		StringBuilder sb = new StringBuilder();
		sb.append("Query is: [");
		sb.append(words.get(0));
		for(int i = 1; i < words.size(); i++) {
			sb.append(", " + words.get(i));
		}
		sb.append("]");
		
		System.out.println(sb.toString());
		System.out.println("Najboljih 10 rezultata:");
		
		Util.printResults(entries, false);
	}

}
