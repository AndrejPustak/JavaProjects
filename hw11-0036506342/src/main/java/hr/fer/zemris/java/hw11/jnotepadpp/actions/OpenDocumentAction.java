package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.nio.file.Path;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;

/**
 * This class represents an action which opens the document from disk
 * @author Andrej
 *
 */
public class OpenDocumentAction extends AbstractAction{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Reference to the parent frame
	 */
	private JNotepadPP frame;
	
	/**
	 * Key used for the localisation
	 */
	private String key;
	
	/**
	 * COnstructor for the class
	 * @param frame parent frame
	 * @param key key used for localisation
	 */
	public OpenDocumentAction(JNotepadPP frame, String key) {
		this.frame = frame;
		this.key = key;
		
		updateLanguage();
		
		frame.getFLP().addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				updateLanguage();
			}
		});
		
	}
	
	/**
	 * This method updates the language of the action
	 */
	public void updateLanguage() {
		putValue(
				Action.NAME,
				frame.getFLP().getString(key));
		putValue(
				Action.SHORT_DESCRIPTION, 
				frame.getFLP().getString(key+"Short"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser jfc = new JFileChooser();
		Path openedFilePath = null;
		jfc.setDialogTitle("Open file");
		if(jfc.showOpenDialog(frame) != JFileChooser.APPROVE_OPTION) {
			return;
		}
		openedFilePath = jfc.getSelectedFile().toPath();
		
		frame.getModel().loadDocument(openedFilePath);
	}
	
	
	
}
