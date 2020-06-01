package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This class represents a provider for the localisation. It is a singleton class, which means
 * only a single instance of this class can be created.
 * @author Andrej
 *
 */
public class LocalizationProvider extends AbstractLocalizationProvider{
	
	/**
	 * Instance of this class
	 */
	private static volatile LocalizationProvider instance;
	
	/**
	 * Current language of the provider
	 */
	private String language = "en";
	
	/**
	 * Bundle for getting the values from .properies file
	 */
	private ResourceBundle bundle;
	
	/**
	 * Default private constructor
	 */
	private LocalizationProvider() {
		setLanguage(language);
	}
	
	/**
	 * MEthod which returns an instance of this class
	 * @return
	 */
	public static LocalizationProvider getInstance() {
		if(instance == null) {
			synchronized (LocalizationProvider.class) {
				instance = new LocalizationProvider();
			}
		}
		return instance;
	}
	
	/**
	 * Setter for the language of the class
	 * @param language language you wisht to set the language to
	 */
	public void setLanguage(String language) {
		Locale locale = Locale.forLanguageTag(language);
		bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.local.prijevodi", locale);
		this.language = language;
		fire();
	}
	
	/**
	 * Getter for the language
	 * @return current language
	 */
	public String getLanguage() {
		return language;
	}

	@Override
	public String getString(String key) {
		return bundle.getString(key);
	}
	
}
