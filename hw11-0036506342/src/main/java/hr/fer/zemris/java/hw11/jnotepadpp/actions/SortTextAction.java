package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.text.Collator;
import java.util.Arrays;
import java.util.Locale;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;

/**
 * This class represents an action which sorts the selected text by lines
 * @author Andrej
 *
 */
public class SortTextAction extends AbstractAction{

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
	 * variable that keeps track if the sort should be
	 * ascending or descending
	 */
	private boolean ascending;
	
	/**
	 * COnstructor for the class
	 * @param frame parent frame
	 * @param key key used for localisation
	 * @param ascending true if you wish for an ascending sort, false for descending
	 */
	public SortTextAction(JNotepadPP frame, String key, boolean ascending) {
		this.frame = frame;
		this.key = key;
		this.ascending = ascending;
		
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
		JTextComponent tc = frame.getModel().getCurrentDocument().getTextComponent();
		Document doc = tc.getDocument();
		Element root = doc.getDefaultRootElement();
		
		Caret caret = tc.getCaret();
		int startLine = root.getElementIndex(caret.getDot());
		int endLine = root.getElementIndex(caret.getMark());
		
		if(endLine < startLine) {
			int num = endLine;
			endLine = startLine;
			startLine = num;
		}
		
		int startOffset = root.getElement(startLine).getStartOffset();
		int endOffset = root.getElement(endLine).getEndOffset();
		
		try {
			String text = sortText(doc.getText(startOffset, Math.abs(endOffset - startOffset)));
			doc.remove(startOffset, Math.abs(endOffset - startOffset)-1);
			doc.insertString(startOffset, text, null);
		} catch (BadLocationException ignorable) {
			ignorable.printStackTrace();
		}
	}
	
	/**
	 * This method is used to sort the text by lines
	 * @param text text you wish to sort
	 * @return sorted text
	 */
	private String sortText(String text) {
		
		String[] lines = text.split("\\r?\\n");
		
		Locale locale = new Locale(LocalizationProvider.getInstance().getLanguage());
		Collator collator = Collator.getInstance(locale);
		
		if(ascending)
			Arrays.sort(lines, collator);
		else
			Arrays.sort(lines, collator.reversed());
		
		return String.join(System.lineSeparator(), lines);
		
	}
	
}
