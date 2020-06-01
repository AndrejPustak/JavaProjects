package hr.fer.zemris.java.hw07.demo4;

/**
 * This class represents a single student record
 * @author Andrej
 *
 */
public class StudentRecord {
	/**
	 * Jmbag of the student
	 */
	private String jmbag;
	
	/**
	 * Surname of the student
	 */
	private String prezime;
	
	/**
	 * Name of the student
	 */
	private String ime;
	
	/**
	 * Points from midterms
	 */
	private double meduispit;
	
	/**
	 * Points from the final exam
	 */
	private double zavrsni;
	
	/**
	 * Points from laboratory 
	 */
	private double labosi;
	
	/**
	 * Final grade
	 */
	private int ocjena;
	
	/**
	 * Constructor for the student record
	 */
	public StudentRecord(String jmbag, String prezime, String ime, double meduispit, double zavrsni, double labosi,
			int ocjena) {
		super();
		this.jmbag = jmbag;
		this.prezime = prezime;
		this.ime = ime;
		this.meduispit = meduispit;
		this.zavrsni = zavrsni;
		this.labosi = labosi;
		this.ocjena = ocjena;
	}
	
	/**
	 * Getter for jmbag
	 * @return jmbag of the student
	 */
	public String getJmbag() {
		return jmbag;
	}
	
	/**
	 * Getter for the surname
	 * @return surname of the student
	 */
	public String getPrezime() {
		return prezime;
	}
	
	/**
	 * Getter for the name of the student
	 * @return name of the student
	 */
	public String getIme() {
		return ime;
	}
	
	/**
	 * Getter for the points on the midterms
	 * @return points from the midterms
	 */
	public double getMeduispit() {
		return meduispit;
	}
	
	/**
	 * Getter for the points on the final exam
	 * @return points on the final exam 
	 */
	public double getZavrsni() {
		return zavrsni;
	}

	public double getLabosi() {
		return labosi;
	}
	
	/**
	 * Getter for the final grade of the student
	 * @return final grade of the student
	 */
	public int getOcjena() {
		return ocjena;
	}
	

	@Override
	public String toString() {
		return "" + jmbag + " " + prezime + " " + ime + " " + meduispit + " " + zavrsni + " " + labosi + " " + ocjena;
	}
	
	
	
	
}
