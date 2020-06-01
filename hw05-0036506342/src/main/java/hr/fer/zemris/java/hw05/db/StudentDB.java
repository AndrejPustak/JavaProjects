package hr.fer.zemris.java.hw05.db;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * This is a program which reads a student database from the database.txt file and then asks
 * user for input of commands to query the database.
 * The program shuts down after command exit is entered.
 * @author Andrej
 *
 */
public class StudentDB {

	public static void main(String[] args) {
		
		List<String> lines;
		try {
			lines = Files.readAllLines(
					 Paths.get("./database.txt"),
					 StandardCharsets.UTF_8
					);
		}
		catch(Exception ex) {
			System.out.println("Error while reading the database.txt");
			return;
		}
		
		StudentDatabase database;
		try {
			database = new StudentDatabase(lines);
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
			return;
		}
		
		try(Scanner sc = new Scanner(System.in)) {
			while(true) {
				System.out.printf("> ");
				
				String line = sc.nextLine();
				if(line.startsWith("exit")) {
					System.out.println("Goodbye!");
					return;
				} else if (!line.startsWith("query")) {
					System.out.println("Invalid command");
					continue;
				}
				line = line.substring(5);
				
				QueryParser qp;
				try {
					qp = new QueryParser(line);
				}
				catch(QueryParserException ex) {
					System.out.println(ex.getMessage());
					continue;
				}
				
				List<StudentRecord> queriedDatabase = new ArrayList<StudentRecord>();;
				
				if(qp.isDirectQuery()) {
					System.out.println("Using index for record retrieval.");
					queriedDatabase.add(database.forJMBAG(qp.getQueriedJMBAG()));
					if(queriedDatabase.size() > 0) printTable(queriedDatabase);
					System.out.printf("Records selected: %d%n", queriedDatabase.size());
				}
				else {
					queriedDatabase = database.filter(new QueryFilter(qp.getQuery()));
					if(queriedDatabase.size() > 0) printTable(queriedDatabase);
					System.out.printf("Records selected: %d%n", queriedDatabase.size());
				}
			}
		}
		catch (IllegalStateException | NoSuchElementException ex) {
			System.out.println(ex.getMessage());
		}

	}

	private static void printTable(List<StudentRecord> queriedDatabase) {
		int jmbagSize = 0;
		int firstNameSize = 0;
		int lastNameSize = 0;
		for(StudentRecord record : queriedDatabase) {
			if(record.getJmbag().length() > jmbagSize) jmbagSize = record.getJmbag().length();
			if(record.getFirstName().length() > firstNameSize) firstNameSize = record.getFirstName().length();
			if(record.getLastName().length() > lastNameSize) lastNameSize = record.getLastName().length();
		}
		
		StringBuilder sb = new StringBuilder("");
		sb.append("+");
		sb.append(getEquals(jmbagSize));
		sb.append("+");
		sb.append(getEquals(lastNameSize));
		sb.append("+");
		sb.append(getEquals(firstNameSize));
		sb.append("+");
		sb.append("===");
		sb.append("+");
		
		String top = sb.toString();
		
		System.out.println(top);
		
		for(StudentRecord record : queriedDatabase) {
			sb = new StringBuilder("| ");
			sb.append(record.getJmbag());
			for(int i = record.getJmbag().length(); i < jmbagSize; i++) {
				sb.append(" ");
			}
			sb.append(" | ");
			sb.append(record.getLastName());
			for(int i = record.getLastName().length(); i < lastNameSize; i++) {
				sb.append(" ");
			}
			sb.append(" | ");
			sb.append(record.getFirstName());
			for(int i = record.getFirstName().length(); i < firstNameSize; i++) {
				sb.append(" ");
			}
			sb.append(" | ");
			sb.append(record.getFinalGrade());
			sb.append(" |");
			
			System.out.println(sb.toString());
		}
		
		System.out.println(top);
		
	}

	private static String getEquals(int size) {
		StringBuilder sb = new StringBuilder("");
		
		for(int i = 0; i < size; i++) {
			sb.append("=");
		}
		
		sb.append("==");
		return sb.toString();
	}

}
