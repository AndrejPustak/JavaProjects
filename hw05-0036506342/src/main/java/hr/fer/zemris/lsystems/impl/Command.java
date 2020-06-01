package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * Interface Command represents a single command for the LSystemBuilder
 * It has a single method called execute
 * @author Andrej
 *
 */
public interface Command {
	
	/**
	 * This method executes the command.
	 * @param ctx context of the drawing
	 * @param painter object of type Painter
	 */
	void execute(Context ctx, Painter painter);
}
