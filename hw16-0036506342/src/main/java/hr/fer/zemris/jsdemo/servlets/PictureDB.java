package hr.fer.zemris.jsdemo.servlets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * THis class represents a picture database
 * @author Andrej
 *
 */
public class PictureDB {

	/**
	 * Instance of the database
	 */
	private static PictureDB instance;
	
	/**
	 * List of pictures
	 */
	List<Picture> pictures = new ArrayList<Picture>();
	
	/**
	 * List of tags of all the pictures
	 */
	List<String> tags = new ArrayList<String>();
	
	/**
	 * Protected constructor
	 */
	protected PictureDB() {
		
	}
	
	/**
	 * Getter for the instance of the database
	 * @return
	 */
	public static PictureDB getDB() {
		
		if(instance == null) {
				instance = new PictureDB();
		}
		
		return instance;
		
	}
	
	/**
	 * THis method is used to configure the database. It takes in one parameter
	 * - path to the file containing all the information about the images.
	 * This includes name, description, and tags.
	 * @param path path to the file
	 */
	public void configureDB(Path path) {
		List<String> lines = null;
		try {
			lines = Files.readAllLines(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(int i = 0; i < lines.size(); i+=3) {
			String name = lines.get(i).strip();
			String desc = lines.get(i+1).strip();
			
			String[] tagsA = lines.get(i+2).split(",");
			List<String> tagsP = new ArrayList<String>();
			for(String tag: tagsA) {
				tagsP.add(tag.strip());
				
				if(!tags.contains(tag.strip())) {
					tags.add(tag.strip());
				}
				
			}
			
			Picture pic = new Picture(name, desc, tagsP);
			
			pictures.add(pic);
			
		}
	}
	
	/**
	 * Getter for the list of all the tags
	 * @return list of all tags
	 */
	public List<String> getTags(){
		return tags;
	}
	
	/**
	 * Getter for the pictures with the specific tag
	 * @param tag tag of the pictures
	 * @return list of pictures with the specified tag
	 */
	public List<Picture> getPicturesWtihTag(String tag){
		return pictures.stream()
				.filter(p->p.getTags().contains(tag))
				.collect(Collectors.toList());
	}
	
	/**
	 * Getter for the picture with the given name
	 * @param name name of the picture
	 * @return picture with the given name
	 */
	public Picture getPicture(String name) {
		for(int i = 0 ; i<pictures.size(); i++) {
			if(pictures.get(i).getName().equals(name)) {
				return pictures.get(i);
			}
		}
		
		return null;
	}
}
