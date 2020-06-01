package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 * Program which takes in a single command lien argument: a path to the information of the
 * bar chart and then draws it.
 * @author Andrej
 *
 */
public class BarChartDemo extends JFrame{
	private static final long serialVersionUID = 1L;

	/**
	 * Method form which the program starts
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		if(args.length != 1) {
			System.out.println("Expected one file path as argument");
			return;
		}
		
		Path file = Paths.get(args[0]);
		
		List<String> lines = null;
		try {
			lines = Files.readAllLines(file);
		} catch (IOException e) {
			System.out.println("Unable to read lines");
		}
		
		String[] sValues = lines.get(2).split("\\s+");
		List<XYValue> values = new ArrayList<XYValue>();
		
		for(String sValue : sValues) {
			String[] xy = sValue.split(",");
			values.add(new XYValue(Integer.parseInt(xy[0]), Integer.parseInt(xy[1])));
		}
		
		BarChart chart = new BarChart(
				values, 
				lines.get(0), 
				lines.get(1), 
				Integer.parseInt(lines.get(3)), 
				Integer.parseInt(lines.get(4)),
				Integer.parseInt(lines.get(5)));
		
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new BarChartDemo();
			frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			frame.setPreferredSize(new Dimension(500, 500));
			frame.setLayout(new BorderLayout());
			
			JLabel label = new JLabel(file.toAbsolutePath().toString(), SwingConstants.CENTER);
			frame.add(label, BorderLayout.PAGE_START);
			frame.add(new BarChartComponent(chart), BorderLayout.CENTER);
			frame.pack();
			frame.setVisible(true);
		});
	}
	
}
