package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract implementation of ILocalizationProvider. It implements everything except the getString() method.
 * @author Andrej
 *
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider{
	
	/**
	 * List of listeners
	 */
	List<ILocalizationListener> listeners;
	
	/**
	 * Default constructor
	 */
	public AbstractLocalizationProvider() {
		listeners = new ArrayList<ILocalizationListener>();
	}
	
	@Override
	public void addLocalizationListener(ILocalizationListener listener) {
		listeners.add(listener);
		
	}
	
	@Override
	public void removeLocalizationListener(ILocalizationListener listener) {
		listeners.remove(listener);
		
	}
	
	/**
	 * Method used to notify all of the listeners about the change
	 */
	public void fire() {
		listeners.stream().forEach(l->l.localizationChanged());
	}
	
}
