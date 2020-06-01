package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

/**
 * Interface which represents a Document Model which supports working with multiple documents.
 * @author Andrej
 *
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel>{
	
	/**
	 * This method is used to create a new document. The current document changes to the
	 * new document.
	 * @return Reference to the new document.
	 */
	SingleDocumentModel createNewDocument();
	
	/**
	 * This method returns the current selected document.
	 * @return Current document.
	 */
	SingleDocumentModel getCurrentDocument();
	
	/**
	 * This method is used to load the document from disk.
	 * @param path Path to the document
	 * @return Reference to the model of the loaded document.
	 */
	SingleDocumentModel loadDocument(Path path);
	
	/**
	 * This method is used to save the given document to the disk.
	 * @param model Document you wish to save.
	 * @param newPath Path you wish to save the document to.
	 */
	void saveDocument(SingleDocumentModel model, Path newPath);
	
	/**
	 * This method is used to close the given document.
	 * @param model Model of the document you wish to close.
	 */
	void closeDocument(SingleDocumentModel model);
	
	/**
	 * This method is used to add a MultipleDocumentListener to this model
	 * @param l listener you wish to add
	 */
	void addMultipleDocumentListener(MultipleDocumentListener l);
	
	/**
	 * This method is used to remove the MultipleDocumentListener from this model
	 * @param l listener you wish to remove
	 */
	void removeMultipleDocumentListener(MultipleDocumentListener l);
	
	/**
	 * This method returns the number of documents.
	 * @return Number of documents.
	 */
	int getNumberOfDocuments();
	
	/**
	 * This method returns the document at the given index
	 * @param index index of the document
	 * @return document at the given index
	 */
	SingleDocumentModel getDocument(int index);
	
}
