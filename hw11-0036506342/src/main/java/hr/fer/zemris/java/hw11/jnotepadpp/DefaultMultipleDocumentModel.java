package hr.fer.zemris.java.hw11.jnotepadpp;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

/**
 * This class is an implementation of MultipleDocumentModel. It extends JTabbedPane an
 * is used to work with multiple documents at the same time.
 * @author Andrej
 *
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * List of the documents in this model
	 */
	private List<SingleDocumentModel> documents;
	
	/**
	 * Reference to the current document
	 */
	private SingleDocumentModel current;
	
	/**
	 * Reference to the parent frame of this model
	 */
	private JNotepadPP frame;
	
	/**
	 * Image icon of the red diskete
	 */
	private ImageIcon red;
	
	/**
	 * Image icon of the green diskete
	 */
	private ImageIcon green;
	
	/**
	 * List of the listeners
	 */
	private List<MultipleDocumentListener> listeners;
	
	/**
	 * Constructor for the DefaultMultipleDocumentModel
	 * @param frame Reference to the parent frame of the model
	 */
	public DefaultMultipleDocumentModel(JNotepadPP frame) {
		this.frame = frame;
		
		documents = new ArrayList<SingleDocumentModel>();
		listeners = new ArrayList<MultipleDocumentListener>();
		
		red = getImage("icons/red.png");
		green = getImage("icons/green.png");
	
		addChangeListener(e->{
			if(current != null) {
				if(getSelectedIndex() != -1) {
					current = documents.get(getSelectedIndex());
					frame.modifiedChange(documents.get(getSelectedIndex()).isModified());
					if(current.getFilePath() == null)
						frame.setTitle("(Unnamed) - JNotepadPP");
					else
						frame.setTitle("" + current.getFilePath().toString() + " - JNotepadPP");
				}
				else {
					frame.setTitle("");
				}
			}
			else {
				if(getSelectedIndex() != -1)
					current = documents.get(getSelectedIndex());
				frame.modifiedChange(false);
				frame.setTitle("");
			}
				
		});
		
	}
	
	/**
	 * This method is used to load and return the image from disk
	 * @param string relative path to the icon
	 * @return ImageIcon at the specified string path
	 */
	private ImageIcon getImage(String string) {
		try(InputStream is = this.getClass().getResourceAsStream(string)){
			byte[] bytes = is.readAllBytes();
			is.close();
			return new ImageIcon(bytes);
		} catch (Exception e) {
			System.out.println("Unable to load the image");
			return null;
		}
		
	}
	
	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return documents.iterator();
	}
	
	@Override
	public SingleDocumentModel createNewDocument() {
		SingleDocumentModel newModel = new DefaultSingleDocumentModel(null, "");
		current = newModel;
		current.addSingleDocumentListener(new DefaultSingleDocumentListener());
		documents.add(current);
		addTab("(unnamed)", new JScrollPane(current.getTextComponent()));
		setSelectedIndex(documents.size()-1);
		setIconAt(getSelectedIndex(), green);
		
		current.getTextComponent().addCaretListener(e->{
			frame.updateStatusBar();
		});
		
		currentUpdate(null, current);
		addedUpdate(current);
		
		return current;
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {
		return current;
	}

	@Override
	public SingleDocumentModel loadDocument(Path path) {
		Objects.requireNonNull(path);
		
		int index = containsDocument(path);
		if(index != -1) {
			SingleDocumentModel previous = current;
			current = documents.get(index);
			setSelectedIndex(index);
			currentUpdate(previous, current);
			return current;
		} else {
			String text = null;
			try {
				text = Files.readString(path);
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(
						this,
						"Dogodila se pogreška pri čitanju datoteke",
						"Greška",
						JOptionPane.ERROR_MESSAGE);
				return null;
			}
			SingleDocumentModel previous = current;
			current = new DefaultSingleDocumentModel(path, text);
			current.addSingleDocumentListener(new DefaultSingleDocumentListener());
			documents.add(current);
			addTab(path.getFileName().toString(), new JScrollPane(current.getTextComponent()));
			setSelectedIndex(documents.size() - 1);
			
			current.getTextComponent().addCaretListener(e->{
				frame.updateStatusBar();
			});
			
			currentUpdate(previous, current);
			addedUpdate(current);
			setIconAt(getSelectedIndex(), green);
			return current;
		}
		
	}
	
	/**
	 * This method checks if the document at the specified path is already opened
	 * and returns its index
	 * @param path Path of the document
	 * @return index of the document if it is already opened, -1 otherwise
	 */
	private int containsDocument(Path path) {
		int index = -1;
		for(int i = 0; i < documents.size(); i++) {
			if(documents.get(i).getFilePath() == null) continue;
			if(documents.get(i).getFilePath().equals(path)) {
				index = i;
				break;
			}
		}
		return index;
	}

	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		if(newPath == null) {
			try {
				Files.writeString(model.getFilePath(), model.getTextComponent().getText());
			} catch (IOException e) {
				JOptionPane.showMessageDialog(
						this,
						"Dogodila se pogreška pri spremanju datoteke",
						"Greška",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
		} else {
			try {
				Files.writeString(newPath, model.getTextComponent().getText());
				model.setFilePath(newPath);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(
						this,
						"Dogodila se pogreška pri spremanju datoteke",
						"Greška",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		model.setModified(false);
	}

	@Override
	public void closeDocument(SingleDocumentModel model) {
		int index = documents.indexOf(model);
		removeTabAt(index);
		documents.remove(model);
		if(index == documents.size()) {
			setSelectedIndex(index-1);
			current = null;
			if(index == 0) {
				current = null;
				currentUpdate(model, null);
			}
			else {
				current = documents.get(index - 1);
				currentUpdate(model, documents.get(index - 1));
			}
			
			removedUpdate(model);
		} else {
			setSelectedIndex(index);
			current = documents.get(index);
			currentUpdate(model, documents.get(index));
			removedUpdate(model);
		}
		
		if(current != null)
			frame.modifiedChange(current.isModified());
		else 
			frame.modifiedChange(false);
		
	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.add(l);
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.remove(l);
	}

	@Override
	public int getNumberOfDocuments() {
		return documents.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		return documents.get(index);
	}
	
	/**
	 * This method is used to notify all the listeners that the current document has changed
	 * @param previousModel previous document model
	 * @param currentModel current document model
	 */
	private void currentUpdate(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
		if(previousModel == null && currentModel == null) {
			System.out.println("Both cant be null");
		}
		listeners.stream().forEach(l->l.currentDocumentChanged(previousModel, currentModel));
	}
	
	/**
	 * This method is used to notify all the listeners that a new document was added
	 * @param model Added document model
	 */
	private void addedUpdate(SingleDocumentModel model) {
		listeners.stream().forEach(l->l.documentAdded(model));
	}
	
	/**
	 * This method is used to notify all the listeners that a document was removed
	 * @param model Removed document model
	 */
	private void removedUpdate(SingleDocumentModel model) {
		listeners.stream().forEach(l->l.documentRemoved(model));
	}
	
	/**
	 * Private implementation of SingleDocumentListener for this class.
	 * @author Andrej
	 *
	 */
	private class DefaultSingleDocumentListener implements SingleDocumentListener{

		@Override
		public void documentModifyStatusUpdated(SingleDocumentModel model) {
			frame.modifiedChange(model.isModified());
			
			if(model.isModified()) setIconAt(getSelectedIndex(), red);
			else setIconAt(getSelectedIndex(), green);
			
		}

		@Override
		public void documentFilePathUpdated(SingleDocumentModel model) {
			setTitleAt(getSelectedIndex(), model.getFilePath().getFileName().toString());
		}
		
	}
}
