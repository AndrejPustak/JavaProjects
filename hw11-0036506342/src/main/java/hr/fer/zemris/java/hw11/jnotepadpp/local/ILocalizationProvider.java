package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * THis interface represents an object capable of providing localisation
 * @author Andrej
 *
 */
public interface ILocalizationProvider {
	
	/**
	 * Adds an ILocalizationListeners to the list of listeners
	 * @param listener listener you wish to add
	 */
	public void addLocalizationListener(ILocalizationListener listener);
	
	/**
	 * Removes the listener from the list
	 * @param listener listener you wish to remove
	 */
	public void removeLocalizationListener(ILocalizationListener listener);
	
	/**
	 * This method is used to get the string which corresponds to the given key. 
	 *
	 * @param key Key of the string
	 * @return string whose key is the given key
	 */
	public String getString(String key);
	
}
