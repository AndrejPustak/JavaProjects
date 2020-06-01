package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;

/**
 * This class represents an action which saves the document on disk as...
 * @author Andrej
 *
 */
public class SaveAsDocumentAction extends AbstractAction{

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
	public SaveAsDocumentAction(JNotepadPP frame, String key) {
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
		Path openedFilePath;
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("Save file");
		if(jfc.showSaveDialog(frame) != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(
					frame,
					frame.getFLP().getString("nothingSaved"),
					frame.getFLP().getString("information"),
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		
		openedFilePath = jfc.getSelectedFile().toPath();
		
		if(Files.exists(openedFilePath)) {
			int button = JOptionPane.YES_NO_OPTION;
			int result = JOptionPane.showConfirmDialog(frame, frame.getFLP().getString("fileExists"), frame.getFLP().getString("confirm"), button);
			if(result == JOptionPane.NO_OPTION) return;
		}
		
		frame.getModel().saveDocument(
				frame.getModel().getCurrentDocument(),
				openedFilePath
				);
	}

}
