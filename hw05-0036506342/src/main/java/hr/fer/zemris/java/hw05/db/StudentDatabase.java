package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is a database which holds student records
 * @author Andrej
 *
 */
public class StudentDatabase {
	
	/**
	 * Map which holds all of the student records in the database
	 */
	Map<String, StudentRecord> map;
	
	/**
	 * Constructor for the StudentDatabase
	 * @param list List of the lines each of which holds information about one student record
	 */
	public StudentDatabase(List<String> list) {
		
		map = new LinkedHashMap<String, StudentRecord>();
		
		for(String line : list) {
			String[] array = line.split("\t");
			
			if(map.containsKey(array[0])) {
				throw new IllegalArgumentException("Duplicate jmbag");
			}
			
			int grade = Integer.parseInt(array[3]);
			if(grade < 1 || grade > 5) {
				throw new IllegalArgumentException("Invalid grade");
			}
			
			map.put(array[0], new StudentRecord(
					array[0],
					array[2], 
					array[1],
					grade));
		}
		
	}
	
	/**
	 * This method returns one student record from the database with the given JMBAG
	 * @param jmbag JMBAG of the student record you wish to get
	 * @return student record if it exists, null otherwise
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return map.get(jmbag);
	}
	
	/**
	 * This method filters the database
	 * @param filter Filter by which you wish to filter the database
	 * @return List of all the records which satisfy the filter
	 */
	public List<StudentRecord> filter(IFilter filter){
		List<StudentRecord> list = new ArrayList<StudentRecord>();
		
		for(StudentRecord record: map.values()) {
			if(filter.accepts(record)) {
				list.add(record);
			}
		}
		
		return list;
	}
	
	
	
}
