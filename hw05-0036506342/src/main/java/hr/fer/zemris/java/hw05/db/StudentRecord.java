package hr.fer.zemris.java.hw05.db;

import java.util.Objects;

/**
 * This class represents a single student record
 * @author Andrej
 *
 */
public class StudentRecord {
	
	private String jmbag;
	private String firstName;
	private String lastName;
	private int finalGrade;
	
	/**
	 * Constructor for the StudentRecord
	 * @param jmbag JMBAG of the student
	 * @param firstName First name of the student
	 * @param lastName Last name of the student
	 * @param finalGrade Final grade of the student
	 */
	public StudentRecord(String jmbag, String firstName, String lastName, int finalGrade) {
		this.jmbag = jmbag;
		this.firstName = firstName;
		this.lastName = lastName;
		
		if(finalGrade < 1 || finalGrade > 5) {
			throw new IllegalArgumentException("Final grade is not valid");
		}
		
		this.finalGrade = finalGrade;
	}
	
	
	@Override
	public int hashCode() {
		return Objects.hash(jmbag);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof StudentRecord))
			return false;
		StudentRecord other = (StudentRecord) obj;
		return Objects.equals(jmbag, other.jmbag);
	}

	
	/**
	 * Getter for the JMBAG of the student record
	 * @return JMBAG of the student record
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Getter for the first name of the student
	 * @return First name of the student
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Getter for the last name of the student
	 * @return last name of the student
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Getter for the final grade of the student
	 * @return final grade of the student
	 */
	public int getFinalGrade() {
		return finalGrade;
	}
	
	
	
	
}
