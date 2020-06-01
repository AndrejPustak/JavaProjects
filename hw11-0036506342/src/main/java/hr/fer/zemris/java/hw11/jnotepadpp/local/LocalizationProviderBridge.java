package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * THis class represents a bridge between a localisation provider and the program so that
 * there is no memory leakage when the program closes.
 * @author Andrej
 *
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider{
	
	/**
	 * Variable that keeps track if the bridge has been connected
	 */
	private boolean connected;
	
	/**
	 * Parent localisation provider
	 */
	private ILocalizationProvider parent;
	
	/**
	 * Listener
	 */
	private ILocalizationListener listener;
	
	/**
	 * Constructor for the class
	 * @param parent parent localisation provider
	 */
	public LocalizationProviderBridge(ILocalizationProvider parent) {
		connected = false;
		this.parent = parent;
	}
	
	/**
	 * Method used to disconnect the listener
	 */
	public void disconnect() {
		parent.removeLocalizationListener(listener);
		listener = null;
	}
	
	/**
	 * Method used to connect the listener
	 */
	public void connect() {
		if(!connected) {
			listener = new ILocalizationListener() {
				
				@Override
				public void localizationChanged() {
					fire();
				}
			};
			
			parent.addLocalizationListener(listener);
		}
		
		return;
		
	}
	
	@Override
	public String getString(String key) {
		return parent.getString(key);
	}

}
