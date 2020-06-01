package hr.fer.zemris.jsdemo.servlets;

import java.util.List;

/**
 * This class represents a single picture.
 * @author Andrej
 *
 */
public class Picture {

	/**
	 * Name of the picture
	 */
	String name;
	
	/**
	 * Description of the picture
	 */
	String description;
	
	/**
	 * Tags of the picture
	 */
	List<String> tags;
	
	/**
	 * Constructor of the picture
	 * @param name name 
	 * @param description description
	 * @param tags list of tags
	 */
	public Picture(String name, String description, List<String> tags) {
		super();
		this.name = name;
		this.description = description;
		this.tags = tags;
	}
	
	/**
	 * Getter for the name of the picture
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Getter for the description
	 * @return description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Getter for the tags of the picture
	 * @return list of tags of the picture
	 */
	public List<String> getTags() {
		return tags;
	}
	
	
	
	
}
