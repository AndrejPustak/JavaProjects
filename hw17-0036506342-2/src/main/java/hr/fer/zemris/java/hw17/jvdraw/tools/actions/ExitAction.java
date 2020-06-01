package hr.fer.zemris.java.hw17.jvdraw.tools.actions;

import java.awt.event.ActionEvent;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw17.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.JVDraw;

/**
 * Class that represents an action used to exit the application
 * @author Andrej
 *
 */
public class ExitAction extends AbstractAction{
	
	private static final long serialVersionUID = 1L;

	JVDraw jvdraw;
	private DrawingModel model;
	
	

	public ExitAction(JVDraw jvdraw, DrawingModel model) {
		super();
		this.model = model;
		this.jvdraw = jvdraw;
		
		putValue(NAME, "Exit");
		putValue(SHORT_DESCRIPTION, "Exit the application");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(model.isModified()) {
			int i = JOptionPane.showConfirmDialog(jvdraw, "File was modified, do you want to save?", "Exit", JOptionPane.YES_NO_CANCEL_OPTION);
			
			if(i == JOptionPane.YES_OPTION) {
				Path openedFilePath;
				JFileChooser jfc = new JFileChooser();
				jfc.setDialogTitle("Save file");
				if(jfc.showSaveDialog(jvdraw) != JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(
							jvdraw,
							"Nothing was saved",
							"Not Saved",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				
				openedFilePath = jfc.getSelectedFile().toPath();
				if(!Utils.getFileExtension(openedFilePath).equals("jvd")) {
					openedFilePath = Paths.get(openedFilePath.toString() + ".jvd");
				}
				
				if(Files.exists(openedFilePath)) {
					int button = JOptionPane.YES_NO_OPTION;
					int result = JOptionPane.showConfirmDialog(jvdraw, "File already exists", "Confirm to save", button);
					if(result == JOptionPane.NO_OPTION) return;
				}
				
				jvdraw.setSavePath(openedFilePath);
				jvdraw.saveDocument();
			} else if(i == JOptionPane.NO_OPTION) {
				jvdraw.dispose();
			} else {
				return;
			}
		}
	}

}
