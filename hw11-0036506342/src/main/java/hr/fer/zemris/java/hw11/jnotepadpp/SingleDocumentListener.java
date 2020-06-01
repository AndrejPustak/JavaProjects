package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * This class represents a listener for SingleDocumentModel.
 * @author Andrej
 *
 */
public interface SingleDocumentListener {
	
	/**
	 * This method is used to notify the observers when modified status of the model
	 * has changed.
	 * @param model Reference to the changed document model
	 */
	void documentModifyStatusUpdated(SingleDocumentModel model);
	
	/**
	 * This method is used to notify the observers when the file path of the document
	 * has changed.
	 * @param model Reference to the changed document model.
	 */
	void documentFilePathUpdated(SingleDocumentModel model);
	
}
