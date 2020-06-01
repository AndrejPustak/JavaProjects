package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Implementation of SingleDocumentModel
 * @author Andrej
 *
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel {
	
	/**
	 * Path to the file
	 */
	private Path filePath;
	
	/**
	 * Text area of the document
	 */
	private JTextArea textArea;
	
	/**
	 * Modified status of the model
	 */
	private boolean modified;
	
	/**
	 * List of listeners
	 */
	private List<SingleDocumentListener> listeners;
	
	/**
	 * Constructor for the DefaultSingleDocumentModel
	 * @param filePath path to the file
	 * @param text text to be shown in the text area
	 */
	public DefaultSingleDocumentModel(Path filePath, String text) {
		
		textArea = new JTextArea(text);
		textArea.getDocument().addDocumentListener(new DocumentListener() {
			
			private void update() {
				setModified(true);
			}
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				update();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				update();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				update();
			}
		});
		
		listeners = new ArrayList<SingleDocumentListener>();
		setFilePath(filePath);
		setModified(false);
		modifiedUpdate();
	}

	@Override
	public JTextArea getTextComponent() {
		return textArea;
	}

	@Override
	public Path getFilePath() {
		return filePath;
	}

	@Override
	public void setFilePath(Path path) {
		filePath = path;
		pathUpdate();
	}

	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public void setModified(boolean modified) {
		if(this.modified != modified) {
			this.modified = modified;
			modifiedUpdate();
		}
	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		listeners.add(l);
	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		listeners.remove(l);
	}
	
	private void pathUpdate() {
		listeners.stream().forEach(l->l.documentFilePathUpdated(this));
	}
	
	private void modifiedUpdate() {
		listeners.stream().forEach(l->l.documentModifyStatusUpdated(this));
	}

}
