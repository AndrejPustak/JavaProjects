package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;

/**
 * This class represents an action which changes the case of
 * the characters based on the key
 * @author Andrej
 *
 */
public class ChangeCaseAction extends AbstractAction{
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
	public ChangeCaseAction(JNotepadPP frame, String key) {
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
	 * THis method updates the language of the action 
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
		Document doc = frame.getModel().getCurrentDocument().getTextComponent().getDocument();
		Caret caret = frame.getModel().getCurrentDocument().getTextComponent().getCaret();
		int start = Math.min(caret.getDot(), caret.getMark());
		int len = Math.abs(caret.getDot() - caret.getMark());
		
		if(len == 0) return;
		
		try {
			String text = doc.getText(start, len);
			text = changeCase(text);
			doc.remove(start, len);
			doc.insertString(start, text, null);
		} catch(BadLocationException ignorable) {
		}
		
	}
	
	/**
	 * This method changes the case of the given text
	 * @param text text you wish to change the case of 
	 * @return new text with changed case
	 */
	private String changeCase(String text) {
		char[] chars = text.toCharArray();
		for(int i = 0; i < chars.length; i++) {
			if(key.equals("upper"))
				chars[i] = Character.toUpperCase(chars[i]);
			else if(key.equals("lower"))
				chars[i] = Character.toLowerCase(chars[i]);
			else chars[i] = invertCase(chars[i]);
		}
		return new String(chars);
	}
	
	/**
	 * This method is used to invert the case of the given character
	 * @param c character you wish to invert the case of 
	 * @return new character with the inverted case
	 */
	private char invertCase(char c) {
		if(Character.isUpperCase(c)) {
			c = Character.toLowerCase(c);
		} else if(Character.isLowerCase(c)) {
			c = Character.toUpperCase(c);
		}
		return c;
	}
}
