package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Supplier;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

import hr.fer.zemris.java.hw17.jvdraw.components.BottomLabel;
import hr.fer.zemris.java.hw17.jvdraw.components.DrawingObjectListModel;
import hr.fer.zemris.java.hw17.jvdraw.components.JColorArea;
import hr.fer.zemris.java.hw17.jvdraw.components.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.geometrical.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.geometrical.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.tools.CircleTool;
import hr.fer.zemris.java.hw17.jvdraw.tools.FilledCircleTool;
import hr.fer.zemris.java.hw17.jvdraw.tools.LineTool;
import hr.fer.zemris.java.hw17.jvdraw.tools.actions.ExitAction;
import hr.fer.zemris.java.hw17.jvdraw.tools.actions.ExportAction;
import hr.fer.zemris.java.hw17.jvdraw.tools.actions.OpenAction;
import hr.fer.zemris.java.hw17.jvdraw.tools.actions.SaveAction;
import hr.fer.zemris.java.hw17.jvdraw.tools.actions.SaveAsAction;
import hr.fer.zemris.java.hw17.jvdraw.tools.actions.SaveVisitor;

/**
 * This class represents a simple drawing application.
 * @author Andrej
 *
 */
public class JVDraw extends JFrame{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Current tool
	 */
	private Tool currentTool;
	
	/**
	 * DrawingModel of the application
	 */
	private DrawingModel model;
	
	/**
	 * Save path for the document
	 */
	private Path savePath;
	
	/**
	 * Constructor for the class
	 */
	public JVDraw() {
		
		model = new DrawingModelImpl();
		
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setTitle("JVDraw");
		setSize(900, 750);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if(model.isModified()) {
					int button = JOptionPane.YES_NO_OPTION;
					int result = JOptionPane.showConfirmDialog(JVDraw.this, "Document was modified, are you sure you wish to close", "Confirm", button);
					if(result == JOptionPane.NO_OPTION) return;
				}
				JVDraw.this.dispose();
			}
		});
		
		initGUI();
		
	}

	/**
	 * This method is used to initialise the GUI
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		Container container = new Container();
		container.setLayout(new BorderLayout());
		
		JColorArea fgColorArea = new JColorArea(Color.black);
		JColorArea bgColorArea = new JColorArea(Color.white);
		JDrawingCanvas canvas = new JDrawingCanvas(new Supplier<Tool>() {
			@Override
			public Tool get() {
				return currentTool;
			}
		}, model);
		
		BottomLabel bottomColorInfo = new BottomLabel(fgColorArea, bgColorArea);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setLayout(new FlowLayout());
		toolBar.add(fgColorArea);
		toolBar.add(bgColorArea);
		
		JToggleButton line = new JToggleButton("Line");
		line.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentTool = new LineTool(model, fgColorArea, canvas);
			}
		});
		JToggleButton circle = new JToggleButton("Circle");
		circle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentTool = new CircleTool(model, fgColorArea, canvas);
			}
		});
		JToggleButton filledCircle = new JToggleButton("FilledCircle");
		filledCircle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentTool = new FilledCircleTool(model, fgColorArea, bgColorArea, canvas);
			}
		});
		
		ButtonGroup group = new ButtonGroup();
		group.add(line);
		group.add(circle);
		group.add(filledCircle);
		line.setSelected(true);
		currentTool = new LineTool(model, fgColorArea, canvas);
		
		toolBar.add(line);
		toolBar.add(circle);
		toolBar.add(filledCircle);
		
		cp.add(toolBar, BorderLayout.PAGE_START);
		
		DrawingObjectListModel listModel = new DrawingObjectListModel(model);
		JList<GeometricalObject> list = new JList<GeometricalObject>(listModel);
		container.add(canvas, BorderLayout.CENTER);
		container.add(new JScrollPane(list), BorderLayout.LINE_END);
		container.add(bottomColorInfo, BorderLayout.PAGE_END);
		
		list.addMouseListener(new MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent e) {
				if(e.getClickCount() == 2) {
					@SuppressWarnings("unchecked")
					JList<GeometricalObject> list = (JList<GeometricalObject>) e.getSource();
					int index = list.locationToIndex(e.getPoint());
					
					GeometricalObject object = list.getModel().getElementAt(index);
					GeometricalObjectEditor editor = object.createGeometricalObjectEditor();
					
					if(JOptionPane.showConfirmDialog(canvas, editor, "Change ", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
						try {
							editor.checkEditing();
							editor.acceptEditing();
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(canvas, ex.getMessage());
						}
					}
				}
			};
		});
		
		list.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				@SuppressWarnings("unchecked")
				JList<GeometricalObject> list = (JList<GeometricalObject>) e.getSource();
				int index = list.getSelectedIndex();
				if(index != -1) {
					GeometricalObject object = list.getModel().getElementAt(index);
					if(e.getKeyCode() == KeyEvent.VK_DELETE) {
						model.remove(object);
					}
					if(e.getKeyCode() == KeyEvent.VK_ADD || e.getKeyCode() == KeyEvent.VK_PLUS) {
						model.changeOrder(object, 1);
						if(model.getSize() != index + 1) {
							list.setSelectedIndex(index + 1);
						}
					}
					if(e.getKeyCode() == KeyEvent.VK_SUBTRACT || e.getKeyCode() == KeyEvent.VK_MINUS) {
						model.changeOrder(object, -1);
						if(index - 1 >= 0) {
							list.setSelectedIndex(index - 1);
						}
					}
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		cp.add(container, BorderLayout.CENTER);
		
		setUpMenu();
	}
	
	/**
	 * This method is used to setup a menu
	 */
	private void setUpMenu() {
		JMenuBar mb = new JMenuBar();
		JMenu file = new JMenu("File");
		mb.add(file);
		file.add(new JMenuItem(new OpenAction(this, model)));
		file.add(new JMenuItem(new SaveAction(this, model)));
		file.add(new JMenuItem(new SaveAsAction(this, model)));
		file.add(new JMenuItem(new ExportAction(this, model)));
		file.add(new JMenuItem(new ExitAction(this, model)));
		
		setJMenuBar(mb);
	}

	/**
	 * This method is used to save the document to the save path
	 */
	public void saveDocument() {
		SaveVisitor visitor = new SaveVisitor();
		for(int i=0; i<model.getSize(); i++) {
			model.getObject(i).accept(visitor);
		}
		String file = visitor.getResultString();
		
		try {
			Files.writeString(savePath, file);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(
					this,
					"Error while saving the file",
					"Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		model.clearModifiedFlag();
	}

	/**
	 * This method is used to get the save path
	 * @return save path
	 */
	public Path getSavePath() {
		return savePath;
	}

	/**
	 * This method is used to set a save path
	 * @param savePath save path
	 */
	public void setSavePath(Path savePath) {
		this.savePath = savePath;
	}

	/**
	 * This is the method from which the program starts
	 * @param args 
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{
			new JVDraw().setVisible(true);
		});
	}
}
