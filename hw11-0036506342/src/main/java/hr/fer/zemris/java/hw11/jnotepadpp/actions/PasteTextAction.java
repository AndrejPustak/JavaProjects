package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import javax.swing.Action;
import javax.swing.text.DefaultEditorKit;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;

/**
 * This class represents an action which pastes the text from clipboard
 * @author Andrej
 *
 */
public class PasteTextAction extends DefaultEditorKit.PasteAction{

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
	public PasteTextAction(JNotepadPP frame, String key) {
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
		putValue(Action.NAME, frame.getFLP().getString(key));
		putValue(Action.SHORT_DESCRIPTION, frame.getFLP().getString(key + "Short"));
	}

}
