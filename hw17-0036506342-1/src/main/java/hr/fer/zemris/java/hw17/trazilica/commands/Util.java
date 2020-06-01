package hr.fer.zemris.java.hw17.trazilica.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map.Entry;

import hr.fer.zemris.java.hw17.trazilica.Document;

/**
 * Class with all the utility methods
 * @author Andrej
 *
 */
public class Util {

	/**
	 * THis method is used to print results. IF all is set to true it prints all the results
	 * and if all is false it prints the first 10
	 * @param results list of results
	 * @param all flag to print all or 10
	 */
	public static void printResults(List<Entry<Document, Double>> results, boolean all) {
		int stop;
		if(all) stop = results.size();
		else stop = 10;
		
		for(int i = 0; i < stop && i < results.size(); i++) {
			System.out.printf("[%d](%.4f) %s%n", i, results.get(i).getValue(), results.get(i).getKey().getPath().toString());
		}
	}
	
	/**
	 * This method is used to print the content of a specific result
	 * @param results list of results
	 * @param i index of the result you wish to print
	 */
	public static void printSpecificResult(List<Entry<Document, Double>> results, int i) {
		Path path = results.get(i).getKey().getPath();
		System.out.println("-----------------------------------------------------------------");
		System.out.printf("Dokument: %s%n", path.toString());
		System.out.println("-----------------------------------------------------------------");
		
		try {
			String docString = Files.readString(path);
			System.out.println(docString);
			System.out.println("-----------------------------------------------------------------");
		} catch (IOException e) {
			System.out.println("Unable to read the file");
		}
	}
}
