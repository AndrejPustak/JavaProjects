package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.hw11.jnotepadpp.actions.ChangeLanguageAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.CloseDocumentAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.CopyTextAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.CreateDocumentAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.CutTextAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.ExitApplicationAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.OpenDocumentAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.PasteTextAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.SaveAsDocumentAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.SaveDocumentAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.SortTextAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.StatisticalInfoAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.UniqueLinesAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.ChangeCaseAction;
import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;

/**
 * Program which represents a simple text editor which can work with multiple
 * documents at the same time.
 * The program supports internationalisation and currently supports 3 languages.
 * @author Andrej
 *
 */
public class JNotepadPP extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Action to open a document from disk
	 */
	private final Action openDocument;
	
	/**
	 * Action to create a new document
	 */
	private final Action createDocument;
	
	/**
	 * Action to save the document to disk
	 */
	private final Action saveDocument;
	
	/**
	 * Action to save the document to disk as...
	 */
	private final Action saveAsDocument;
	
	/**
	 * Action to close the current document
	 */
	private final Action closeDocument;
	
	/**
	 * Action to show the statistical info 
	 */
	private final Action statisticalInfo;
	
	/**
	 * Action to exit the application
	 */
	private final Action exitApplication;
	
	/**
	 * Action to copy the selected text to clipboard
	 */
	private final Action copyText;
	
	/**
	 * Action to paste text from clipboard
	 */
	private final Action pasteText;
	
	/**
	 * Action to cut text to clipboard
	 */
	private final Action cutText;
	

	/**
	 * Action to change the selected text to upper case
	 */
	private final Action toUpperCase;
	
	/**
	 * Action to change the selected text to lower case
	 */
	private final Action toLowerCase;
	
	/**
	 * Action to invert the case of the selected text
	 */
	private final Action inverCase;
	
	/**
	 * Action to sort the selected lines ascending
	 */
	private final Action ascendingSort;
	
	/**
	 * Action to sort the selected lines descending
	 */
	private final Action descendingSort;
	
	/**
	 * Action to remove duplicate lines
	 */
	private final Action uniqueLines;
	
	
	/**
	 * Action to change the language to English
	 */
	private final Action changeToEnglish;
	
	/**
	 * Action to change the language to German
	 */
	private final Action changeToCroatian;
	
	/**
	 * Action to change the language to Croatian
	 */
	private final Action changeToGerman;
	
	/**
	 * MultipleDocumentModel used by this program
	 */
	DefaultMultipleDocumentModel model;
	
	/**
	 * Status bar of the program
	 */
	StatusBar statusBar;
	
	/**
	 * Localisation provider used by the program
	 */
	FormLocalizationProvider flp;
	
	/**
	 * Constructor for the JNotepadPP
	 */
	public JNotepadPP() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setSize(900, 750);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if(checkIfModified()) {
					int button = JOptionPane.YES_NO_OPTION;
					int result = JOptionPane.showConfirmDialog(JNotepadPP.this, "One of the documents is modified, are you sure you wannt to close", "Confirm", button);
					if(result == JOptionPane.NO_OPTION) return;
				}
				JNotepadPP.this.dispose();
			}
		});
		
		flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
		
		openDocument = new OpenDocumentAction(this, "open");
		createDocument = new CreateDocumentAction(this, "new");
		saveDocument = new SaveDocumentAction(this, "save");
		saveAsDocument = new SaveAsDocumentAction(this, "saveAs");
		closeDocument = new CloseDocumentAction(this, "close");
		statisticalInfo = new StatisticalInfoAction(this, "info");
		exitApplication = new ExitApplicationAction(this, "exit");
		
		copyText = new CopyTextAction(this, "copy");
		pasteText = new PasteTextAction(this, "paste");
		cutText = new CutTextAction(this, "cut");
		
		toUpperCase = new ChangeCaseAction(this, "upper");
		toLowerCase = new ChangeCaseAction(this, "lower");
		inverCase = new ChangeCaseAction(this, "invert");
		ascendingSort = new SortTextAction(this, "asc", true);
		descendingSort = new SortTextAction(this, "desc", false);
		uniqueLines = new UniqueLinesAction(this, "unique");
		
		changeToCroatian = new ChangeLanguageAction(this, "hr");
		changeToEnglish = new ChangeLanguageAction(this, "en");
		changeToGerman = new ChangeLanguageAction(this, "de");
		
		flp.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				UIManager.put("OptionPane.yesButtonText", flp.getString("yes"));
				UIManager.put("OptionPane.noButtonText", flp.getString("no"));
			}
		});
		
		initGUI();
		setLocationRelativeTo(null);
	}
	
	/**
	 * MEthod to initialize the GUI of the program
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		model = new DefaultMultipleDocumentModel(this);
		model.addChangeListener((e)->{
		});
		
		statusBar = new StatusBar(this);
		
		Container cont = new Container();
		cont.setLayout(new BorderLayout());
		cont.add(model, BorderLayout.CENTER);
		cont.add(statusBar, BorderLayout.PAGE_END);
		
		
		
		cp.add(cont, BorderLayout.CENTER);
		
		configureActions();
		createMenus();
		createToolbar();
	}
	
	/**
	 * When this method it changes enables/disables all actions that 
	 * can be used depending on if the document has been modified
	 * @param value true if you wish to enable, false otherwise
	 */
	public void modifiedChange(boolean value){
		saveDocument.setEnabled(value);
	}
	
	/**
	 * This method is used to configure the actions at the start of the program
	 */
	private void configureActions() {
		openDocument.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control 0"));
		openDocument.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_A);
		
		createDocument.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control N"));
		createDocument.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_N);
		
		saveDocument.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control S"));
		saveDocument.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_S);
		saveDocument.setEnabled(false);
		
		saveAsDocument.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control alt S"));
		saveAsDocument.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_A);
		saveAsDocument.setEnabled(false);
		
		closeDocument.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control W"));
		closeDocument.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_W);
		closeDocument.setEnabled(false);
		
		copyText.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control C"));
		copyText.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_C);
		
		pasteText.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control V"));
		pasteText.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_C);
		
		cutText.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control X"));
		cutText.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_X);
		
		statisticalInfo.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control I"));
		statisticalInfo.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_I);
		statisticalInfo.setEnabled(false);
		
		exitApplication.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("alt F4"));
		exitApplication.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_E);
		
		toUpperCase.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control shift U"));
		toUpperCase.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_U);
		
		toLowerCase.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control U"));
		toLowerCase.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_L);
		
		ascendingSort.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_A);
		
		descendingSort.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_D);
		
		uniqueLines.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_Q);
		
		model.addMultipleDocumentListener(new MultipleDocumentListener() {
			
			@Override
			public void documentRemoved(SingleDocumentModel model) {
				if(getModel().getCurrentDocument() == null) {
					saveAsDocument.setEnabled(false);
					closeDocument.setEnabled(false);
					statisticalInfo.setEnabled(false);
				}
			}
			
			@Override
			public void documentAdded(SingleDocumentModel model) {
				saveAsDocument.setEnabled(true);
				closeDocument.setEnabled(true);
				statisticalInfo.setEnabled(true);
			}
			
			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				statusBar.update();
			}
		});
	}
	
	/**
	 * This method is used to create menus for the program
	 */
	private void createMenus() {
		JMenuBar mb = new JMenuBar();
		JMenu file = new JMenu(flp.getString("file"));
		mb.add(file);
		file.add(new JMenuItem(createDocument));
		file.add(new JMenuItem(openDocument));
		file.add(new JMenuItem(saveDocument));
		file.add(new JMenuItem(saveAsDocument));
		file.add(new JMenuItem(closeDocument));
		file.addSeparator();
		file.add(new JMenuItem(statisticalInfo));
		file.addSeparator();
		file.add(new JMenuItem(exitApplication));
		JMenu edit = new JMenu(flp.getString("edit"));
		mb.add(edit);
		edit.add(new JMenuItem(copyText));
		edit.add(new JMenuItem(pasteText));
		edit.add(new JMenuItem(cutText));
		
		JMenu tools = new JMenu(flp.getString("tools"));
		mb.add(tools);
		tools.add(toUpperCase);
		tools.add(toLowerCase);
		tools.add(inverCase);
		JMenu sort = new JMenu(flp.getString("sort"));
		tools.add(sort);
		sort.add(ascendingSort);
		sort.add(descendingSort);
		tools.add(uniqueLines);
		
		JMenu languages = new JMenu(flp.getString("languages"));
		mb.add(languages);
		languages.add(changeToCroatian);
		languages.add(changeToEnglish);
		languages.add(changeToGerman);
		
		flp.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				file.setText(flp.getString("file"));
				edit.setText(flp.getString("edit"));
				languages.setText(flp.getString("languages"));
				tools.setText(flp.getString("tools"));
				sort.setText(flp.getString("sort"));
			}
		});
	
		setJMenuBar(mb);
		
	}
	
	/**
	 * This method is used to create the toolbar for the program
	 */
	private void createToolbar() {
		JToolBar tb = new JToolBar();
		tb.setFloatable(true);
		
		tb.add(new JButton(createDocument));
		tb.add(new JButton(openDocument));
		tb.add(new JButton(saveDocument));
		tb.add(new JButton(saveAsDocument));
		tb.add(new JButton(closeDocument));
		tb.add(new JButton(statisticalInfo));
		tb.add(new JButton(exitApplication));
		tb.add(new JButton(copyText));
		tb.add(new JButton(pasteText));
		tb.add(new JButton(cutText));
		tb.add(new JButton(toUpperCase));
		tb.add(new JButton(toLowerCase));
		tb.add(new JButton(inverCase));
		tb.add(new JButton(ascendingSort));
		tb.add(new JButton(descendingSort));
		tb.add(new JButton(uniqueLines));
		
		getContentPane().add(tb, BorderLayout.PAGE_START);
		
	}
	
	/**
	 * This method is used to check if one if the documents has been modified
	 * @return true if one has been modified, false otherwise
	 */
	public boolean checkIfModified() {
		for(SingleDocumentModel sd : model) {
			if(sd.isModified()) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Getter for the model of this class
	 * @return MultipleDocumentModel of this class
	 */
	public MultipleDocumentModel getModel() {
		return model;
	}
	
	/**
	 * Getter for the flp
	 * @return FormLocalizationProvider of this class
	 */
	public FormLocalizationProvider getFLP() {
		return flp;
	}
	
	/**
	 * This method is used to update the status bar
	 */
	public void updateStatusBar() {
		statusBar.update();
	}

	/**
	 * THis is the method from which thr program starts
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{
			new JNotepadPP().setVisible(true);
		});
	}
	
}
