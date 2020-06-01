package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.LSystemBuilderProvider;

/**
 * This class is an implementation of LSystemBuilderProvider
 * It has a single method which is used to create a new LSystemBuilder
 * @author Andrej
 *
 */
public class LSystemBuilderProviderImpl implements LSystemBuilderProvider{
	
	/**
	 * This method is used to create a new LSystemBuilder
	 */
	@Override
	public LSystemBuilder createLSystemBuilder() {
		return new LSystemBuilderImpl();
	}

}
