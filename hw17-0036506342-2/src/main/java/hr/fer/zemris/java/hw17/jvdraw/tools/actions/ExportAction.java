package hr.fer.zemris.java.hw17.jvdraw.tools.actions;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import hr.fer.zemris.java.hw17.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.JVDraw;
import hr.fer.zemris.java.hw17.jvdraw.geometrical.GeometricalObjectBBCalculator;
import hr.fer.zemris.java.hw17.jvdraw.geometrical.GeometricalObjectPainter;

/**
 * Class that represents an action used to export the application as jpg, gif or png
 * @author Andrej
 *
 */
public class ExportAction extends AbstractAction{
	private static final long serialVersionUID = 1L;

	JVDraw jvdraw;
	private DrawingModel model;
	
	

	public ExportAction(JVDraw jvdraw, DrawingModel model) {
		super();
		this.model = model;
		this.jvdraw = jvdraw;
		
		putValue(NAME, "Export");
		putValue(SHORT_DESCRIPTION, "Export document");
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		
		String ext = chooseExtenstion();
		
		if(ext == null) {
			return;
		}
		
		JFileChooser jfc = new JFileChooser();
		Path openedFilePath = null;
		jfc.setDialogTitle("Open file");
		if(jfc.showOpenDialog(jvdraw) != JFileChooser.APPROVE_OPTION) {
			return;
		}
		openedFilePath = Paths.get(jfc.getSelectedFile().toPath().toString() + "." + ext);
		
		GeometricalObjectBBCalculator bbcalc = new GeometricalObjectBBCalculator();
		for(int i = 0;  i < model.getSize(); i++) {
			model.getObject(i).accept(bbcalc);
		}
		Rectangle box = bbcalc.getBoundingBox();
		
		BufferedImage image = new BufferedImage(box.width, box.height, BufferedImage.TYPE_3BYTE_BGR);
		
		Graphics2D g2d = image.createGraphics();
		g2d.setColor(Color.white);
		g2d.fillRect(0, 0, box.width, box.height);
		g2d.translate(-box.x, -box.y);
		
		
		GeometricalObjectPainter painter = new GeometricalObjectPainter(g2d);
		for(int i = 0;  i < model.getSize(); i++) {
			model.getObject(i).accept(painter);
		}
		
		g2d.dispose();
		java.io.File file = openedFilePath.toFile();
		try {
			ImageIO.write(image, ext, file);
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(jvdraw, "File was not exported");
			return;
		}
		
		JOptionPane.showMessageDialog(jvdraw, "File exported");
	}



	private String chooseExtenstion() {
		String ext = null;
		
		JPanel panel = new JPanel();
		JToggleButton jpg = new JToggleButton("jpg");
		jpg.setActionCommand("jpg");
		JToggleButton png = new JToggleButton("png");
		png.setActionCommand("png");
		JToggleButton gif = new JToggleButton("gif");
		gif.setActionCommand("gif");
		
		ButtonGroup group = new ButtonGroup();
		group.add(jpg);
		group.add(png);
		group.add(gif);
		
		panel.add(jpg);
		panel.add(png);
		panel.add(gif);
		
		jpg.setSelected(true);
		
		if(JOptionPane.showConfirmDialog(jvdraw, panel, "Chose extension", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
			ext = group.getSelection().getActionCommand();
		}
		return ext;
	}
}
