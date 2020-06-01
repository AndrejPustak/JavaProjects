package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * This class represents a listener for MultipleDocumentModel.
 * @author Andrej
 *
 */
public interface MultipleDocumentListener {
	
	/**
	 * This method is used to notify the observers when the current document changes.
	 * @param previousModel Previous document
	 * @param currentModel current document
	 */
	void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);
	
	/**
	 * This method is used to notify the observers when a new document is added.
	 * @param model new document model
	 */
	void documentAdded(SingleDocumentModel model);
	
	/**
	 * This method is used to notify the observer when a document is removed.
	 * @param model Removed document model.
	 */
	void documentRemoved(SingleDocumentModel model);
	
}
