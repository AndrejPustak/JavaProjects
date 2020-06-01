package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

public class PrimDemo extends JFrame{
	
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(()->{
			JFrame frame = new PrimDemo();
			frame.setLayout(new BorderLayout());
			frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			frame.setPreferredSize(new Dimension(500, 500));
			
			Container con = new Container();
			con.setLayout(new GridLayout(1, 2));
			PrimListModel list = new PrimListModel();
			
			con.add(new JScrollPane(new JList<Integer>(list)));
			con.add(new JScrollPane(new JList<Integer>(list)));
			
			JButton button = new JButton("Next");
			button.addActionListener(r->{
				list.next();
			});
			
			frame.add(con, BorderLayout.CENTER);
			frame.add(button, BorderLayout.PAGE_END);
			
			frame.pack();
			frame.setVisible(true);
		});
		
	}
	
}	
