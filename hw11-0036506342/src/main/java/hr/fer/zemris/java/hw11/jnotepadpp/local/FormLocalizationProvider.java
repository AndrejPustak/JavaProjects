package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * Class that extends LocalizationProviderBridge. It should be used to connect
 * a frame to an localisation provider
 * @author Andrej
 *
 */
public class FormLocalizationProvider extends LocalizationProviderBridge{
	
	/**
	 * Constructor for the FormLocalizationProvider
	 * @param parent parent localisation listener
	 * @param frame frame you wish to internationalise
	 */
	public FormLocalizationProvider(ILocalizationProvider parent, JFrame frame) {
		super(parent);
		
		frame.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				connect();
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				disconnect();
			}
		});
	}
	
	
	
}
