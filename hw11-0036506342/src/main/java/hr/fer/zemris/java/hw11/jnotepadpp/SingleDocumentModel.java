package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

import javax.swing.JTextArea;

/**
 * THis class represents a single document model.
 * @author Andrej
 *
 */
public interface SingleDocumentModel {
	
	/**
	 * This method returns the text component of this model
	 * @return Text component of this model
	 */
	JTextArea getTextComponent();
	
	/**
	 * This method returns the file path of this model
	 * @return file path of this model
	 */
	Path getFilePath();
	
	/**
	 * Setter for the file path of this model
	 * @param path path you wish to set the path of the model to
	 */
	void setFilePath(Path path);
	
	/**
	 * This method is used to check if the model has been modified
	 * @return true if it has been modified, false otherwise
	 */
	boolean isModified();
	
	/**
	 * This method is used to set the modified status of the model
	 * @param modified status you wish to set the modified to
	 */
	void setModified(boolean modified);
	
	/**
	 * This method is used to add a SingleDocumentListener to this model
	 * @param l listener you wish to add
	 */
	void addSingleDocumentListener(SingleDocumentListener l);
	
	/**
	 * This method is used to remove the given listener from this model
	 * @param l listener you wish to remove from this model
	 */
	void removeSingleDocumentListener(SingleDocumentListener l);

	
}
