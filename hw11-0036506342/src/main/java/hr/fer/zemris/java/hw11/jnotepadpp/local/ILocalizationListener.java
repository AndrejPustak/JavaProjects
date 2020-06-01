package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * THis class represents a listener that listens to localisation changes
 * @author Andrej
 *
 */
public interface ILocalizationListener {
	
	/**
	 * Method used to notify the observer that the localisation has changed
	 */
	public void localizationChanged();
	
}
