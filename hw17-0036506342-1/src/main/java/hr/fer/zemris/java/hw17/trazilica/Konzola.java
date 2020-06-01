package hr.fer.zemris.java.hw17.trazilica;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import hr.fer.zemris.java.hw17.trazilica.commands.ExitCommand;
import hr.fer.zemris.java.hw17.trazilica.commands.QueryCommand;
import hr.fer.zemris.java.hw17.trazilica.commands.ResultsCommand;
import hr.fer.zemris.java.hw17.trazilica.commands.TypeCommand;

/**
 * This class represents a simple search engine application. It is used to search for specific words in a 
 * group of documents.
 * @author Andrej
 *
 */
public class Konzola {
	
	/**
	 * File visitor used to add documents to DocumentTFIDF object
	 * @author Andrej
	 *
	 */
	private class MyFileVisitor extends SimpleFileVisitor<Path> {
		private DocumentTFIDF docTFIDF;
		
		public MyFileVisitor(DocumentTFIDF docTFIDF) {
			this.docTFIDF = docTFIDF;
		}
		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			if(Files.isRegularFile(file)) {
				docTFIDF.addDocument(new Document(file));
			}
			
			return FileVisitResult.CONTINUE;
		}
	}

	/**
	 * Map of commands
	 */
	private Map<String, Command> commands;
	/**
	 * Reference to DocumentTFIDF
	 */
	private DocumentTFIDF docTFIDF;
	
	/**
	 * List of querry results
	 */
	private List<Entry<Document, Double>> queryResult;
	
	/**
	 * Flag for exiting application
	 */
	private boolean exit = false;
	
	/**
	 * Getter for current DocumentTFIDF
	 * @return
	 */
	public DocumentTFIDF getDocTFIDF() {
		return docTFIDF;
	}

	/**
	 * Constructor for the class
	 * @param docPath path to the file with text files
	 */
	public Konzola(Path docPath) {
		docTFIDF = new DocumentTFIDF();
		
		try {
			Files.walkFileTree(docPath, new MyFileVisitor(docTFIDF));
		} catch (IOException e) {
			System.out.println("Unable to walk through the files");
		}
		docTFIDF.generateTFIDF();
		
		commands = new HashMap<String, Command>();
		
		commands.put("query", new QueryCommand());
		commands.put("type", new TypeCommand());
		commands.put("results", new ResultsCommand());
		commands.put("exit", new ExitCommand());
	}
	
	/**
	 * Method from which the program starts
	 * @param args
	 */
	public static void main(String[] args) {
		if(args.length != 1) {
			System.out.println("Path to the documents folder not given");
			return;
		}
		
		Path docPath = Paths.get(args[0]);
		Konzola console = new Konzola(docPath);
		
		Scanner sc = new Scanner(System.in);
		String command = "";
		
		System.out.println("Veličina rječnika je " + console.getDocTFIDF().getVocabularySize() + " riječi.");
		
		while(!console.isExit()) {
			System.out.print("Enter command > ");
			String line = sc.nextLine();
			
			int i = 0;
			for(; i < line.length(); i++) {
				if(Character.isWhitespace(line.charAt(i))) {
					break;
				}
			}
			
			command = line.substring(0, i);
			
			if(!console.commands.containsKey(command)){
				System.out.println("Nepoznata naredba");
				continue;
			}
			
			console.commands.get(command).execute(line.substring(i).strip(), console);
			
		}
		
		sc.close();
	}

	/**
	 * Getter for the query results
	 * @return query results
	 */
	public List<Entry<Document, Double>> getQueryResult() {
		return queryResult;
	}

	/**
	 * Setter for the query results
	 * @param queryResult list of query results
	 */
	public void setQueryResult(List<Entry<Document, Double>> queryResult) {
		this.queryResult = queryResult;
	}

	/**
	 * Getter for the exit flag
	 * @return exit flag
	 */
	public boolean isExit() {
		return exit;
	}

	/**
	 * Setter for the exit flag
	 * @param exit exit flag
	 */
	public void setExit(boolean exit) {
		this.exit = exit;
	}
	
	
	
}
