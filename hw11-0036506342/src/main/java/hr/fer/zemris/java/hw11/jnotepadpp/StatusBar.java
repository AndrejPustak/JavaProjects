package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;

/**
 * This class represents the status bar for the JNotepadPP.
 * @author Andrej
 *
 */
public class StatusBar extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Left-most label on the status bar
	 */
	JLabel left;
	
	/**
	 * Middle label on the status bar
	 */
	JLabel middle;
	
	/**
	 * Right-most label on the status bar
	 */
	JLabel right;
	
	/**
	 * Reference to the parent frame
	 */
	JNotepadPP frame;
	
	/**
	 * Constructor for the StuatusBar
	 * @param frame parent frame
	 */
	public StatusBar(JNotepadPP frame) {
		
		this.frame = frame;
		
		left = new JLabel();
		right = new Clock();
		middle = new JLabel();
		
		frame.getFLP().addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				update();
			}
		});
		
		setLayout(new BorderLayout());
		add(left, BorderLayout.LINE_START);
		add(middle, BorderLayout.CENTER);
		add(right, BorderLayout.LINE_END);
	}
	
	/**
	 * This method is used to update the status bar
	 */
	public void update() {
		
		if(frame.getModel().getCurrentDocument() == null) {
			left.setText("");
			middle.setText("");
			return;
		}
		JTextComponent tc = frame.getModel().getCurrentDocument().getTextComponent();
		Document doc = tc.getDocument();
		Element root = doc.getDefaultRootElement();
		
		left.setText("" + frame.getFLP().getString("length") + ":" + tc.getText().length() + "          ");
		
		int l = root.getElementIndex(tc.getCaretPosition());
		int c = tc.getCaretPosition() - root.getElement(l).getStartOffset();
		
		int s = Math.abs(tc.getCaret().getMark() - tc.getCaret().getDot());
		
		middle.setText(""+ frame.getFLP().getString("ln") + ":" +(l+1) + " " + frame.getFLP().getString("col") + ":" + (c + 1) + " " + frame.getFLP().getString("sel") + ":" + s);
		
	}
	
	/**
	 * This class extends JLabel and represents a clock which shows
	 * current date and time.
	 * @author Andrej
	 *
	 */
	static class Clock extends JLabel {

		private static final long serialVersionUID = 1L;
		
		volatile String time;
		volatile boolean stopRequested;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("y/MM/dd HH:mm:ss");
		
		public Clock() {
			updateTime();
			
			Thread t = new Thread(()->{
				while(true) {
					try {
						Thread.sleep(500);
					} catch(Exception ex) {}
					SwingUtilities.invokeLater(()->{
						updateTime();
					});
				}
			});
			t.setDaemon(true);
			t.start();
		}
		
		private void updateTime() {
			time = formatter.format(LocalDateTime.now());
			setText(time);
		}

		
	}
	
}
