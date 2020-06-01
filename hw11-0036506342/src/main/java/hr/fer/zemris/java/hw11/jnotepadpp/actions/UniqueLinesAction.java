package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;

/**
 * This class represents an action which removes duplicate lines
 * @author Andrej
 *
 */
public class UniqueLinesAction extends AbstractAction{

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
	public UniqueLinesAction(JNotepadPP frame, String key) {
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
			String text = removeLines(doc.getText(startOffset, Math.abs(endOffset - startOffset)));
			doc.remove(startOffset, Math.abs(endOffset - startOffset)-1);
			doc.insertString(startOffset, text, null);
		} catch (BadLocationException ignorable) {
			ignorable.printStackTrace();
		}
	}
	
	/**
	 * This class is used to remove duplicate lines
	 * @param text text you wish to remove the liens from
	 * @return resulting text
	 */
	private String removeLines(String text) {
		
		String[] lines = text.split("\\r?\\n");
		
		List<String> list = new ArrayList<String>();
		
		for(String line : lines) {
			if(!list.contains(line)) {
				list.add(line);
			}
		}
		
		return String.join(System.lineSeparator(), Arrays.copyOf(list.toArray(), list.size(), String[].class));
		
	}
	
}
