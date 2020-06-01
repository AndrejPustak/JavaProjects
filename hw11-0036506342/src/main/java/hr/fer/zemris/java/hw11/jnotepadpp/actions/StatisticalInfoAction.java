package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;

/**
 * This class represents an action which shows the statistical info of the current document
 * @author Andrej
 *
 */
public class StatisticalInfoAction extends AbstractAction{

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
	public StatisticalInfoAction(JNotepadPP frame, String key) {
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
		
		int numberOfLines = 1;
		int numberOfCharacters = 0;
		int numberOfNonBlankCharacters = 0;
		
		String text = frame.getModel().getCurrentDocument().getTextComponent().getText();
		
		for(char c : text.toCharArray()) {
			if(c == '\n') numberOfLines++;
			if(!Character.isWhitespace(c)) numberOfNonBlankCharacters++;
			numberOfCharacters++;
		}
		
		JOptionPane.showMessageDialog(
				frame, 
				"" + frame.getFLP().getString("info1") +" "+  numberOfCharacters +" "+ frame.getFLP().getString("info2")+" "+ numberOfNonBlankCharacters +" "+frame.getFLP().getString("info3")+" "+ numberOfLines+" "+frame.getFLP().getString("info4"), 
				frame.getFLP().getString("statInfo"), 
				JOptionPane.INFORMATION_MESSAGE);
		
	}

}
