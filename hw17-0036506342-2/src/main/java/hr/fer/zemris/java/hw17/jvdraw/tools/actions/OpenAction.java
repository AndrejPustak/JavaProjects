package hr.fer.zemris.java.hw17.jvdraw.tools.actions;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import hr.fer.zemris.java.hw17.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.JVDraw;
import hr.fer.zemris.java.hw17.jvdraw.geometrical.Circle;
import hr.fer.zemris.java.hw17.jvdraw.geometrical.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.geometrical.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.geometrical.Line;

/**
 * Class that represents an action used to open an existing document
 * @author Andrej
 *
 */
public class OpenAction extends AbstractAction{

	private static final long serialVersionUID = 1L;

	JVDraw jvdraw;
	private DrawingModel model;
	
	

	public OpenAction(JVDraw jvdraw, DrawingModel model) {
		super();
		this.model = model;
		this.jvdraw = jvdraw;
		
		putValue(NAME, "Open");
		putValue(SHORT_DESCRIPTION, "Open document");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(model.isModified()) {
			int button = JOptionPane.YES_NO_OPTION;
			int result = JOptionPane.showConfirmDialog(jvdraw, "Document was modified, are you sure you wish to open a new one", "Confirm", button);
			if(result == JOptionPane.NO_OPTION) return;
		}
		
		JFileChooser jfc = new JFileChooser();
		FileNameExtensionFilter  filter = new FileNameExtensionFilter("Filter for .jvd files", "jvd");
		jfc.setFileFilter(filter);
		Path openedFilePath = null;
		jfc.setDialogTitle("Open file");
		if(jfc.showOpenDialog(jvdraw) != JFileChooser.APPROVE_OPTION) {
			return;
		}
		openedFilePath = jfc.getSelectedFile().toPath();
		
		List<String> lines = null;
		try {
			lines = Files.readAllLines(openedFilePath);
		} catch (IOException e1) {
			System.out.println("Unable to read the file");
		}
		
		model.clear();
		
		for(String line : lines) {
			String[] lineA = line.split("\\s+");
			
			switch (lineA[0]) {
			case "LINE":
				model.add(getLineFromString(lineA));
				break;
			case "CIRCLE":
				model.add(getCircleFromString(lineA));
				break;
			case "FCIRCLE":
				model.add(getFilledCircleFromString(lineA));
				break;
			default:
				break;
			}
		}
		
		model.clearModifiedFlag();
		jvdraw.setSavePath(openedFilePath);
	}
	
	private GeometricalObject getFilledCircleFromString(String[] lineA) {
		int x = Integer.parseInt(lineA[1]);
		int y = Integer.parseInt(lineA[2]);
		int rad = Integer.parseInt(lineA[3]);
		int r1 = Integer.parseInt(lineA[4]);
		int g1 = Integer.parseInt(lineA[5]);
		int b1 = Integer.parseInt(lineA[6]);
		int r2 = Integer.parseInt(lineA[7]);
		int g2 = Integer.parseInt(lineA[8]);
		int b2 = Integer.parseInt(lineA[9]);
		
		return new FilledCircle(new Point(x, y), rad, new Color(r1, g1, b1), new Color(r2, g2, b2));
	}

	private GeometricalObject getCircleFromString(String[] lineA) {
		int x = Integer.parseInt(lineA[1]);
		int y = Integer.parseInt(lineA[2]);
		int rad = Integer.parseInt(lineA[3]);
		int r = Integer.parseInt(lineA[4]);
		int g = Integer.parseInt(lineA[5]);
		int b = Integer.parseInt(lineA[6]);
		
		return new Circle(new Point(x, y), rad, new Color(r, g, b));
	}

	private Line getLineFromString(String[] lineA) {
		int x1 = Integer.parseInt(lineA[1]);
		int y1 = Integer.parseInt(lineA[2]);
		int x2 = Integer.parseInt(lineA[3]);
		int y2 = Integer.parseInt(lineA[4]);
		int r = Integer.parseInt(lineA[5]);
		int g = Integer.parseInt(lineA[6]);
		int b = Integer.parseInt(lineA[7]);
		
		return new Line(new Point(x1, y1), new Point(x2, y2), new Color(r, g, b));
	}

}
