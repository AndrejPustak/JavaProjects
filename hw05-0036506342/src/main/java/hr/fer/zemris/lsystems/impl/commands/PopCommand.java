package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * This command pops one state from the context
 * @author Andrej
 *
 */
public class PopCommand implements Command{
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.popState();
	}
}
